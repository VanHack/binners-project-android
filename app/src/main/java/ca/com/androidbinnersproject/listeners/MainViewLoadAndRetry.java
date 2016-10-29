package ca.com.androidbinnersproject.listeners;

import java.util.List;

/**
 * Created by jonathan_campos on 28/10/2016.
 */

public interface MainViewLoadAndRetry<T> {

    void onShowProgress();

    void onHideProgress();

    void onShowRetry();

    void onHideRetry();

    void onRenderList(List<T> obj);

    void onErrorMessage(String message);
}
