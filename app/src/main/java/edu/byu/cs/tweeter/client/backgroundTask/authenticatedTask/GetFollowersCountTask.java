package edu.byu.cs.tweeter.client.backgroundTask.authenticatedTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import edu.byu.cs.tweeter.client.backgroundTask.authenticatedTask.AuthenticatedTask;
import edu.byu.cs.tweeter.client.model.service.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import request.authenticated_request.FollowRequest;
import request.authenticated_request.GetFollowersCountRequest;
import response.FollowResponse;
import response.GetFollowersCountResponse;

/**
 * Background task that queries how many followers a user has.
 */
public class GetFollowersCountTask extends AuthenticatedTask {
    private static final String LOG_TAG = "GetFollowersCountTask";

    public static final String COUNT_KEY = "count";

    /**
     * The user whose follower count is being retrieved.
     * (This can be any user, not just the currently logged-in user.)
     */
    private User targetUser;


    public GetFollowersCountTask(AuthToken authToken, User targetUser, Handler messageHandler) {
        super(messageHandler, authToken);
        this.targetUser = targetUser;
    }



    @Override
    protected void doTask() throws Exception {
        GetFollowersCountRequest request = new GetFollowersCountRequest(authToken, targetUser);
        GetFollowersCountResponse response = new ServerFacade().getFollowersCount(request);
        if(response.isSuccess()){
            sendSuccessMessage(response.getFollowersCount());
        }else{
            String message = response.getMessage();
            if(message == null) message = "Unknown";
            sendFailureMessage(message);
        }

    }

    private void sendSuccessMessage(int count) {
        Bundle msgBundle = new Bundle();
        msgBundle.putBoolean(SUCCESS_KEY, true);
        msgBundle.putInt(COUNT_KEY, count);

        Message msg = Message.obtain();
        msg.setData(msgBundle);

        messageHandler.sendMessage(msg);
    }

}
