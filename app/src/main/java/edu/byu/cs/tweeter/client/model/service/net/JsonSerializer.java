package edu.byu.cs.tweeter.client.model.service.net;

import com.google.gson.Gson;

import java.io.Reader;
import java.io.Writer;

public class JsonSerializer {

    public static void writeToRequest(Object requestInfo, Writer reqBody) {
        (new Gson()).toJson(requestInfo, reqBody);
    }

    public static <T> T readFromResponse(Reader resBody, Class<T> returnType) {
        return (new Gson()).fromJson(resBody, returnType);
    }
}