package com.alterego.lifesumtest.app;

import android.app.Activity;
import android.app.Application;

import com.alterego.advancedandroidlogger.implementations.NullAndroidLogger;
import com.alterego.advancedandroidlogger.interfaces.IAndroidLogger;
import com.alterego.lifesumtest.app.api.LifesumApiManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(prefix="m")
public class SettingsManager {

    private final ImageLoaderConfiguration mImageLoaderConfiguration;
    @Getter private final LifesumApiManager mLifesumApiManager;

    @Getter Activity mParentActivity;
    @Getter @Setter Application mParentApplication;
    @Getter @Setter IAndroidLogger mLogger = NullAndroidLogger.instance;
    @Getter private Gson mGson = new GsonBuilder().create();
    //@Getter private Gson mGson = new GsonBuilder().registerTypeAdapter(DateTime.class, dateSerializer).create();

    public SettingsManager(Application app, IAndroidLogger logger, ImageLoaderConfiguration imageLoaderConfig) {
        setLogger(logger);
        setParentApplication(app);
        mImageLoaderConfiguration = imageLoaderConfig;
        //TODO setupDatabase(app);
        mLifesumApiManager = new LifesumApiManager(this);
    }

    public void setParentActivity(Activity act) {
        mParentActivity = act;
    }



}
