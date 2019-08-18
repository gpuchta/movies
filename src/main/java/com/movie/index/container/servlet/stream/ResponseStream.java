package com.movie.index.container.servlet.stream;

import com.movie.index.app.callback.Callback;
import org.apache.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.function.Function;

public class ResponseStream<T> implements Callback<T> {

  private HttpServletResponse _resp;
  private PrintWriter _writer;
  private Function<T, String> _func;

  public ResponseStream(HttpServletResponse resp) throws IOException {
    _resp = resp;
    _resp.setCharacterEncoding("UTF-8");
    _resp.setStatus(HttpStatus.SC_OK);
    _writer = resp.getWriter();
    _writer.println("Start");
  }

  @Override
  public void handle(T item) {
    _writer.println(_func.apply(item));
    _writer.flush();
  }

  public void close() {
    _writer.println("Done");
    _writer.close();
  }

  public void setProducer(Function<T, String> func) {
    _func = func;
  }
}
