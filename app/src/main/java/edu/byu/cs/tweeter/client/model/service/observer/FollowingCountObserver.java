package edu.byu.cs.tweeter.client.model.service.observer;

public interface FollowingCountObserver extends Observer{
    void handleSuccess(int followingCount);
}
