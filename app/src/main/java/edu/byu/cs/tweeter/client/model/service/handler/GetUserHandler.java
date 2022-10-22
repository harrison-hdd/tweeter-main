package edu.byu.cs.tweeter.client.model.service.handler;

import android.os.Message;

import edu.byu.cs.tweeter.client.backgroundTask.authenticatedTask.GetUserTask;
import edu.byu.cs.tweeter.client.model.service.observer.GetUserObserver;
import edu.byu.cs.tweeter.model.domain.User;

public class GetUserHandler extends TaskHandler{
    private final GetUserObserver getUserObserver;

    public GetUserHandler(GetUserObserver getUserObserver) {
        super(getUserObserver);
        this.getUserObserver = getUserObserver;
    }

    @Override
    protected String getTaskName() {
        return "get user";
    }

    @Override
    protected void handleSuccess(Message msg) {
        User user = (User) msg.getData().getSerializable(GetUserTask.USER_KEY);
        getUserObserver.handleSuccess(user);
    }
}
