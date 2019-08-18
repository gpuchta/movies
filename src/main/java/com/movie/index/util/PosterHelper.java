package com.movie.index.util;

import com.movie.index.Config;
import com.movie.index.Constants;
import com.movie.index.app.image.ImageConverter;
import com.movie.index.app.image.ImageConverter.Format;
import com.movie.index.app.image.ImageManager;
import com.movie.index.app.model.Movie;
import com.movie.index.tmdb.v3.TmdbMediaUrl;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Optional;

public class PosterHelper {

  public static void generatePosterDataUri(Movie movie, ImageManager imageManager) {
    String poster_path = movie.getPosterPath();
    String posterDataUri;
    if(poster_path != null) {
      try {
        URL posterUrl = new URL(TmdbMediaUrl.getPoster185(poster_path));
        posterDataUri = ImageConverter.toDataUri(posterUrl, Format.JPG, Constants.POSTER_WIDTH);
      }
      catch (MalformedURLException e) {
        throw new RuntimeException(e);
      }
    }
    else {
      posterDataUri = imageManager.getPosterDefaultDataUri();
    }
    movie.setPosterDataUri(posterDataUri);
  }

  public static BufferedImage toBufferedImage(String posterDataUri) {
    try {
      return ImageConverter.fromDataUri(posterDataUri).get();
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static void persistPoster(Movie movie, Config config) {
    try {
      Optional<BufferedImage> image = ImageConverter.fromDataUri(movie.getPosterDataUri());
      if (image.isPresent()) {
        File outputfile = Paths.get(config.getImageFolder().getAbsolutePath(), movie.getPosterPath()).toFile();
        if (outputfile.exists()) {
          outputfile.delete();
        }
        ImageIO.write(image.get(), ImageConverter.Format.JPG.toString(), outputfile);
      }
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
