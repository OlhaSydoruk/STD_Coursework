package application.pagesController;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import application.SpringFXMLLoader;
import application.entity.BuildSettings;
import application.validator.Validation;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InfoController extends FxController {
    private String iconPath;
    private Validation validator = new Validation();;
    BuildSettings buildSettings = BuildSettings.getInstance();

    @Autowired
    public void setLoader(SpringFXMLLoader loader) {
        setSpringFXMLLoader(loader);
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button exit;

    @FXML
    private Button filesButton;

    @FXML
    private Button informationButton;

    @FXML
    private ImageView logo;

    @FXML
    private TextField companyNameField;

    @FXML
    private TextField nameProjectField;

    @FXML
    private Button saveBtn;

    @FXML
    private Button settingsButton;
    @FXML
    private Button chooseIconBtn;
    @FXML
    private TextField iconPathField;
    @FXML
    private Label showErrorField;

    public String getIconPath() {
        return iconPath;
    }

    private boolean dataSave() {
        validator.addFieldToValidationMap("nameProject", nameProjectField);
        if(validator.validate()){
            buildSettings.setIconPath(iconPath);
            buildSettings.setProjectName(nameProjectField.getText().trim());
            return true;
        }else return false;
    }

    private void setLastEnteredValues(){
        nameProjectField.setText(buildSettings.getProjectName());
        companyNameField.setText(buildSettings.getNameCompany());
        iconPathField.setText(buildSettings.getIconPath());
    }

    private void setupValidationListeners() {
        nameProjectField.textProperty().addListener((observable, oldValue, newValue) -> validateFields());
    }
    private void validateFields() {
        validator.validate();
    }

    @FXML
    void initialize() {
        setLastEnteredValues();
        setupValidationListeners();

        filesButton.setOnAction(e -> {
            dataSave();
            Stage currentStage = getStageFromButton(filesButton);
            Platform.runLater(() -> {
                switchScene(currentStage, "/application/filePage-view.fxml");
            });
        });
        settingsButton.setOnAction(e -> {
            dataSave();
            Stage currentStage = getStageFromButton(settingsButton);
            Platform.runLater(() -> {
                switchScene(currentStage, "/application/settingsPage-view.fxml");
            });
        });
        chooseIconBtn.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Оберите іконку");
            File initialDirectory = new File("C:\\Users\\sid19\\Documents\\Olya\\Study\\5_Semestr\\TRPZ\\course_work");
            fileChooser.setInitialDirectory(initialDirectory);

            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("ICON files (*.ico)", "*.ico");
            fileChooser.getExtensionFilters().add(extFilter);
            File selectedFile = fileChooser.showOpenDialog(new Stage());
            if (selectedFile != null) {
                iconPath = selectedFile.getAbsolutePath();
                iconPathField.setText(iconPath);
            }
        });
        saveBtn.setOnAction(e -> {
            handleSave(showErrorField, this::dataSave);
        });
        exit.setOnAction(e->{
            Platform.exit();
            this.applicationContext.close();
        });

    }

}
