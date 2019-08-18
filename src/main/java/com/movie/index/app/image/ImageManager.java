package com.movie.index.app.image;

import com.movie.index.Config;
import com.movie.index.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import java.awt.image.BufferedImage;
import java.net.URI;

public class ImageManager {

  private static final Logger LOG = LoggerFactory.getLogger(ImageManager.class);

  private static final String IMG_POSTER_DEFAULT_JPG = "/img/poster-default.jpg";

  private URI _posterDefaultFileUri;
  private BufferedImage _bufferedImage;
  private String _posterDefaultDataUri;

  private ImageManager() { }

  public static ImageManager init(ServletContext context, Config config) {
    LOG.info("Initialize image manager");
    ImageManager imageManager = new ImageManager();

    URI posterDefaultFileUri;
    BufferedImage bufferedImage;
    String imageDataUri;

    try {
      posterDefaultFileUri = context.getResource(IMG_POSTER_DEFAULT_JPG).toURI();
      bufferedImage = ImageIO.read(context.getResourceAsStream(IMG_POSTER_DEFAULT_JPG));
      imageDataUri = ImageConverter.toDataUri(bufferedImage, Constants.POSTER_WIDTH);
    }
    catch (Exception e) {
      LOG.warn("Failed to load poster default image", e);

      try {
        posterDefaultFileUri = new URI("memory:///Constants.FALLBACK_POSTER_DATA_URI");
        imageDataUri = Constants.FALLBACK_POSTER_DATA_URI;
        bufferedImage = ImageConverter.fromDataUri(imageDataUri).get();
      }
      catch (Exception e2) {
        LOG.warn("Failed to convert fallback poster to image", e2);
        throw new RuntimeException(e2);
      }
    }

    imageManager._posterDefaultFileUri = posterDefaultFileUri;
    imageManager._bufferedImage = bufferedImage;
    imageManager._posterDefaultDataUri = imageDataUri;
    return imageManager;
  }

  public String getPosterDefaultDataUri() {
    return _posterDefaultDataUri;
  }

  public BufferedImage getPosterDefaultAsBufferedImage() {
    return _bufferedImage;
  }

  public void log() {
    LOG.info("Poster default {}", _posterDefaultFileUri);
  }
}
