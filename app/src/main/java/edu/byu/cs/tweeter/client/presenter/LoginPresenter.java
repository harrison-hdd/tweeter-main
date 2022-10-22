package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.UserService;

public class LoginPresenter extends AuthenticationPresenter{

    private static final String LOG_TAG = "LoginPresenter";
    private final LoginView view;




    public interface LoginView extends AuthenticationPresenter.View{}

    public LoginPresenter(LoginView view) {
        super(view);
        this.view = view;
    }

    public void initiateLogin(){
        view.displayInfoMessage("Logging in...");
        initiateAuthentication();
    }

    @Override
    public void callAuthenticationService(String username, String password) {
        new UserService().login(username, password, new LoginObserver());
    }

    private class LoginObserver extends AuthenticationPresenter.AuthenticationObserver{ }
}
