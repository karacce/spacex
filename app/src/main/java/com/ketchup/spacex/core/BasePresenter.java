package com.ketchup.spacex.core;

public interface BasePresenter<T> {
    void takeView(T view);
    void dropView();
}
