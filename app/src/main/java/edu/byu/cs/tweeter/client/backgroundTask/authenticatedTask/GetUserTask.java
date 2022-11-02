package edu.byu.cs.tweeter.client.backgroundTask.authenticatedTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import edu.byu.cs.tweeter.client.backgroundTask.authenticatedTask.AuthenticatedTask;
import edu.byu.cs.tweeter.client.model.service.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.util.FakeData;
import request.authenticated_request.FollowRequest;
import request.authenticated_request.GetUserRequest;
import response.FollowResponse;
import response.GetUserResponse;

/**
 * Background task that returns the profile for a specified user.
 */
public class GetUserTask extends AuthenticatedTask {
    private static final String LOG_TAG = "GetUserTask";

    public static final String USER_KEY = "user";



    /**
     * Alias (or handle) for user whose profile is being retrieved.
     */
    private String alias;


    public GetUserTask(AuthToken authToken, String alias, Handler messageHandler) {
        super(messageHandler, authToken);
        this.alias = alias;
    }



    @Override
    protected void doTask() throws Exception {
        GetUserRequest request = new GetUserRequest(authToken, alias);
        GetUserResponse response = new ServerFacade().getUser(request);
        if(response.isSuccess()){
            sendSuccessMessage(response.getUser());
        }else{
            String message = response.getMessage();
            if(message == null) message = "Unknown";
            sendFailureMessage(message);
        }
    }

    private FakeData getFakeData() {
        return FakeData.getInstance();
    }

    private User getUser() {
        User user = getFakeData().findUserByAlias(alias);
        return user;
    }

    private void sendSuccessMessage(User user) {
        Bundle msgBundle = new Bundle();
        msgBundle.putBoolean(SUCCESS_KEY, true);
        msgBundle.putSerializable(USER_KEY, user);

        Message msg = Message.obtain();
        msg.setData(msgBundle);

        messageHandler.sendMessage(msg);
    }
}
