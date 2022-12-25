package edu.byu.cs.tweeter.client;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.observer.PagedServiceObserver;
import edu.byu.cs.tweeter.client.model.service.observer.PostStatusObserver;
import edu.byu.cs.tweeter.client.model.service.observer.authenticationObserver.LoginObserver;
import edu.byu.cs.tweeter.client.presenter.MainPresenter;
import edu.byu.cs.tweeter.client.presenter.pagedPresenter.PagedPresenter;
import edu.byu.cs.tweeter.client.presenter.pagedPresenter.StoryPresenter;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.util.FakeData;
import request.LoginRequest;
import response.LoginResponse;

public class GetStoryTest {
    private StatusService statusService;
    private UserService userService;

    private User loggedInUser;

    private PagedServiceObserver<Status> mockObserver;
    private LoginObserver mockLoginObserver;

    private MainPresenter mainPresenter;

    private MainPresenter.MainView mainView;
    private CountDownLatch latch;

    private List<Status> story;

    private class ObserverImpl implements PagedServiceObserver<Status>{

        @Override
        public void handleFailure(String message) {
            latch.countDown();
        }

        @Override
        public void handleSuccess(List<Status> items, boolean hasMorePages) {
            latch.countDown();
        }
    }

    private class LoginObserverImpl implements LoginObserver{

        @Override
        public void handleFailure(String message) {
            latch.countDown();
        }

        @Override
        public void handleSuccess(User user) {
            loggedInUser = user;
            latch.countDown();
        }
    }

    private class GetStoryObserverImpl implements PagedServiceObserver<Status>{

        @Override
        public void handleFailure(String message) {
            latch.countDown();
        }

        @Override
        public void handleSuccess(List<Status> items, boolean hasMorePages) {
            story = items;
            latch.countDown();
        }
    }

    @BeforeEach
    public void setup(){
        statusService = new StatusService();
        userService = new UserService();

        latch = new CountDownLatch(1);

        mockObserver = Mockito.spy(new ObserverImpl());

        mockLoginObserver = Mockito.spy(new LoginObserverImpl());

        mainView = Mockito.mock(MainPresenter.MainView.class);
        Mockito.doAnswer(invocation -> {
            latch.countDown();
            return null;
        }).when(mainView).displayInfoMessage(Mockito.anyString());
        mainPresenter = new MainPresenter(mainView);


    }

//    @Test
//    public void testSuccessGetStory() throws Exception{
//        statusService.getStory(user, null, 10, mockObserver);
//        latch.await();
//        Mockito.verify(mockObserver).handleSuccess(Mockito.anyList(), Mockito.anyBoolean());
//    }

    @Test
    public void testPostStatusSuccess() throws Exception{
        String username = "@username1";
        String password = "password";
        userService.login(username, password, mockLoginObserver);

        latch.await();
        Mockito.verify(mockLoginObserver).handleSuccess(Mockito.any());
        Assertions.assertEquals(username, loggedInUser.alias);

        latch = new CountDownLatch(2); //displayMessage is called 2 times

        String post = UUID.randomUUID().toString();
        mainPresenter.postStatus(post);

        latch.await();
        
        Mockito.verify(mainView).displayInfoMessage("Successfully Posted!");

        latch = new CountDownLatch(1);
        statusService.getStory(loggedInUser, null, 10, new GetStoryObserverImpl());
        latch.await();

        Status firstStatus = story.get(0);
        Assertions.assertEquals(post, firstStatus.post);
        Assertions.assertEquals(loggedInUser.alias, firstStatus.user.alias);

    }

}
