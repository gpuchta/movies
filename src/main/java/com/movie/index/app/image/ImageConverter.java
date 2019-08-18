package com.movie.index.app.image;

import com.movie.index.exception.MovieException;
import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.movie.index.util.Arguments.arg;

public class ImageConverter {

  public enum Format {
    JPG("jpg"),
    PNG("png");

    private static final Map<String, Object> FILE_EXTENSIONS =
        arg("jpg", JPG).
        arg("jpeg", JPG).
        arg("png", PNG);

    private String _name;

    private Format(String name) {
      _name = name;
    }

    @Override
    public String toString() {
      return _name;
    }

    public static Format fromFileExtension(String extension) {
      return (Format)FILE_EXTENSIONS.get(extension);
    }
  }

  /**
   * Fetch the image for the given URL, scale
   * it and convert it to a base64 encoded
   * data URI.
   *
   * @param url Image url to fetch and convert
   * @return base64 encoded data URI
   */
  public static String toDataUri(URL url, Format format, int width) {
    try {
      BufferedImage sourceImage = ImageIO.read(url);
      return imageToDataUri(sourceImage, format, width);
    }
    catch (Throwable e) {
      throw new MovieException(e);
    }
  }

  /**
   * Fetch the image for the given classpath resource,
   * scale it and convert it to a base64 encoded data
   * URI.
   *
   * @param url Image classpath resource to fetch and
   *   convert
   * @return base64 encoded data URI
   */
  public static String toDataUri(String resourceName) {
    InputStream inputStream = null;
    try {
      inputStream = ImageConverter.class.getResourceAsStream(resourceName);
      BufferedImage sourceImage = ImageIO.read(inputStream);
      return imageToDataUri(sourceImage, Format.fromFileExtension(FilenameUtils.getExtension(resourceName)));
    }
    catch (Throwable e) {
      throw new MovieException(e);
    }
    finally {
      try {
        if(inputStream != null) {
          inputStream.close();
        }
      }
      catch (Throwable e) {
        throw new MovieException(e);
      }
    }
  }

  /**
   * Fetch the image for the given file resource
   * and convert it to a base64 encoded data URI.
   *
   * @param url Image file to fetch and convert
   * @return base64 encoded data URI
   */
  public static String toDataUri(File file) {
    InputStream inputStream = null;
    try {
      inputStream = new FileInputStream(file);
      BufferedImage sourceImage = ImageIO.read(inputStream);
      return imageToDataUri(sourceImage, Format.fromFileExtension(FilenameUtils.getExtension(file.getName())));
    }
    catch (Throwable e) {
      throw new MovieException(e);
    }
    finally {
      try {
        if(inputStream != null) {
          inputStream.close();
        }
      }
      catch (Throwable e) {
        throw new MovieException(e);
      }
    }
  }

  /**
   * Convert given BufferedImage into a data uri string.
   *
   * @param image image to convert
   * @param width width to scale image to before converting
   * @return data uri of scaled image
   * @throws IOException
   */
  public static String toDataUri(BufferedImage image, int width) throws IOException {
    return imageToDataUri(image, Format.JPG, width);
  }

  private static String imageToDataUri(BufferedImage image, Format format) throws IOException {
    return imageToDataUri(image, format, -1);
  }

  /**
   * Scale image and convert to base64 data uri.
   *
   * @param image image to scale and convert
   * @param format image format.
   * @return base64 data uri
   * @throws IOException
   * @see {@link Format}
   */
  private static String imageToDataUri(BufferedImage image, Format format, int maxWidth) throws IOException {
    if(maxWidth > 10) {
      Image thumbnail = image.getScaledInstance(maxWidth, -1, Image.SCALE_SMOOTH);
      BufferedImage bufferedThumbnail = new BufferedImage(thumbnail.getWidth(null),
          thumbnail.getHeight(null),
          BufferedImage.TYPE_INT_RGB);
      bufferedThumbnail.getGraphics().drawImage(thumbnail, 0, 0, null);
      image = bufferedThumbnail;
    }

    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    ImageIO.write(image, format.toString(), buffer);

    return String.format("data:image/%s;base64,%s",
        format.toString(),
        Base64.getEncoder().encodeToString(buffer.toByteArray()));
  }

  private static final Pattern DATA_URI = Pattern.compile("data:image/(?<format>[^;]+);base64,(?<base64Image>.+)");

  /**
   * Convert given data uri into a buffered image.
   *
   * @param dataUri data uri to convert
   * @return buffered image
   * @throws IOException
   */
  public static Optional<BufferedImage> fromDataUri(String dataUri) throws IOException {
    Matcher matcher = DATA_URI.matcher(dataUri);
    if(matcher.matches()) {
      String base64Image = matcher.group("base64Image");

      byte[] image = Base64.getDecoder().decode(base64Image);
      return Optional.of(ImageIO.read(new ByteArrayInputStream(image)));
    }
    return Optional.empty();
  }
}
