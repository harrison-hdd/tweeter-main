package edu.byu.cs.tweeter.client.model.service.handler.authenticationHandler;

import android.os.Message;

import edu.byu.cs.tweeter.client.backgroundTask.authenticationTask.LoginTask;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.handler.TaskHandler;

import edu.byu.cs.tweeter.client.model.service.observer.authenticationObserver.AuthenticationObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class AuthenticationHandler extends TaskHandler {
    AuthenticationObserver observer;
    protected AuthenticationHandler(AuthenticationObserver observer) {
        super(observer);
        this.observer = observer;
    }

    @Override
    protected void handleSuccess(Message msg) {
        User loggedInUser = (User) msg.getData().getSerializable(LoginTask.USER_KEY);
        AuthToken authToken = (AuthToken) msg.getData().getSerializable(LoginTask.AUTH_TOKEN_KEY);

        Cache.getInstance().setCurrUser(loggedInUser);
        Cache.getInstance().setCurrUserAuthToken(authToken);

        observer.handleSuccess(loggedInUser);
    }
}
