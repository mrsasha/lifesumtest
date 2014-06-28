package com.alterego.lifesumtest.app.api;

import com.alterego.lifesumtest.app.R;
import com.alterego.lifesumtest.app.SettingsManager;
import com.alterego.lifesumtest.app.data.LifesumResponse;

import java.util.HashMap;

import retrofit.RestAdapter;
import retrofit.android.AndroidLog;
import retrofit.converter.GsonConverter;
import rx.Observable;
import rx.subjects.PublishSubject;

public class LifesumApiManager {

    public static final String LIFESUM_TOKEN = "a794ecd348a3f71894426c65c37fea35da89a295bcbad687ca68a96fbfc7d371";
    public static final String LIFESUM_TOKEN2 = "5791569:a794ecd348a3f71894426c65c37fea35da89a295bcbad687ca68a96fbfc7d371";
    public static final String LIFESUM_TOKEN3 = "6764086:1bbe7ae40fe214c158212559f7c038abc0ced409a66fe79043945a2ca4fd99ed";

    private final ILifesumApi mLifesumService;
    private final SettingsManager mSettingsManager;
    private PublishSubject<Object> mFoodSearchSubject;

    public LifesumApiManager (SettingsManager mgr) {

        mSettingsManager = mgr;

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setConverter(new GsonConverter(mSettingsManager.getGson()))
                .setEndpoint(mSettingsManager.getParentApplication().getResources().getString(R.string.lifesum_server))
                .setErrorHandler(new LifesumApiErrorHandler(mSettingsManager.getLogger()))
                .setLogLevel(RestAdapter.LogLevel.FULL).setLog(new AndroidLog("LifesumApiManager"))
                .build();

        mLifesumService = restAdapter.create(ILifesumApi.class);

    }

    public Observable<LifesumResponse> doFoodSearch (String food_name) {

        mSettingsManager.getLogger().debug("LifesumApiManager doFoodSearch looking for = " + food_name);
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("type", "food");
        params.put("search", food_name);
        return mLifesumService.getSearchResults(LIFESUM_TOKEN, params);

    }

}
