package edu.byu.cs.tweeter.net.response;

import java.io.Serializable;

public abstract class Response implements Serializable {
    public boolean success;
    public String message;
}
