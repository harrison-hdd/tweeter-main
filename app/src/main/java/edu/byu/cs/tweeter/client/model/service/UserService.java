package edu.byu.cs.tweeter.client.model.service;

import edu.byu.cs.tweeter.client.backgroundTask.BackgroundTaskUtils;
import edu.byu.cs.tweeter.client.backgroundTask.authenticatedTask.GetUserTask;
import edu.byu.cs.tweeter.client.backgroundTask.authenticationTask.LoginTask;
import edu.byu.cs.tweeter.client.backgroundTask.authenticatedTask.LogoutTask;
import edu.byu.cs.tweeter.client.backgroundTask.authenticationTask.RegisterTask;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.handler.GetUserHandler;
import edu.byu.cs.tweeter.client.model.service.handler.LogoutHandler;
import edu.byu.cs.tweeter.client.model.service.handler.authenticationHandler.LoginHandler;
import edu.byu.cs.tweeter.client.model.service.handler.authenticationHandler.RegisterHandler;
import edu.byu.cs.tweeter.client.model.service.observer.authenticationObserver.AuthenticationObserver;
import edu.byu.cs.tweeter.client.model.service.observer.GetUserObserver;
import edu.byu.cs.tweeter.client.model.service.observer.LogoutObserver;

public class UserService {

    public UserService(){}

    public void login(String username, String password, AuthenticationObserver loginObserver){
        //is void because we're doing async programming, so we can return anything right away
        //run LoginTask in background
        LoginTask loginTask = new LoginTask(username, password, new LoginHandler(loginObserver));

        BackgroundTaskUtils.runTask(loginTask);
    }

    public void register(String firstName, String lastName, String username, String password, String image, AuthenticationObserver registerObserver){
        RegisterTask registerTask = new RegisterTask(firstName, lastName, username, password, image, new RegisterHandler(registerObserver));
        BackgroundTaskUtils.runTask(registerTask);
    }

    public void logout(LogoutObserver logoutObserver){
        LogoutTask logoutTask = new LogoutTask(Cache.getInstance().getCurrUserAuthToken(), new LogoutHandler(logoutObserver));
        BackgroundTaskUtils.runTask(logoutTask);
    }

    public void getUser(String username, GetUserObserver getUserObserver){
        GetUserTask getUserTask = new GetUserTask(Cache.getInstance().getCurrUserAuthToken(),
                username, new GetUserHandler(getUserObserver));
        BackgroundTaskUtils.runTask(getUserTask);
    }

}
