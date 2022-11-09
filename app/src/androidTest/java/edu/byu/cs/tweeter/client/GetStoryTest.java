package edu.byu.cs.tweeter.client;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.observer.PagedServiceObserver;
import edu.byu.cs.tweeter.client.presenter.pagedPresenter.PagedPresenter;
import edu.byu.cs.tweeter.client.presenter.pagedPresenter.StoryPresenter;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.util.FakeData;

public class GetStoryTest {
    private StatusService statusService;
    private PagedServiceObserver<Status> mockObserver;
    private User user;
    private CountDownLatch latch;

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

    @BeforeEach
    public void setup(){
        statusService = Mockito.spy(new StatusService());
        user = FakeData.getInstance().getFirstUser();
        latch = new CountDownLatch(1);

        mockObserver = Mockito.spy(new ObserverImpl());

        Cache.getInstance().setCurrUserAuthToken(new AuthToken());
    }

    @Test
    public void testSuccessGetStory() throws Exception{
        statusService.getStory(user, null, 10, mockObserver);
        latch.await();
        Mockito.verify(mockObserver).handleSuccess(Mockito.anyList(), Mockito.anyBoolean());
    }
}
