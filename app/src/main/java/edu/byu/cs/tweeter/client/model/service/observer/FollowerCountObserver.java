package edu.byu.cs.tweeter.client.model.service.observer;

public interface FollowerCountObserver extends Observer{
    void handleSuccess(int followersCount);
}
