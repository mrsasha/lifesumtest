package com.alterego.lifesumtest.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.alterego.advancedandroidlogger.implementations.NullAndroidLogger;
import com.alterego.advancedandroidlogger.interfaces.IAndroidLogger;
import com.alterego.lifesumtest.DaoMaster;
import com.alterego.lifesumtest.DaoSession;
import com.alterego.lifesumtest.LifesumItem;
import com.alterego.lifesumtest.LifesumItemDao;
import com.alterego.lifesumtest.LifesumSearchDaoEntryDao;
import com.alterego.lifesumtest.app.api.LifesumApiManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import rx.Observable;
import rx.Subscriber;

@Accessors(prefix="m")
public class SettingsManager {

    private final ImageLoaderConfiguration mImageLoaderConfiguration;
    @Getter private final LifesumApiManager mLifesumApiManager;

    @Getter Activity mParentActivity;
    @Getter @Setter Application mParentApplication;
    @Getter @Setter IAndroidLogger mLogger = NullAndroidLogger.instance;
    @Getter private Gson mGson = new GsonBuilder().create();
    @Getter private LifesumItemDao mLifesumItemDao;
    @Getter private LifesumSearchDaoEntryDao mLifesumSearchDaoEntryDao;

    public SettingsManager(Application app, IAndroidLogger logger, ImageLoaderConfiguration imageLoaderConfig) {
        setLogger(logger);
        setParentApplication(app);
        mImageLoaderConfiguration = imageLoaderConfig;
        setupDatabase(app);
        mLifesumApiManager = new LifesumApiManager(this);
    }

    public void setParentActivity(Activity act) {
        mParentActivity = act;
    }

    private void setupDatabase(Context ctx) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(ctx, "lifesumtest-db", null);
        SQLiteDatabase database = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
        DaoSession daoSession = daoMaster.newSession();
        mLifesumItemDao = daoSession.getLifesumItemDao();
        mLifesumSearchDaoEntryDao = daoSession.getLifesumSearchDaoEntryDao();
    }

    /********************* DAO MANAGEMENT *******************************/

    public Observable<List<LifesumItem>> loadLifesumItems() {
        return Observable.create(new Observable.OnSubscribe<List<LifesumItem>>() {
            @Override
            public void call(Subscriber<? super List<LifesumItem>> subscriber) {
                try {
                    List<LifesumItem> items = mLifesumItemDao.loadAll();
                    mLogger.info("SettingsManager loadLifesumItems size = " + items.size());
                    subscriber.onNext(items);
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    public Observable<Boolean> storeLifesumItems(final List<LifesumItem> items) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                try {
                    for (LifesumItem item : items) {
                        mLifesumItemDao.insert(item);
                    }
                    mLogger.info("SettingsManager storeLifesumItems successful, new items added size = " + items.size());
                    subscriber.onNext(true);
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    public Observable<Boolean> deleteLifesumItems(final List<LifesumItem> items) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                try {
                    for (LifesumItem item : items) {
                        mLifesumItemDao.delete(item);
                    }
                    mLogger.info("SettingsManager deleteLifesumItems successful, removed items size = " + items.size());
                    subscriber.onNext(true);
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }

}
