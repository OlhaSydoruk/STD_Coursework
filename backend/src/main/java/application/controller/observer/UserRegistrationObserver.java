package application.controller.observer;

public interface UserRegistrationObserver {
    void onUserRegistrationStatusChanged(boolean isRegistered);
}
