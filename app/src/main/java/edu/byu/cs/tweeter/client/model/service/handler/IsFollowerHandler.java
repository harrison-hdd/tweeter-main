package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Message;

import edu.byu.cs.tweeter.client.backgroundTask.authenticatedTask.IsFollowerTask;
import edu.byu.cs.tweeter.client.model.service.observer.IsFollowerObserver;

public class IsFollowerHandler extends TaskHandler{
    private final IsFollowerObserver isFollowerObserver;

    public IsFollowerHandler(IsFollowerObserver isFollowerObserver){
        super(isFollowerObserver);
        this.isFollowerObserver = isFollowerObserver;
    }

    @Override
    protected String getTaskName() {
        return "determine following relationship";
    }

    @Override
    protected void handleSuccess(Message msg) {
        boolean isFollower = msg.getData().getBoolean(IsFollowerTask.IS_FOLLOWER_KEY);
        isFollowerObserver.handleSuccess(isFollower);
    }
}
