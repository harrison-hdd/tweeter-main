package edu.byu.cs.tweeter.client.presenter;

import edu.byu.cs.tweeter.client.model.service.UserService;


public class RegisterPresenter extends AuthenticationPresenter{
    private static final String LOG_TAG = "RegisterPresenter";
    private final RegisterView view;


    public interface RegisterView extends AuthenticationPresenter.View{
        String getFirstName();
        String getLastName();
        String getImageBase64();
    }

    public RegisterPresenter(RegisterView view){
        super(view);
        this.view = view;
    }

    public void initiateRegistration(){
        view.displayInfoMessage("Logging in...");
        initiateAuthentication();
    }


    public void validatePersonalInfo(String firstName, String lastName, String imageBase64) {
        if (firstName.length() == 0) {
            throw new IllegalArgumentException("First Name cannot be empty.");
        }
        if (lastName.length() == 0) {
            throw new IllegalArgumentException("Last Name cannot be empty.");
        }
        if (imageBase64 == null) {
            throw new IllegalArgumentException("Profile image must be uploaded.");
        }
    }

    @Override
    public void callAuthenticationService(String username, String password) {
        String firstName = view.getFirstName();
        String lastname = view.getLastName();
        String imageBase64 = view.getImageBase64();

        validatePersonalInfo(firstName, lastname, imageBase64); //any exceptions will be caught in initiateAuthentication();

        new UserService().register(firstName, lastname, username, password, imageBase64, new RegisterObserver());

    }
    private class RegisterObserver extends AuthenticationPresenter.AuthenticationObserver{}
}
