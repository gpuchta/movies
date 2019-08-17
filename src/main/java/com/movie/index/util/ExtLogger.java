package com.movie.index.util;

import java.text.SimpleDateFormat;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class ExtLogger extends Logger {

  private static SimpleDateFormat TIME_FORMATTER = new SimpleDateFormat("EEE MM/DD HH:mm:ss.SSS");
  private static Formatter FORMATTER;
  private static ConsoleHandler HANDLER;
  private static final Level DEBUG = new DebugLevel("DEBUG", Level.SEVERE.intValue() + 100);

  @SuppressWarnings("serial")
  private static class DebugLevel extends Level {
    protected DebugLevel(String name, int value) {
      super(name, value);
    }
  }
  
  static {
    FORMATTER = new Formatter() {
      @Override
      public String format(LogRecord record) {
        return String.format("%s [%-7s] %s\n",
            TIME_FORMATTER.format(record.getMillis()),
            record.getLevel().getName(),
            record.getMessage());
      }
    };

    HANDLER = new ConsoleHandler();
    HANDLER.setFormatter(FORMATTER);
  }

  private ExtLogger(String name, String resourceBundleName) {
    super(name, resourceBundleName);
  }

  public static ExtLogger getLogger(Class<?> clazz) {
    return getLogger(clazz.getName());
  }

  public static ExtLogger getLogger(String name) {
    ExtLogger extLogger = new ExtLogger(name, null);
    extLogger.addHandler(HANDLER);
    extLogger.setLevel(Level.FINEST);
    return extLogger;
  }

  public void debug(String template, Object... args) {
    super.log(DEBUG, String.format(template, args));
  }

  public void info(String template, Object... args) {
    super.info(String.format(template, args));
  }

  public void warning(String template, Object... args) {
    super.warning(String.format(template, args));
  }

  public void severe(String template, Object... args) {
    super.severe(String.format(template, args));
  }

  public void severe(String msg, Throwable e) {
    super.log(Level.SEVERE, msg, e);
  }
}
