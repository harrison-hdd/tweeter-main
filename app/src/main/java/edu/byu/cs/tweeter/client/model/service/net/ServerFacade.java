package edu.byu.cs.tweeter.client.model.service.net;


import request.LoginRequest;
import request.RegisterRequest;
import request.authenticated_request.FollowRequest;
import request.authenticated_request.GetFolloweesCountRequest;
import request.authenticated_request.GetFollowersCountRequest;
import request.authenticated_request.GetUserRequest;
import request.authenticated_request.IsFollowerRequest;
import request.authenticated_request.LogoutRequest;
import request.authenticated_request.PostStatusRequest;
import request.authenticated_request.UnfollowRequest;
import request.authenticated_request.paged_service_request.GetFeedRequest;
import request.authenticated_request.paged_service_request.GetFolloweesRequest;
import request.authenticated_request.paged_service_request.GetFollowersRequest;
import request.authenticated_request.paged_service_request.GetStoryRequest;
import response.FollowResponse;
import response.GetFolloweesCountResponse;
import response.GetFollowersCountResponse;
import response.GetUserResponse;
import response.IsFollowerResponse;
import response.LoginResponse;
import response.LogoutResponse;
import response.PostStatusResponse;
import response.RegisterResponse;
import response.UnfollowResponse;
import response.paged_service_response.GetFeedResponse;
import response.paged_service_response.GetFolloweesResponse;
import response.paged_service_response.GetFollowersResponse;
import response.paged_service_response.GetStoryResponse;

public class ServerFacade {
    private static final String baseURLPath = "https://3v017vx0a3.execute-api.us-east-1.amazonaws.com/test/";

    private final ClientCommunicator clientCommunicator;

    public ServerFacade(){

        clientCommunicator = new ClientCommunicator(baseURLPath);
    }

    public LoginResponse login(LoginRequest request) throws Exception {
        return clientCommunicator.doPost(request, "login", LoginResponse.class);
    }

    public RegisterResponse register(RegisterRequest request)throws Exception{
        return clientCommunicator.doPost(request, "register", RegisterResponse.class);
    }

    public GetFollowersResponse getFollowers(GetFollowersRequest request) throws Exception {
        return clientCommunicator.doPost(request, "get_followers", GetFollowersResponse.class);
    }


    public GetFolloweesResponse getFollowees(GetFolloweesRequest request) throws Exception {
        return clientCommunicator.doPost(request, "get_followees", GetFolloweesResponse.class);
    }

    public GetFeedResponse getFeed(GetFeedRequest request) throws Exception{
        return clientCommunicator.doPost(request, "get_feed", GetFeedResponse.class);
    }

    public GetStoryResponse getStory(GetStoryRequest request) throws Exception{
        return clientCommunicator.doPost(request, "get_story", GetStoryResponse.class);
    }

    public FollowResponse follow(FollowRequest request)throws Exception{
        return clientCommunicator.doPost(request, "follow", FollowResponse.class);
    }

    public GetFollowersCountResponse getFollowersCount(GetFollowersCountRequest request)throws Exception{
        return clientCommunicator.doPost(request, "get_followers_count", GetFollowersCountResponse.class);
    }

    public GetFolloweesCountResponse getFolloweesCount(GetFolloweesCountRequest request)throws Exception{
        return clientCommunicator.doPost(request, "get_followees_count", GetFolloweesCountResponse.class);
    }

    public GetUserResponse getUser(GetUserRequest request) throws Exception{
        return clientCommunicator.doPost(request, "get_user", GetUserResponse.class);
    }

    public IsFollowerResponse isFollower(IsFollowerRequest request) throws Exception{
        return clientCommunicator.doPost(request, "is_follower", IsFollowerResponse.class);
    }

    public LogoutResponse logout(LogoutRequest request) throws Exception{
        return clientCommunicator.doPost(request, "logout", LogoutResponse.class);
    }

    public PostStatusResponse postStatus(PostStatusRequest request) throws Exception{
        return clientCommunicator.doPost(request, "post_status", PostStatusResponse.class);
    }

    public UnfollowResponse unfollow(UnfollowRequest request) throws Exception{
        return clientCommunicator.doPost(request, "unfollow", UnfollowResponse.class);
    }
}
