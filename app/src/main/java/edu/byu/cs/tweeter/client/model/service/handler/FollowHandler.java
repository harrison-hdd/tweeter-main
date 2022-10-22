package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Looper;
import android.os.Message;

import edu.byu.cs.tweeter.client.model.service.observer.FollowObserver;

public class FollowHandler extends TaskHandler{
    private final FollowObserver followObserver;

    public FollowHandler(FollowObserver followObserver){
        super(followObserver);
        this.followObserver = followObserver;
    }

    @Override
    protected String getTaskName() {
        return "follow";
    }

    @Override
    protected void handleSuccess(Message message) {
        followObserver.handleSuccess();
    }
}
