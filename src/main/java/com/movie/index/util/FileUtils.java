package com.movie.index.util;

import java.io.File;
import java.io.IOException;

import com.movie.index.exception.MovieIOException;

public class FileUtils {

  public static File makeDir(File folder) throws MovieIOException {
    try {
      org.apache.commons.io.FileUtils.forceMkdir(folder);
    }
    catch (IOException e) {
      throw new MovieIOException(e);
    }
    return folder;
  }

  public static File makeDir(File folder, String folderName) throws MovieIOException {
    File newFolder = new File(folder, folderName);
    try {
      org.apache.commons.io.FileUtils.forceMkdir(newFolder);
    }
    catch (IOException e) {
      throw new MovieIOException(folder.getAbsolutePath(), e);
    }
    return newFolder;
  }
}
