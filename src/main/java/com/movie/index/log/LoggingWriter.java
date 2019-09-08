package com.movie.index.log;

import org.slf4j.Logger;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.StringWriter;

public final class LoggingWriter extends FilterWriter {
  protected Logger _logger;
  protected StringBuilder _stringBuilder = new StringBuilder();

  private static final char CR = '\r';
  private static final char LF = '\n';

  public LoggingWriter(Logger logger) {
    super(new StringWriter());
    _logger = logger;
  }

  @Override
  public synchronized void write(int c) throws IOException {
    _stringBuilder.append(c);
  }

  @Override
  public synchronized void write(char[] cbuf, int off, int len) throws IOException {
    _stringBuilder.append(cbuf, off, len);
  }

  @Override
  public synchronized void write(String str, int off, int len) throws IOException {
    _stringBuilder.append(str.substring(off, off + len));
  }

  @Override
  public synchronized void flush() throws IOException {
    log();
  }

  @Override
  public synchronized void close() throws IOException {
    if (_stringBuilder.length() != 0) {
      log();
    }
  }

  @Override
  protected void finalize() throws Throwable {
    if (_stringBuilder.length() != 0) {
      log();
    }
    super.finalize();
  }

  protected void log() {
    stripExtraNewLine();
    logBuffer(_stringBuilder);
    _stringBuilder.delete(0, _stringBuilder.length());
  }

  private void logBuffer(final StringBuilder sb) {
    if (_logger == null) {
      return;
    }

    _logger.info(sb.toString());
  }

  private void stripExtraNewLine() {
    int length = _stringBuilder.length();

    if (length == 0) {
      return;
    }

    if (length == 1) {
      char last = _stringBuilder.charAt(0);
      if (last == CR || last == LF) {
        _stringBuilder.deleteCharAt(0);
      }
    } else {
      int lastPosition = length - 1;
      char secondLast = _stringBuilder.charAt(lastPosition);
      if (secondLast == CR) {
        _stringBuilder.deleteCharAt(lastPosition);
      } else if (secondLast == LF) {
        int secondlastPosition = length - 2;
        _stringBuilder.deleteCharAt(lastPosition);
        if (_stringBuilder.charAt(secondlastPosition) == CR) {
          _stringBuilder.deleteCharAt(secondlastPosition);
        }
      }
    }
  }
}