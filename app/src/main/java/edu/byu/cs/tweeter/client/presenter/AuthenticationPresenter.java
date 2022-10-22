package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.util.Pair;

public abstract class AuthenticationPresenter extends Presenter{
    protected View view;

    protected AuthenticationPresenter(View view){
        this.view = view;
    }
    public interface View extends Presenter.View {
        String getPassword();
        String getUsername();

        void displayErrorMessage(String message);
        void authenticationSuccess();
    }

    protected Pair<String, String> getUserNameAndPassword(){
        String username = view.getUsername();
        String password = view.getPassword();
        return new Pair<>(username, password);
    }

    protected void validateAliasAndPassword(String alias, String password) {
        if (alias.charAt(0) != '@') {
            throw new IllegalArgumentException("Alias must begin with @.");
        }
        if (alias.length() < 2) {
            throw new IllegalArgumentException("Alias must contain 1 or more characters after the @.");
        }
        if (password.length() == 0) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }
    }

    public void initiateAuthentication(){
        Pair<String, String> credentials = getUserNameAndPassword();
        String alias = credentials.getFirst();
        String password = credentials.getSecond();

        try {
            validateAliasAndPassword(alias, password);
            view.displayErrorMessage(null);

            callAuthenticationService(alias, password);
        }catch (Exception e){
            view.displayErrorMessage(e.getMessage());
        }
    }

    public abstract void callAuthenticationService(String username, String password);

    protected class AuthenticationObserver implements edu.byu.cs.tweeter.client.model.service.observer.authenticationObserver.AuthenticationObserver {
        @Override
        public void handleSuccess(User user) {
            view.displayInfoMessage("Hello " + user.getName());
            view.authenticationSuccess();
        }

        @Override
        public void handleFailure(String message) {
            view.displayInfoMessage(message);
        }
    }
}

