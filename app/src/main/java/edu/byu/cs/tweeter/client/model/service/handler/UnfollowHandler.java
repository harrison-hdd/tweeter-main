package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Message;

import edu.byu.cs.tweeter.client.model.service.observer.UnfollowObserver;

public class UnfollowHandler extends TaskHandler{
    private final UnfollowObserver unfollowObserver;
    public UnfollowHandler(UnfollowObserver unfollowObserver){
        super(unfollowObserver);
        this.unfollowObserver = unfollowObserver;
    }
    @Override
    protected String getTaskName() {
        return "unfollow";
    }

    @Override
    protected void handleSuccess(Message message) {
        unfollowObserver.handleSuccess();
    }
}
