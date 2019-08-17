package com.movie.index.app.image;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Optional;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TestImageConverter {

  private static final String DATA_URI = "data:image/jpeg;base64,"
      + "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"
      + "Ry8TCcyZzMZTA//9k=";

  private static final String EXPECTED_DATA_URI = "data:image/jpg;base64,"
      + "/9j/4AAQSkZJRgABAgAAAQABAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0aHBwgJC4nICIsIx"
      + "wcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIy"
      + "MjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wAARCABbAEYDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAw"
      + "QFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkK"
      + "FhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmq"
      + "KjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEB"
      + "AQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRob"
      + "HBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOE"
      + "hYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+P"
      + "n6/9oADAMBAAIRAxEAPwD2OKHFWkip0ceKsKtAEaxfSneWfb8qlC07FAEOw+35UhjPt+VT4oxQBVaM+1QvFV8rmo2S"
      + "gDLeHnpRVxo+TxRQBOi/N+FSgU1RhvwqSgAorJvtW8p3ihxlTgsRn8hWW1/cSthriRc+hwP0oA6qiuT+3TqBtmk6ck"
      + "uTz+dTR6jebdyzHg4O7GP1oA6XFIRWZaavuIS4Cj/bQ8fiO1avWgCAr8xoqTHJ+tFACgfN+FJLIsUTSN91Rk0o+8fp"
      + "Q6LIjIwyrDBFAHKyHzLh5VQDeSwVj6/zqNpGU7ZIk+hTH8qu3On3VmW8sGSH6Z/MVWW5AG0owHoG4/I5oAhXyxksGP"
      + "oAcU7dGeCjKPZqkM6npsz/ALUS/wBKcrsQPnUZ7RRDP8v60AQCI8k8J/e9a6232m2i2tuXaMH14rDh02e6Yb1aKLqS"
      + "5yzVvRoscaoowqjAFAB3P1ooHU/WigBB978KZcz/AGa3aYqWC9QKcp+b8KWSNJomjcZVhg0AU4dYtJeC5jPo4xVkxW"
      + "1yu4xxyD1wDWbNoMZyYZSvs3NUX0u+t2yik+8bUAa8uj2ko+VDGfVTWbJp1xYXMckUm5dw+bp+dQ7dT6Yuv1qza6fe"
      + "TTo9yzKisDhjyce1AG9RRRQA3ufrRTSfmNFAEUbc/hU4OaoRS5qwr0AWKWow9ODCgB1FJuFIXFADqaWppao2cAUADN"
      + "hjRVZ5OetFAGfFPwOauRz8VhRMcjmrsTHPWgDXWYEdakEo9azUY+tTAmgC75g9aQyj1qpuPrSZNAFlpqgeaomJxVaR"
      + "j60APlmwetFUJGO7rRQB/9k=";


  @Test
  public void testFromDataUri() throws IOException {
    Optional<BufferedImage> bufferedImage = ImageConverter.fromDataUri(DATA_URI);
    Assert.assertTrue(bufferedImage.isPresent());
    Assert.assertEquals(bufferedImage.get().getWidth(), 160);
    Assert.assertEquals(bufferedImage.get().getHeight(), 208);

    String dataUri = ImageConverter.toDataUri(bufferedImage.get(), 70);
    bufferedImage = ImageConverter.fromDataUri(dataUri);
    Assert.assertTrue(bufferedImage.isPresent());
    Assert.assertEquals(bufferedImage.get().getWidth(), 70);
    Assert.assertEquals(bufferedImage.get().getHeight(), 91);
    Assert.assertEquals(dataUri, EXPECTED_DATA_URI);
  }
}
