package edu.byu.cs.tweeter.client.model.service;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.backgroundTask.BackgroundTaskUtils;
import edu.byu.cs.tweeter.client.backgroundTask.authenticatedTask.pagedTask.GetFeedTask;
import edu.byu.cs.tweeter.client.backgroundTask.authenticatedTask.pagedTask.GetStoryTask;
import edu.byu.cs.tweeter.client.backgroundTask.authenticatedTask.PostStatusTask;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.handler.PostStatusHandler;
import edu.byu.cs.tweeter.client.model.service.handler.pageServiceHandler.GetFeedHandler;
import edu.byu.cs.tweeter.client.model.service.handler.pageServiceHandler.GetStoryHandler;
import edu.byu.cs.tweeter.client.model.service.observer.PagedServiceObserver;
import edu.byu.cs.tweeter.client.model.service.observer.PostStatusObserver;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StatusService {
    public void getFeed(User user, Status lastStatus, int pageSize, PagedServiceObserver<Status> pagedServiceObserver){

        GetFeedTask getFeedTask = new GetFeedTask(Cache.getInstance().getCurrUserAuthToken(),
                user, pageSize, lastStatus, new GetFeedHandler(pagedServiceObserver));
        BackgroundTaskUtils.runTask(getFeedTask);
    }

    public void getStory(User user, Status lastStatus, int pageSize, PagedServiceObserver<Status> pagedServiceObserver){
        GetStoryTask getStoryTask = new GetStoryTask(Cache.getInstance().getCurrUserAuthToken(),
                user, pageSize, lastStatus, new GetStoryHandler(pagedServiceObserver));
        BackgroundTaskUtils.runTask(getStoryTask);
    }

    public void postStatus(Status newStatus, PostStatusObserver postStatusObserver){
        PostStatusTask statusTask = new PostStatusTask(Cache.getInstance().getCurrUserAuthToken(),
                newStatus, new PostStatusHandler(postStatusObserver));
        BackgroundTaskUtils.runTask(statusTask);
    }

}
