package edu.byu.cs.tweeter.client.model.service.observer;

public interface IsFollowerObserver extends Observer{
    void handleSuccess(boolean isFollower);
}
