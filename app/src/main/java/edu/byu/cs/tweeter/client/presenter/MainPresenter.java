package edu.byu.cs.tweeter.client.presenter;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.byu.cs.client.R;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.observer.FollowerCountObserver;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class MainPresenter extends Presenter{
    private static final String LOG_TAG = "MainPresenter";
    private final MainView view;
    private final StatusService statusService;


    public interface MainView extends Presenter.View{
        void logoutSuccess();
        User getSelectedUser();
        void setFollowButton(boolean isVisible, boolean isFollowing);
        void setFollowerCount(int numFollowers);
        void setFollowingCount(int numFollowing);
        void setFollowButtonEnabled(boolean isEnabled);

        String getStringByResId(int resId);
        String getFollowButtonText();
    }

    public MainPresenter(MainView view){
        this.view = view;
        statusService = new StatusService();
    }

    public MainPresenter(MainView view, StatusService statusService){
        this.view = view;
        this.statusService = statusService;
    }



    public void initiateLogout(){
        view.displayInfoMessage("Logging out");
        new UserService().logout(new LogoutObserver());
    }

    public void onNewUserSelected(){
        User selectedUser = view.getSelectedUser();

        updateSelectedUserFollowingAndFollowers(selectedUser);

        if (selectedUser.compareTo(Cache.getInstance().getCurrUser()) == 0) {//users cannot follow themselves
            view.setFollowButton(false, false);
        } else {
            new FollowService().isFollower(selectedUser, new IsFollowerObserver());
        }
    }

    public void onFollowButtonClicked(){
        view.setFollowButtonEnabled(false);

        User selectedUser = view.getSelectedUser();

        if(view.getFollowButtonText().equals(view.getStringByResId(R.string.follow))) {
            new FollowService().follow(selectedUser, new FollowObserver());
            view.displayInfoMessage("Adding " + selectedUser.getName() + "...");
        }else{
            new FollowService().unfollow(selectedUser, new UnfollowObserver());
            view.displayInfoMessage("Removing " + selectedUser.getName() + "...");
        }

    }

    private void updateSelectedUserFollowingAndFollowers(User selectedUser){
        new FollowService().followingAndFollowerCount(selectedUser, new FollowerCountObserver(), new FollowingCountObserver());
    }

    private void updateFollowButton(boolean removed) {
        // If follow relationship was removed.
        if (removed) {
            view.setFollowButton(true, false);
        } else {
            view.setFollowButton(true, true);
        }
    }

    public PostStatusObserver postStatus(String post){
        view.displayInfoMessage("Posting Status...");
        PostStatusObserver observer = postStatusObserverFactory();
        try{
            Status newStatus = statusFactory(post);
            statusService.postStatus(newStatus, observer);
        }catch (Exception ex){
//            Log.e(LOG_TAG, ex.getMessage(), ex);
            view.displayInfoMessage("Failed to post the status because of exception: " + ex.getMessage());
        }
        return observer;
    }

    public Status statusFactory(String post) throws ParseException {
        return new Status(post, Cache.getInstance().getCurrUser(), getFormattedDateTime(), parseURLs(post), parseMentions(post));
    }
    private String getFormattedDateTime() throws ParseException {
//        SimpleDateFormat userFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        SimpleDateFormat statusFormat = new SimpleDateFormat("MMM d yyyy h:mm aaa");
//
//        return statusFormat.format(userFormat.parse(LocalDate.now().toString() + " " + LocalTime.now().toString().substring(0, 8)));
        return new Date().toString();
    }

    private List<String> parseURLs(String post) {
        List<String> containedUrls = new ArrayList<>();
        for (String word : post.split("\\s")) {
            if (word.startsWith("http://") || word.startsWith("https://")) {

                int index = findUrlEndIndex(word);

                word = word.substring(0, index);

                containedUrls.add(word);
            }
        }

        return containedUrls;
    }

    private List<String> parseMentions(String post) {
        List<String> containedMentions = new ArrayList<>();

        for (String word : post.split("\\s")) {
            if (word.startsWith("@")) {
                word = word.replaceAll("[^a-zA-Z0-9]", "");
                word = "@".concat(word);

                containedMentions.add(word);
            }
        }

        return containedMentions;
    }

    private int findUrlEndIndex(String word) {
        if (word.contains(".com")) {
            int index = word.indexOf(".com");
            index += 4;
            return index;
        } else if (word.contains(".org")) {
            int index = word.indexOf(".org");
            index += 4;
            return index;
        } else if (word.contains(".edu")) {
            int index = word.indexOf(".edu");
            index += 4;
            return index;
        } else if (word.contains(".net")) {
            int index = word.indexOf(".net");
            index += 4;
            return index;
        } else if (word.contains(".mil")) {
            int index = word.indexOf(".mil");
            index += 4;
            return index;
        } else {
            return word.length();
        }
    }

    public PostStatusObserver postStatusObserverFactory(){
        return new PostStatusObserver();
    }

    public class PostStatusObserver implements edu.byu.cs.tweeter.client.model.service.observer.PostStatusObserver {
        @Override
        public void handleSuccess() {
            view.displayInfoMessage("Successfully Posted!");
        }

        @Override
        public void handleFailure(String message) {
            view.displayInfoMessage(message);
        }
    }

    private class UnfollowObserver implements edu.byu.cs.tweeter.client.model.service.observer.UnfollowObserver {
        @Override
        public void handleSuccess() {
            updateSelectedUserFollowingAndFollowers(view.getSelectedUser());
            updateFollowButton(true);
            view.setFollowButtonEnabled(true);
        }

        @Override
        public void handleFailure(String message) {
            view.displayInfoMessage(message);
            view.setFollowButtonEnabled(true);
        }
    }

    private class FollowObserver implements edu.byu.cs.tweeter.client.model.service.observer.FollowObserver {
        @Override
        public void handleSuccess() {
            updateSelectedUserFollowingAndFollowers(view.getSelectedUser());
            updateFollowButton(false);
            view.setFollowButtonEnabled(true);
        }

        @Override
        public void handleFailure(String message) {
            view.displayInfoMessage(message);
            view.setFollowButtonEnabled(true);
        }
    }

    private class LogoutObserver implements edu.byu.cs.tweeter.client.model.service.observer.LogoutObserver {
        @Override
        public void handleSuccess() {
            view.logoutSuccess();
        }

        @Override
        public void handleFailure(String message) {
            view.displayInfoMessage(message);
        }
    }

    private class IsFollowerObserver implements edu.byu.cs.tweeter.client.model.service.observer.IsFollowerObserver {
        @Override
        public void handleSuccess(boolean isFollower) {
            if (isFollower) {
                view.setFollowButton(true, true);
            } else {
                view.setFollowButton(true, false);
            }
        }

        @Override
        public void handleFailure(String message) {
            view.displayInfoMessage(message);
        }
    }

    private class FollowingCountObserver implements edu.byu.cs.tweeter.client.model.service.observer.FollowingCountObserver {
        @Override
        public void handleSuccess(int numFollowing) {
            view.setFollowingCount(numFollowing);
        }

        @Override
        public void handleFailure(String message) {
            view.displayInfoMessage(message);
        }

    }

    private class FollowerCountObserver implements edu.byu.cs.tweeter.client.model.service.observer.FollowerCountObserver {
        @Override
        public void handleSuccess(int numFollowers) {
            view.setFollowerCount(numFollowers);
        }

        @Override
        public void handleFailure(String message) {
            view.displayInfoMessage(message);
        }

    }

}
