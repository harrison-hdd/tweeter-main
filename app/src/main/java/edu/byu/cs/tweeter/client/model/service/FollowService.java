package edu.byu.cs.tweeter.client.model.service;


import edu.byu.cs.tweeter.client.backgroundTask.BackgroundTaskUtils;
import edu.byu.cs.tweeter.client.backgroundTask.authenticatedTask.FollowTask;
import edu.byu.cs.tweeter.client.backgroundTask.authenticatedTask.GetFollowersCountTask;
import edu.byu.cs.tweeter.client.backgroundTask.authenticatedTask.pagedTask.GetFollowersTask;
import edu.byu.cs.tweeter.client.backgroundTask.authenticatedTask.GetFollowingCountTask;
import edu.byu.cs.tweeter.client.backgroundTask.authenticatedTask.pagedTask.GetFollowingTask;
import edu.byu.cs.tweeter.client.backgroundTask.authenticatedTask.IsFollowerTask;
import edu.byu.cs.tweeter.client.backgroundTask.authenticatedTask.UnfollowTask;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.handler.FollowHandler;
import edu.byu.cs.tweeter.client.model.service.handler.FollowerCountHandler;
import edu.byu.cs.tweeter.client.model.service.handler.FollowingCountHandler;
import edu.byu.cs.tweeter.client.model.service.handler.IsFollowerHandler;
import edu.byu.cs.tweeter.client.model.service.handler.UnfollowHandler;
import edu.byu.cs.tweeter.client.model.service.handler.pageServiceHandler.GetFollowersHandler;
import edu.byu.cs.tweeter.client.model.service.handler.pageServiceHandler.GetFollowingHandler;
import edu.byu.cs.tweeter.client.model.service.observer.FollowObserver;
import edu.byu.cs.tweeter.client.model.service.observer.FollowerCountObserver;
import edu.byu.cs.tweeter.client.model.service.observer.FollowingCountObserver;
import edu.byu.cs.tweeter.client.model.service.observer.IsFollowerObserver;
import edu.byu.cs.tweeter.client.model.service.observer.PagedServiceObserver;
import edu.byu.cs.tweeter.client.model.service.observer.UnfollowObserver;
import edu.byu.cs.tweeter.model.domain.User;

public class FollowService {

    public void getFollowing(User user, User lastFollowee, int pageSize, PagedServiceObserver<User> getFollowingObserver){
        GetFollowingTask getFollowingTask = new GetFollowingTask(Cache.getInstance().getCurrUserAuthToken(),
                user, pageSize, lastFollowee, new GetFollowingHandler(getFollowingObserver));

        BackgroundTaskUtils.runTask(getFollowingTask);

    }

    public void getFollowers(User user, User lastFollower, int pageSize, PagedServiceObserver<User> getFollowersObserver) {
        GetFollowersTask getFollowersTask = new GetFollowersTask(Cache.getInstance().getCurrUserAuthToken(),
                user, pageSize, lastFollower, new GetFollowersHandler(getFollowersObserver));

        BackgroundTaskUtils.runTask(getFollowersTask);
    }


    public void isFollower(User selectedUser, IsFollowerObserver isFollowerObserver){
        IsFollowerTask isFollowerTask = new IsFollowerTask(Cache.getInstance().getCurrUserAuthToken(),
                Cache.getInstance().getCurrUser(), selectedUser, new IsFollowerHandler(isFollowerObserver));
        BackgroundTaskUtils.runTask(isFollowerTask);
    }

    public void followingAndFollowerCount(User selectedUser, FollowerCountObserver followerCountObserver, FollowingCountObserver followingCountObserver){


        // Get count of most recently selected user's followers.
        GetFollowersCountTask followersCountTask = new GetFollowersCountTask(Cache.getInstance().getCurrUserAuthToken(),
                selectedUser, new FollowerCountHandler(followerCountObserver));


        // Get count of most recently selected user's followees (who they are following)
        GetFollowingCountTask followingCountTask = new GetFollowingCountTask(Cache.getInstance().getCurrUserAuthToken(),
                selectedUser, new FollowingCountHandler(followingCountObserver));

        BackgroundTaskUtils.runTask(followersCountTask);
        BackgroundTaskUtils.runTask(followingCountTask);
    }

    public void follow(User selectedUser, FollowObserver followObserver){
        FollowTask followTask = new FollowTask(Cache.getInstance().getCurrUserAuthToken(),
                selectedUser, new FollowHandler(followObserver));
        BackgroundTaskUtils.runTask(followTask);
    }

    public void unfollow(User selectedUser, UnfollowObserver unfollowObserver){
        UnfollowTask unfollowTask = new UnfollowTask(Cache.getInstance().getCurrUserAuthToken(),
                selectedUser, new UnfollowHandler(unfollowObserver));
        BackgroundTaskUtils.runTask(unfollowTask);
    }
}
