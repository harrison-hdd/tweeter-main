package edu.byu.cs.tweeter.client;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

import edu.byu.cs.tweeter.client.model.service.net.ServerFacade;
import edu.byu.cs.tweeter.client.model.service.net.remote_exception.BadRequestException;
import edu.byu.cs.tweeter.client.model.service.net.remote_exception.RemoteException;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.util.FakeData;
import request.LoginRequest;
import request.RegisterRequest;
import request.authenticated_request.GetFolloweesCountRequest;
import request.authenticated_request.GetFollowersCountRequest;
import request.authenticated_request.paged_service_request.GetFollowersRequest;
import response.GetFolloweesCountResponse;
import response.GetFollowersCountResponse;
import response.LoginResponse;
import response.RegisterResponse;
import response.paged_service_response.GetFollowersResponse;

public class ServerFacadeTest {
    private ServerFacade serverFacade;
    private User allen;
    private User amy;
    private AuthToken authToken;

    @BeforeEach
    public void setup(){
        serverFacade = new ServerFacade();
        allen = FakeData.getInstance().getFirstUser();
        amy = FakeData.getInstance().findUserByAlias("@amy");
        authToken = new AuthToken();
    }

    @Test
    public void testSuccessRegister() throws Exception {

        RegisterRequest request = new RegisterRequest("@username", "password", "first", "last", "imageBase64");
        RegisterResponse response = serverFacade.register(request);
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNull(response.getMessage());
        Assertions.assertNotNull(response.getUser());
        Assertions.assertNotNull(response.getAuthToken());
    }

    @Test
    public void testFailedRegister(){
        RegisterRequest request1 = new RegisterRequest(null, "password", "first", "last", "imageBase64");
        Assertions.assertThrows(BadRequestException.class, ()-> serverFacade.register(request1));
        RegisterRequest request2 = new RegisterRequest("@username", null, "first", "last", "imageBase64");
        Assertions.assertThrows(BadRequestException.class, ()-> serverFacade.register(request2));
        RegisterRequest request3 = new RegisterRequest("@username", "password", null, "last", "imageBase64");
        Assertions.assertThrows(BadRequestException.class, ()-> serverFacade.register(request3));
        RegisterRequest request4 = new RegisterRequest("@username", "password", "first", null, "imageBase64");
        Assertions.assertThrows(BadRequestException.class, ()-> serverFacade.register(request4));
        RegisterRequest request5 = new RegisterRequest("@username", "password", "first", "last", null);
        Assertions.assertThrows(BadRequestException.class, ()-> serverFacade.register(request5));

        RegisterRequest request6 = new RegisterRequest("", "password", "first", "last", "imageBase64");
        Assertions.assertThrows(BadRequestException.class, ()-> serverFacade.register(request6));
        RegisterRequest request7 = new RegisterRequest("@username", "", "first", "last", "imageBase64");
        Assertions.assertThrows(BadRequestException.class, ()-> serverFacade.register(request7));
        RegisterRequest request8 = new RegisterRequest("@username", "password", "", "last", "imageBase64");
        Assertions.assertThrows(BadRequestException.class, ()-> serverFacade.register(request8));
        RegisterRequest request9 = new RegisterRequest("@username", "password", "first", "", "imageBase64");
        Assertions.assertThrows(BadRequestException.class, ()-> serverFacade.register(request9));
        RegisterRequest request10 = new RegisterRequest("@username", "password", "first", "last", "");
        Assertions.assertThrows(BadRequestException.class, ()-> serverFacade.register(request10));


        RegisterRequest request11 = new RegisterRequest("username", "password", "first", "last", "");
        Assertions.assertThrows(BadRequestException.class, ()-> serverFacade.register(request11));

    }

    @Test
    public void testSuccessGetFollowers() throws Exception {
        GetFollowersRequest request = new GetFollowersRequest(new AuthToken(), allen, null, 10);
        GetFollowersResponse response = serverFacade.getFollowers(request);
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNull(response.getMessage());
        Assertions.assertNotNull(response.getItems());
        Assertions.assertTrue(response.getHasMorePages());

        request = new GetFollowersRequest(new AuthToken(), amy, null, 10);
        response = serverFacade.getFollowers(request);
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNull(response.getMessage());
        Assertions.assertNotNull(response.getItems());
        Assertions.assertTrue(response.getHasMorePages());
    }

    @Test
    public void testFailedGetFollowers(){
        GetFollowersRequest request1 = new GetFollowersRequest(null, allen, null, 10);
        Assertions.assertThrows(BadRequestException.class, ()->serverFacade.getFollowers(request1));

        GetFollowersRequest request2 = new GetFollowersRequest(new AuthToken(), null, null, 10);
        Assertions.assertThrows(BadRequestException.class, ()->serverFacade.getFollowers(request2));

    }

    @Test
    public void testSuccessGetFollowersCount() throws Exception {
        GetFollowersCountRequest request = new GetFollowersCountRequest(new AuthToken(), allen);
        GetFollowersCountResponse response = serverFacade.getFollowersCount(request);
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNull(response.getMessage());

        request = new GetFollowersCountRequest(new AuthToken(), amy);
        response = serverFacade.getFollowersCount(request);
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNull(response.getMessage());
    }

    @Test
    public void testFailedGetFollowersCount() throws Exception{
        GetFollowersCountRequest request1 = new GetFollowersCountRequest(null, allen);
        Assertions.assertThrows(BadRequestException.class, ()->serverFacade.getFollowersCount(request1));

        GetFollowersCountRequest request2 = new GetFollowersCountRequest(new AuthToken(), null);
        Assertions.assertThrows(BadRequestException.class, ()->serverFacade.getFollowersCount(request2));
    }

    @Test
    public void testSuccessGetFolloweesCount() throws Exception {
        GetFolloweesCountRequest request = new GetFolloweesCountRequest(new AuthToken(), allen);
        GetFolloweesCountResponse response = serverFacade.getFolloweesCount(request);
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNull(response.getMessage());

        request = new GetFolloweesCountRequest(new AuthToken(), amy);
        response = serverFacade.getFolloweesCount(request);
        Assertions.assertTrue(response.isSuccess());
        Assertions.assertNull(response.getMessage());
    }

    @Test
    public void testFailedGetFolloweesCount() throws Exception{
        GetFolloweesCountRequest request1 = new GetFolloweesCountRequest(null, allen);
        Assertions.assertThrows(BadRequestException.class, ()->serverFacade.getFolloweesCount(request1));

        GetFolloweesCountRequest request2 = new GetFolloweesCountRequest(new AuthToken(), null);
        Assertions.assertThrows(BadRequestException.class, ()->serverFacade.getFolloweesCount(request2));
    }
}
