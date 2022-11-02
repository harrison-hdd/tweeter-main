package edu.byu.cs.tweeter.client.backgroundTask.authenticatedTask.pagedTask;

import android.os.Handler;

import java.util.List;

import edu.byu.cs.tweeter.client.backgroundTask.authenticatedTask.pagedTask.PagedTask;
import edu.byu.cs.tweeter.client.model.service.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.util.FakeData;
import edu.byu.cs.tweeter.util.Pair;
import request.authenticated_request.paged_service_request.GetFolloweesRequest;
import request.authenticated_request.paged_service_request.GetFollowersRequest;
import response.paged_service_response.GetFolloweesResponse;
import response.paged_service_response.GetFollowersResponse;

/**
 * Background task that retrieves a page of followers.
 */
public class GetFollowersTask extends PagedTask<User> {

    public GetFollowersTask(AuthToken authToken, User targetUser, int limit, User lastFollower,
                            Handler messageHandler) {
        super(messageHandler, authToken, targetUser, limit, lastFollower);
    }


    @Override
    protected void doTask() throws Exception {
        ServerFacade serverFacade = new ServerFacade();
        GetFollowersRequest request = new GetFollowersRequest(authToken, targetUser, lastItem, limit);
        GetFollowersResponse response = serverFacade.getFollowers(request);
        if(response.isSuccess()){
            sendSuccessMessage(response.getItems(), response.getHasMorePages());
        }else{
            sendFailureMessage(response.getMessage());
        }
    }
}
