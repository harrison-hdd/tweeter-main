package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Message;

import edu.byu.cs.tweeter.client.backgroundTask.authenticatedTask.GetFollowingCountTask;
import edu.byu.cs.tweeter.client.model.service.observer.FollowingCountObserver;

public class FollowingCountHandler extends TaskHandler{
    private final FollowingCountObserver followingCountObserver;

    public FollowingCountHandler(FollowingCountObserver followingCountObserver) {
        super(followingCountObserver);
        this.followingCountObserver = followingCountObserver;
    }

    @Override
    protected String getTaskName() {
        return "get following count";
    }

    @Override
    protected void handleSuccess(Message msg) {
        int count = msg.getData().getInt(GetFollowingCountTask.COUNT_KEY);
        followingCountObserver.handleSuccess(count);
    }
}
