package com.movie.index.container.servlet;

import org.apache.http.HttpStatus;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.Date;
import java.util.concurrent.TimeUnit;



@SuppressWarnings("serial")
public class MovieImageServlet extends AbstractHttpServlet {

  private static final long CACHE_AGE_IN_DAYS = 3;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    try {
      String posterPath = getPath(req);
      if(!posterPath.startsWith("/")) {
        posterPath = "/" + posterPath;
      }

      resp.setDateHeader("Expires", new Date().getTime() + TimeUnit.DAYS.toMillis(CACHE_AGE_IN_DAYS));
      resp.setContentType("image/jpeg");
      resp.setHeader("Cache-Control", "max-age=" + TimeUnit.DAYS.toSeconds(CACHE_AGE_IN_DAYS));

      File posterFile = Paths.get(getConfig().getImageFolder().getAbsolutePath(), posterPath).toFile();
      BufferedImage bufferedImage;
      if(posterFile.exists()) {
        bufferedImage = ImageIO.read(posterFile);
      } else {
        bufferedImage = ImageIO.read(getServletContext().getResourceAsStream("/img/poster-default.jpg"));
      }
      OutputStream out = resp.getOutputStream();
      ImageIO.write(bufferedImage, "jpg", out);
      out.close();
    }
    catch(Throwable e) {
      resp.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
      write(resp, e.getMessage());
    }
  }
}
