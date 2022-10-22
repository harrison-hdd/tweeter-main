package edu.byu.cs.tweeter.client.model.service.observer.authenticationObserver;

import edu.byu.cs.tweeter.client.model.service.observer.Observer;
import edu.byu.cs.tweeter.model.domain.User;

public interface AuthenticationObserver extends Observer {//used by LoginHandler and RegisterHandler
    void handleSuccess(User user);
}
