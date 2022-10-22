package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Message;

import edu.byu.cs.tweeter.client.backgroundTask.authenticatedTask.GetFollowersCountTask;
import edu.byu.cs.tweeter.client.model.service.observer.FollowerCountObserver;

public class FollowerCountHandler extends TaskHandler{
    private final FollowerCountObserver followerCountObserver;

    public FollowerCountHandler(FollowerCountObserver followerCountObserver) {
        super(followerCountObserver);
        this.followerCountObserver = followerCountObserver;
    }

    @Override
    protected String getTaskName() {
        return "get follower count";
    }

    @Override
    protected void handleSuccess(Message msg) {
        int count = msg.getData().getInt(GetFollowersCountTask.COUNT_KEY);
        followerCountObserver.handleSuccess(count);
    }
}
