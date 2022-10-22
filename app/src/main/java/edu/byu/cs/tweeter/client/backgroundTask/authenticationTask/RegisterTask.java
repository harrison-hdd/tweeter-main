package edu.byu.cs.tweeter.client.backgroundTask.authenticationTask;

import android.os.Handler;

import edu.byu.cs.tweeter.client.backgroundTask.authenticationTask.AuthenticationTask;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.util.FakeData;
import edu.byu.cs.tweeter.util.Pair;

/**
 * Background task that creates a new user account and logs in the new user (i.e., starts a session).
 */
public class RegisterTask extends AuthenticationTask {
    private static final String LOG_TAG = "RegisterTask";



    /**
     * The user's first name.
     */
    private String firstName;
    /**
     * The user's last name.
     */
    private String lastName;
    /**
     * The base-64 encoded bytes of the user's profile image.
     */
    private String image;


    public RegisterTask(String firstName, String lastName, String username, String password,
                        String image, Handler messageHandler) {
        super(messageHandler, username, password, LOG_TAG);
        this.firstName = firstName;
        this.lastName = lastName;
        this.image = image;
    }

    private FakeData getFakeData() {
        return FakeData.getInstance();
    }

    protected Pair<User, AuthToken> doTask() {
        User registeredUser = getFakeData().getFirstUser();
        AuthToken authToken = getFakeData().getAuthToken();
        return new Pair<>(registeredUser, authToken);
    }
}
