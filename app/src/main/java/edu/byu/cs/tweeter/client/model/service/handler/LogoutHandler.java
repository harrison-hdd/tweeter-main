package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Message;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.observer.LogoutObserver;

public class LogoutHandler extends TaskHandler{
    private final LogoutObserver logoutObserver;

    public LogoutHandler(LogoutObserver logoutObserver) {
        super(logoutObserver);
        this.logoutObserver = logoutObserver;
    }

    @Override
    protected String getTaskName() {
        return "logout";
    }

    @Override
    protected void handleSuccess(Message message) {
        Cache.getInstance().clearCache();
        logoutObserver.handleSuccess();
    }
}
