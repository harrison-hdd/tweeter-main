package edu.byu.cs.tweeter.client.backgroundTask.authenticatedTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import edu.byu.cs.tweeter.client.backgroundTask.authenticatedTask.AuthenticatedTask;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import request.authenticated_request.FollowRequest;
import response.FollowResponse;

/**
 * Background task that establishes a following relationship between two users.
 */
public class FollowTask extends AuthenticatedTask {
    private static final String LOG_TAG = "FollowTask";



    /**
     * The user that is being followed.
     */
    private User followee;


    public FollowTask(AuthToken authToken, User followee, Handler messageHandler) {
        super(messageHandler, authToken);
        this.followee = followee;
    }

//    @Override
//    public void run() {
//        try {
//
//            sendSuccessMessage();
//
//        } catch (Exception ex) {
//            Log.e(LOG_TAG, ex.getMessage(), ex);
//            sendExceptionMessage(ex);
//        }
//    }

    @Override
    protected void doTask() throws Exception {
        FollowRequest request = new FollowRequest(authToken, Cache.getInstance().getCurrUser(), followee);
        FollowResponse response = new ServerFacade().follow(request);
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
