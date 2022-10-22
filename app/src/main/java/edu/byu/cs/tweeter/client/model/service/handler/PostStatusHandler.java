package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Message;

import edu.byu.cs.tweeter.client.model.service.observer.PostStatusObserver;

public class PostStatusHandler extends TaskHandler{
    private final PostStatusObserver postStatusObserver;
    public PostStatusHandler(PostStatusObserver postStatusObserver){
        super(postStatusObserver);
        this.postStatusObserver = postStatusObserver;
    }

    @Override
    protected String getTaskName() {
        return "post status";
    }

    @Override
    protected void handleSuccess(Message message) {
        postStatusObserver.handleSuccess();
    }
}
