package com.movie.index.app.image;

import java.awt.image.BufferedImage;
import java.net.URI;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import com.movie.index.Config;
import com.movie.index.Constants;
import com.movie.index.Logging;
import com.movie.index.util.ExtLogger;

public class ImageManager implements Logging {

  private static final String IMG_POSTER_DEFAULT_JPG = "/img/poster-default.jpg";

  private static final ExtLogger LOG = ExtLogger.getLogger(ImageManager.class);

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
      LOG.severe("Failed to load poster default image", e);

      try {
        posterDefaultFileUri = new URI("memory:///Constants.FALLBACK_POSTER_DATA_URI");
        imageDataUri = Constants.FALLBACK_POSTER_DATA_URI;
        bufferedImage = ImageConverter.fromDataUri(imageDataUri).get();
      }
      catch (Exception e2) {
        LOG.severe("Failed to convert fallback poster to image", e2);
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
    LOG.info(CONFIG_LOG_FORMAT, "Poster default", _posterDefaultFileUri);
  }
}
