package com.alterego.lifesumtest.app.api;


import com.alterego.lifesumtest.app.data.LifesumResponse;

import java.util.Map;

import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.QueryMap;
import rx.Observable;

/**
 * This is the API for building Lifesum app.
 * Every call needs a valid token.
 *
 * For the moment it has only one call, search.
 *
 */
public interface ILifesumApi {

    /**
     * This call executes the food search and retrieves the found items
     *
     * @param authorization String Client token
     * @param params Map<String, String> with the search parameters
     *
     * @return {@link com.alterego.lifesumtest.app.data.LifesumResponse} result as an {@link rx.Observable}
     */
    @GET("/search/query")
    Observable<LifesumResponse> getSearchResults(@Header("Authorization") String authorization, @QueryMap Map<String, String> params);

}
