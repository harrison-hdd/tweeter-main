package edu.byu.cs.tweeter.client.backgroundTask.authenticatedTask.pagedTask;

import android.os.Handler;

import java.util.List;

import edu.byu.cs.tweeter.client.backgroundTask.authenticatedTask.pagedTask.PagedTask;
import edu.byu.cs.tweeter.client.model.service.net.ServerFacade;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.util.FakeData;
import edu.byu.cs.tweeter.util.Pair;
import request.authenticated_request.paged_service_request.GetFeedRequest;
import request.authenticated_request.paged_service_request.GetFollowersRequest;
import response.paged_service_response.GetFeedResponse;
import response.paged_service_response.GetFollowersResponse;

/**
 * Background task that retrieves a page of statuses from a user's feed.
 */
public class GetFeedTask extends PagedTask<Status> {
    private static final String LOG_TAG = "GetFeedTask";

    public GetFeedTask(AuthToken authToken, User targetUser, int limit, Status lastStatus,
                       Handler messageHandler) {
        super(messageHandler, authToken, targetUser, limit, lastStatus);
    }

    @Override
    protected void doTask() throws Exception {
        ServerFacade serverFacade = new ServerFacade();
        GetFeedRequest request = new GetFeedRequest(authToken, targetUser, lastItem, limit);
        GetFeedResponse response = serverFacade.getFeed(request);
        if(response.isSuccess()){
            sendSuccessMessage(response.getItems(), response.getHasMorePages());
        }else{
            sendFailureMessage(response.getMessage());
        }

    }
}
