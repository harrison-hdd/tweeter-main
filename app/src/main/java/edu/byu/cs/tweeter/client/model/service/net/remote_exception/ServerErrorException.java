package edu.byu.cs.tweeter.client.model.service.net.remote_exception;

import java.util.List;

public class ServerErrorException extends RemoteException{
    public ServerErrorException(String message, String errorType, List<String> stackTrace) {
        super(message, errorType, stackTrace);
    }
}
