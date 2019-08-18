package com.movie.index.app.favicon;

import com.movie.index.app.image.ImageConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class FaviconFactory {

  private static final Logger LOG = LoggerFactory.getLogger(FaviconFactory.class);

  private static final String FAVICON_FOLDER_NAME = "favicon";

  public enum Type {
    FAVICON_16x16("favicon-16x16.png"),
    FAVICON_32x32("favicon-32x32.png"),
    FAVICON_48x48("favicon-48x48.png"),
    FAVICON_60x60("favicon-60x60.png"),
    FAVICON_76x76("favicon-76x76.png"),
    FAVICON_120x120("favicon-120x120.png"),
    FAVICON_152x152("favicon-152x152.png");

    private String _filename;
    private String _dataUri;

    private Type(String filename) {
      _filename = filename;
    }

    public String getFilename() {
      return _filename;
    }

    public void setDataUri(String dataUri) {
      _dataUri = dataUri;
    }

    public String getDataUri() {
      return _dataUri;
    }
  }

  public static void buildCache(File rootFolder) {
    LOG.info("Build and cache favicon data URIs");
    File faviconFolder = new File(rootFolder, FAVICON_FOLDER_NAME);

    for (Type type : Type.values()) {
      type.setDataUri(ImageConverter.toDataUri(new File(faviconFolder, type.getFilename())));
    }
  }
}
