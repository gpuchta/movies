package com.movie.index.db.dao;

import com.movie.index.app.model.Settings;
import com.movie.index.util.GsonHelper;

public class SettingsBuilder extends Settings {

  @Override
  public SettingsBuilder setTmdbApiKey(String tmdbApiKey) {
    _tmdbApiKey = tmdbApiKey;
    return this;
  }

  public Settings build() {
    return GsonHelper.BASIC.fromJson(GsonHelper.BASIC.toJson(this), Settings.class);
  }

  public static Settings build(String json) {
    return GsonHelper.BASIC.fromJson(json, Settings.class);
  }
}
