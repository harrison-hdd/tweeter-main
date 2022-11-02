package edu.byu.cs.tweeter.client.backgroundTask.authenticationTask;

import android.os.Handler;

import edu.byu.cs.tweeter.client.backgroundTask.authenticationTask.AuthenticationTask;
import edu.byu.cs.tweeter.client.model.service.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.util.FakeData;
import edu.byu.cs.tweeter.util.Pair;
import request.LoginRequest;
import response.LoginResponse;

/**
 * Background task that logs in a user (i.e., starts a session).
 */
public class LoginTask extends AuthenticationTask {

    private static final String LOG_TAG = "LoginTask";




    public LoginTask(String username, String password, Handler messageHandler) {
        super(messageHandler, username, password, LOG_TAG);
    }

    @Override
    protected void doTask() throws Exception {
            LoginRequest request = new LoginRequest(username, password);
            ServerFacade serverFacade = new ServerFacade();
            LoginResponse response = serverFacade.login(request);
            if (response.isSuccess()) {
                sendSuccessMessage(response.getUser(), response.getAuthToken());
            } else {
                String message = response.getMessage();
                if (message == null) message = "Unknown";
                sendFailureMessage(message);
            }
    }
}
