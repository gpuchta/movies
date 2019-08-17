package com.movie.index.app.model;

//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import com.google.gson.annotations.SerializedName;

public class LocatorFactory {

//  private static final String STREAMING = "streaming";
//
//  private static class LocatorBinder implements Locator {
//    @SerializedName("type")
//    private final Type _type = Type.BINDER;
//
//    @SerializedName("binder")
//    private String _binder;
//
//    @SerializedName("page")
//    private String _page;
//
//    @SerializedName("provider")
//    private String _provider;
//
//    @SerializedName(STREAMING)
//    private boolean _streaming;
//
//    @Override
//    public String toString() {
//      String result = _binder + _page;
//      if(_streaming) {
//        result += ":" + STREAMING;
//      }
//      return result;
//    }
//  }
//
//  private static class LocatorProvider implements Locator {
//    @SerializedName("type")
//    private final Type _type = Type.PROVIDER;
//
//    @SerializedName("provider")
//    private String _provider;
//
//    @Override
//    public String toString() {
//      return _provider;
//    }
//  }
//
//  public static Locator createBinder(String binder, String page, boolean streaming) {
//    LocatorBinder locator = new LocatorBinder();
//    locator._binder = binder;
//    locator._page = page;
//    locator._streaming = streaming;
//    return locator;
//  }
//
//  public static Locator createBinder(String binder, String page) {
//    return createBinder(binder, page, false);
//  }
//
//  public static Locator createProvider(String provider) {
//    LocatorProvider locator = new LocatorProvider();
//    locator._provider = provider;
//    return locator;
//  }
//
//  private static Pattern BINDER_PATTERN = Pattern.compile("(?<binder>[A-Z])(?<page>[0-9]+)(:(?<" + STREAMING + ">" + STREAMING + "))?");
//
//  public static Locator create(String locator) {
//    if(locator == null) {
//      return null;
//    }
//
//    Matcher matcher = BINDER_PATTERN.matcher(locator);
//    if(matcher.matches()) {
//      return LocatorFactory.createBinder(
//          matcher.group("binder"),
//          matcher.group("page"),
//          STREAMING.equals(matcher.group(STREAMING)));
//    }
//    else {
//      return LocatorFactory.createProvider(locator);
//    }
//  }
}
