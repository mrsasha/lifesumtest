package com.alterego.lifesumtest.app.api;

import com.alterego.advancedandroidlogger.interfaces.IAndroidLogger;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;

class LifesumApiErrorHandler implements ErrorHandler {

    private final IAndroidLogger mLogger;

    LifesumApiErrorHandler(IAndroidLogger logger) {
        mLogger = logger;
    }

    @Override
    public Throwable handleError(RetrofitError cause) {
        try {
            return new LifesumApiException(cause);
        } catch (Exception e) {
            mLogger.error("LifesumApiErrorHandler cannot read body, error = " + cause);
        }
        return cause;
    }



    private class LifesumApiException extends Throwable {

        public LifesumApiException(RetrofitError cause) {
            try {
                mLogger.error("LifesumApiErrorHandler error = " + cause.getMessage() + ", response = " + cause.getResponse().getBody().in().toString());
            } catch (Exception e) {
                mLogger.error("LifesumApiErrorHandler error = " + cause.getMessage() + ", can't read the response!");
            }
        }
    }
}