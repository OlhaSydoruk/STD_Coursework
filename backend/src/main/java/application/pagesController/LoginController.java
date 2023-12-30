package application.pagesController;

import application.SpringFXMLLoader;
import application.controller.UserController;
import application.controller.observer.UserRegistrationObserver;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
@Component
public class LoginController extends FxController implements UserRegistrationObserver {
    @Autowired
    private UserController userController;
    @Autowired
    public void setLoader(SpringFXMLLoader loader) {
        setSpringFXMLLoader(loader);
    }
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button loginButton;

    @FXML
    private ImageView logo;

    @FXML
    private Button signUpButton;

    @FXML
    void initialize() {
        userController.registerObserver(this);

        signUpButton.setOnAction(e->{
            openWebPage( "http://localhost:5173/authorization");
        });

        loginButton.setOnAction(e->{
            openWebPage( "http://localhost:5173/");
        });

    }
    @Override
    public void onUserRegistrationStatusChanged(boolean isRegistered) {
        if (isRegistered) {
            Stage currentStage = getStageFromButton(loginButton);
            Platform.runLater(() -> {
                switchScene(currentStage, "/application/infoPage-view.fxml");
            });
        }
    }

        private void openWebPage(String url) {
        String os = System.getProperty("os.name").toLowerCase();
        Runtime runtime = Runtime.getRuntime();
        try {
            if (os.contains("win")) {
                runtime.exec("rundll32 url.dll,FileProtocolHandler " + url);
            } else if (os.contains("mac")) {
                runtime.exec("open " + url);
            } else if (os.contains("nix") || os.contains("nux")) {
                runtime.exec("xdg-open " + url);
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
    }


}
