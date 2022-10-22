package edu.byu.cs.tweeter.client.model.service.observer;

import edu.byu.cs.tweeter.model.domain.User;

public interface GetUserObserver extends Observer{
    void handleSuccess(User user);
}
