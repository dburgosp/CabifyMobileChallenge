package com.example.android.cabifymobilechallenge.common.presenter;

/**
 * Custom callback class for receiving errors and results in the Presenters from the Interactors.
 */
public abstract class Callback<T> {
    public abstract void returnResult(T t);

    public abstract void returnError(String message);
}
