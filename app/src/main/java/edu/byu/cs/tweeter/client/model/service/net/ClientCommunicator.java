package edu.byu.cs.tweeter.client.model.service.net;

import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import edu.byu.cs.tweeter.client.model.service.net.remote_exception.BadRequestException;
import edu.byu.cs.tweeter.client.model.service.net.remote_exception.ServerErrorException;

public class ClientCommunicator {
    private final String baseURLPath;
    private static final int TIME_OUT = 60000;

    public ClientCommunicator(String baseURLPath){
        this.baseURLPath = baseURLPath;
    }

    public <T> T doPost(Object request, String urlPath, Class<T> responseType) throws Exception {
        HttpURLConnection connection = null;
        T response = null;
        try{
            URL url = new URL(getURL(urlPath));
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setReadTimeout(TIME_OUT);
            connection.setRequestMethod("POST");
            connection.connect();


            String req = new Gson().toJson(request); //fixme: debug

            Writer requestBodyStream = new OutputStreamWriter(connection.getOutputStream());
            JsonSerializer.writeToRequest(request, requestBodyStream);
            requestBodyStream.flush();
            requestBodyStream.close();

            Reader resBody;

            int resCode = connection.getResponseCode();

            if(resCode == HttpURLConnection.HTTP_OK) {
                resBody = new InputStreamReader(connection.getInputStream());
                response = JsonSerializer.readFromResponse(resBody, responseType);
            } else if(resCode == HttpURLConnection.HTTP_BAD_REQUEST ||
                      resCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                resBody = new InputStreamReader(connection.getErrorStream());
                ExceptionResponse badReqEx = JsonSerializer.readFromResponse(resBody, ExceptionResponse.class);
                throw new BadRequestException(badReqEx.errorMessage, badReqEx.errorType, badReqEx.stackTrace);
            } else if(resCode == HttpURLConnection.HTTP_INTERNAL_ERROR) {
                resBody = new InputStreamReader(connection.getErrorStream());
                ExceptionResponse serverEx = JsonSerializer.readFromResponse(resBody, ExceptionResponse.class);
                throw new ServerErrorException(serverEx.errorMessage, serverEx.errorType, serverEx.stackTrace);
            } else {
                throw new RuntimeException("An unknown error occurred. Response code = " + connection.getResponseCode());
            }

        }finally {
            if(connection != null){
                connection.disconnect();
            }
        }

        return response;
    }

    private String getURL(String urlPath){
        return this.baseURLPath + urlPath;
    }

    private static class ExceptionResponse{
        public String errorMessage;
        public String errorType;
        public List<String> stackTrace;
    }
}
