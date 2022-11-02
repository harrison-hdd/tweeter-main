package edu.byu.cs.tweeter.client.model.service.net.remote_exception;

import androidx.annotation.NonNull;

import java.util.List;

public class RemoteException extends Exception{
    protected String errorType;
    protected List<String> stackTrace;

    protected RemoteException(){}

    protected RemoteException(String message, String errorType, List<String> stackTrace){
        super(message);
        this.errorType = errorType;
        this.stackTrace = stackTrace;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public List<String> getRemoteStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(List<String> stackTrace) {
        this.stackTrace = stackTrace;
    }
}
