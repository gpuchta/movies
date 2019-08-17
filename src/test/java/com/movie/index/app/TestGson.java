package com.movie.index.app;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class TestGson {

  static class Type1 {
    @SerializedName("key1")
    protected String _key1;
    @SerializedName("key2")
    protected int _key2;
  }

  static class Type2 {
    @SerializedName("key3")
    protected String _key3;
    @SerializedName("key4")
    protected boolean _key4;
  }


  @Test
  public void testMerge() {
    Gson gson = new Gson();

    Type1 t1 = new Type1();
    t1._key1 = "type-1-value-1";
    t1._key2 = 13;

    Type2 t2 = new Type2();
    t2._key3 = "type-2-value-3";
    t2._key4 = true;

    HashMap t1map = gson.fromJson(gson.toJson(t1), HashMap.class);
    HashMap t2map = gson.fromJson(gson.toJson(t2), HashMap.class);

    Map mergeMap = new HashMap();
    mergeMap.putAll(t1map);
    mergeMap.putAll(t2map);

    String json = gson.toJson(mergeMap);

    System.out.println(json);

    t1 = gson.fromJson(json, Type1.class);
    t2 = gson.fromJson(json, Type2.class);

    System.out.println(t1._key1);
    System.out.println(t1._key2);

    System.out.println(t2._key3);
    System.out.println(t2._key4);
  }
}
