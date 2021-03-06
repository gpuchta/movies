package com.movie.index.app.model;

import com.google.gson.annotations.SerializedName;
import com.movie.index.util.GsonHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Settings {
  private static final Logger LOG = LoggerFactory.getLogger(Settings.class);

  @SerializedName("tmdb_api_key")
  protected String _tmdbApiKey;

  public String getTmdbApiKey() {
    return _tmdbApiKey;
  }

  public Settings setTmdbApiKey(String tmdbApiKey) {
    _tmdbApiKey = tmdbApiKey;
    return this;
  }

  public boolean hasTmdbApiKey() {
    return StringUtils.isNotBlank(_tmdbApiKey);
  }

  public boolean isSetupComplete() {
    return hasTmdbApiKey();
  }

  public String toJson() {
    return GsonHelper.PRETTY.toJson(this);
  }

  public static Settings fromJson(String jsonString) {
    return GsonHelper.BASIC.fromJson(jsonString, Settings.class);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((_tmdbApiKey == null) ? 0 : _tmdbApiKey.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    Settings other = (Settings) obj;
    if (_tmdbApiKey == null) {
      if (other._tmdbApiKey != null) return false;
    }
    else if (!_tmdbApiKey.equals(other._tmdbApiKey)) return false;
    return true;
  }

  public void log() {
    LOG.info("API Key         {}", _tmdbApiKey);
  }
}
