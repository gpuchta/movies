package com.movie.index.container.servlet;

import com.google.gson.reflect.TypeToken;
import com.movie.index.app.image.ImageManager;
import com.movie.index.app.model.Movie;
import com.movie.index.container.service.MovieServletContext;
import com.movie.index.container.servlet.stream.ResponseStream;
import com.movie.index.util.GsonHelper;
import com.movie.index.util.PosterHelper;
import org.apache.http.HttpStatus;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

@SuppressWarnings("serial")
public class MovieRestoreServlet extends AbstractHttpServlet {

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    if (ServletFileUpload.isMultipartContent(req)) {
      try {
        restoreMovieData(req, resp, uploadMovieData(req));
      }
      catch(Throwable e) {
        resp.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
        write(resp, e.getMessage());
      }
    }
  }

  private List<Movie> uploadMovieData(HttpServletRequest req)
      throws FileUploadException, UnsupportedEncodingException {

    // Create a factory for disk-based file items
    DiskFileItemFactory factory = new DiskFileItemFactory();

    // Configure a repository (to ensure a secure temp location is used)
    ServletContext servletContext = req.getServletContext();
    File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
    factory.setRepository(repository);

    // Create a new file upload handler
    ServletFileUpload upload = new ServletFileUpload(factory);

    // Parse the request
    List<FileItem> items = upload.parseRequest(new ServletRequestContext(req));

    if (items.size() == 0) {
      log("no file in multi part form submit");
      return Collections.emptyList();
    }

    String jsonContent = items.get(0).getString("UTF-8");
    Type collectionType = new TypeToken<List<Movie>>(){}.getType();
    return GsonHelper.BASIC.fromJson(jsonContent, collectionType);
  }

  private void restoreMovieData(HttpServletRequest req, HttpServletResponse resp, List<Movie> movies)
      throws IOException {

    ResponseStream<Movie> loggingStream = new ResponseStream<Movie>(resp);
    ImageManager imageManager = MovieServletContext.getImageManager(req.getServletContext());

    try {
      Function<Movie, String> logProducer = movie -> String.format("Generate poster file for %s (%d)", movie.getTitle(), movie.getTmdbId());
      loggingStream.setProducer(logProducer);
      movies.forEach(movie -> {
        LOG.info(logProducer.apply(movie));
        loggingStream.handle(movie);
        PosterHelper.generatePosterDataUri(movie, imageManager);
        PosterHelper.persistPoster(movie, getConfig());
      });

      loggingStream.setProducer(movie -> String.format("Store movie %s (%d)", movie.getTitle(), movie.getTmdbId()));
      getDaoFactory().getMovieDao().restore(movies, loggingStream);
    }
    finally {
      loggingStream.close();
    }
  }
}
