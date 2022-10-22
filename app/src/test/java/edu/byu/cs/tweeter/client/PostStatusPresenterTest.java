package edu.byu.cs.tweeter.client;

import android.util.Log;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.text.ParseException;
import java.util.Locale;
import java.util.function.Predicate;

import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.observer.PostStatusObserver;
import edu.byu.cs.tweeter.client.presenter.MainPresenter;
import edu.byu.cs.tweeter.model.domain.Status;

public class PostStatusPresenterTest {
    MainPresenter presenterSpy;
    StatusService mockService;
    MainPresenter.MainView mockView;




    @BeforeEach
    public void setup(){
        mockService = Mockito.mock(StatusService.class);
        mockView = Mockito.mock(MainPresenter.MainView.class);

        presenterSpy = Mockito.spy(new MainPresenter(mockView, mockService));
    }

    @Test
    public void successTest() throws ParseException {
        String successPost = "@alias this is a success post tweeter.com";

        Mockito.doAnswer(new Answer() {
                             @Override
                             public Object answer(InvocationOnMock invocation) throws Throwable {
                                 PostStatusObserver postStatusObserver = invocation.getArgument(1);
                                 postStatusObserver.handleSuccess();
                                 return null;
                             }
                         }
        ).when(mockService).postStatus(Mockito.any(Status.class), Mockito.any(PostStatusObserver.class));

        presenterSpy.postStatus(successPost);

        Mockito.verify(mockView, Mockito.atLeast(1)).displayInfoMessage("Posting Status...");
        Mockito.verify(mockView, Mockito.atLeast(1)).displayInfoMessage("Successfully Posted!");
    }

    @Test
    public void failureTest() throws ParseException{
        String failurePost = "@alias this is a failure post tweeter.com";

        Mockito.doAnswer(new Answer() {
                             @Override
                             public Object answer(InvocationOnMock invocation) throws Throwable {
                                 Status status = invocation.getArgument(0);
                                 PostStatusObserver postStatusObserver = invocation.getArgument(1);
                                 postStatusObserver.handleFailure("Failed to post status: Failure message");
                                 return null;
                             }
                         }
        ).when(mockService).postStatus(Mockito.any(Status.class), Mockito.any(PostStatusObserver.class));

        presenterSpy.postStatus(failurePost);

        Mockito.verify(mockView, Mockito.atLeast(1)).displayInfoMessage("Posting Status...");
        Mockito.verify(mockView, Mockito.atLeast(1)).displayInfoMessage("Failed to post status: Failure message");
    }

    @Test
    public void exceptionTest() throws ParseException{
        String exceptionPost = "@alias this is a exception post tweeter.com";

        Mockito.doAnswer(new Answer() {
                             @Override
                             public Object answer(InvocationOnMock invocation) throws Throwable {
                                 Status status = invocation.getArgument(0);
                                 PostStatusObserver postStatusObserver = invocation.getArgument(1);
                                 postStatusObserver.handleFailure("Failed to post status because of exception: Exception message");

                                 return null;
                             }
                         }
        ).when(mockService).postStatus(Mockito.any(Status.class), Mockito.any(PostStatusObserver.class));

        presenterSpy.postStatus(exceptionPost);

        Mockito.verify(mockView, Mockito.atLeast(1)).displayInfoMessage("Posting Status...");
        Mockito.verify(mockView, Mockito.atLeast(1)).displayInfoMessage("Failed to post status because of exception: Exception message");

    }
    @Test
    public void testCorrectParameters() throws ParseException {
        //verify that post and status has the same content

        String post = "@abc this is a status google.com";

        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Status status = invocation.getArgument(0);
                Assertions.assertTrue(post.equals(status.getPost()));
                return null;
            }
        }).when(mockService).postStatus(Mockito.any(), Mockito.any());

        presenterSpy.postStatus(post);
    }

}
