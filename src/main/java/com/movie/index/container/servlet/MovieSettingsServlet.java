package com.movie.index.container.servlet;

import com.google.gson.annotations.SerializedName;
import com.movie.index.app.environment.EnvironmentManager;
import com.movie.index.app.model.Environment;
import com.movie.index.app.model.Settings;
import com.movie.index.util.GsonHelper;
import org.apache.http.HttpStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@SuppressWarnings("serial")
public class MovieSettingsServlet extends AbstractHttpServlet {

  static class AllSettings {

    @SerializedName("environment")
    Environment _environment;

    @SerializedName("settings")
    Settings _settings;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    try {
      resp.setStatus(HttpStatus.SC_OK);
      AllSettings configuration = new AllSettings();
      configuration._environment = getEnvironment();
      configuration._settings = getSettings();
      write(resp, GsonHelper.PRETTY.toJson(configuration));
    }
    catch(Throwable e) {
      resp.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }
  }

  @Override
  protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    try {
      String jsonString = getInputAsString(req);

      AllSettings allSettings = GsonHelper.BASIC.fromJson(jsonString, AllSettings.class);

      // first persist environment which may switch to
      // another data folder
      Environment environment = allSettings._environment;
      File newDataFolder = environment.getDataFolder();
      if(!newDataFolder.exists() || !newDataFolder.isDirectory()) {
        resp.setStatus(HttpStatus.SC_BAD_REQUEST);
        write(resp, "path to data folder is not valid: %s", newDataFolder.getAbsolutePath());
        return;
      }

      boolean doReload = false;

      if(!newDataFolder.equals(getEnvironment().getDataFolder())) {
        EnvironmentManager.store(environment, getConfig().getConfigFolder());
        setEnvironment(environment);
        doReload = true;
      }

      // now persist settings as we may have switched
      // to a database in another folder
      if(!allSettings._settings.equals(getSettings())) {
        getDaoFactory().getSettingsDao().persist(allSettings._settings);
        setSettings(allSettings._settings);
        doReload = true;
      }

      if(doReload) {
        reloadServiceManager();
      }
    }
    catch(Throwable e) {
      resp.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }
  }


}
