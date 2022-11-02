package edu.byu.cs.tweeter.client.backgroundTask.authenticatedTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import edu.byu.cs.tweeter.client.backgroundTask.authenticatedTask.AuthenticatedTask;
import edu.byu.cs.tweeter.client.model.service.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import request.authenticated_request.FollowRequest;
import request.authenticated_request.LogoutRequest;
import response.FollowResponse;
import response.LogoutResponse;

/**
 * Background task that logs out a user (i.e., ends a session).
 */
public class LogoutTask extends AuthenticatedTask {
    private static final String LOG_TAG = "LogoutTask";


    public LogoutTask(AuthToken authToken, Handler messageHandler) {
        super(messageHandler, authToken);
    }

    @Override
    protected void doTask() throws Exception {
        LogoutRequest request = new LogoutRequest(authToken);
        LogoutResponse response = new ServerFacade().logout(request);
        if(response.isSuccess()){
            sendSuccessMessage();
        }else{
            String message = response.getMessage();
            if(message == null) message = "Unknown";
            sendFailureMessage(message);
        }
    }

    private void sendSuccessMessage() {
        Bundle msgBundle = new Bundle();
        msgBundle.putBoolean(SUCCESS_KEY, true);

        Message msg = Message.obtain();
        msg.setData(msgBundle);

        messageHandler.sendMessage(msg);
    }

}
