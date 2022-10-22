package edu.byu.cs.tweeter.net.response;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class LoginResponse extends Response{
    public User user;
    public AuthToken authToken;
}
