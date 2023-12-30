package application.pagesController;

import application.SpringFXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import javafx.scene.Scene;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.util.function.Supplier;

public abstract class FxController {
    @Autowired
    protected SpringFXMLLoader springFXMLLoader;
    @Autowired
    protected ConfigurableApplicationContext applicationContext;

    public void setSpringFXMLLoader(SpringFXMLLoader loader) {
        this.springFXMLLoader = loader;
    }

    protected void switchScene(Stage stage, String fxmlPath) {
        try {
            Parent root = springFXMLLoader.load(fxmlPath);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Обработка ошибок
        }
    }

    protected Stage getStageFromButton(Button button) {
        return (Stage) button.getScene().getWindow();
    }

    protected void handleSave(Label showErrorField, Supplier<Boolean> dataSaveMethod) {
            if (dataSaveMethod.get()) {
                showErrorField.setText("Saving is successful");
                showErrorField.setStyle("-fx-text-fill: #04a404;");
            } else {
                showErrorField.setText("Something went wrong");
                showErrorField.setStyle("-fx-text-fill: red;");
            }

    }
}
