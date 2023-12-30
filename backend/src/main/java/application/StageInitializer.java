package application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class StageInitializer implements ApplicationListener<App.StageReadyEvent> {
    @Value("classpath:/application/login-view.fxml")
    private Resource appResource;

    @Autowired
    private SpringFXMLLoader springFXMLLoader;

    @Override
    public void onApplicationEvent(App.StageReadyEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(appResource.getURL());
            Parent parent = springFXMLLoader.load("/application/login-view.fxml");

            Stage stage = event.getStage();
            stage.setScene(new Scene(parent, 700, 450));
            stage.setTitle("Authorization");
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException();
        }



    }
}
