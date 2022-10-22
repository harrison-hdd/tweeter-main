package edu.byu.cs.tweeter.client.model.service.handler.authenticationHandler;

import android.os.Message;

import edu.byu.cs.tweeter.client.backgroundTask.authenticationTask.LoginTask;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.handler.TaskHandler;
import edu.byu.cs.tweeter.client.model.service.observer.authenticationObserver.AuthenticationObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class LoginHandler extends AuthenticationHandler {

    public LoginHandler(AuthenticationObserver loginObserver){
        super(loginObserver);
    }


    @Override
    protected String getTaskName() {
        return "login";
    }

}
