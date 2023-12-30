package application.pagesController;

import application.SpringFXMLLoader;
import application.constatns.ConvertConstants;
import application.entity.BuildSettings;
import application.services.ConversionFacade;
import application.services.convert.ConversionCallback;
import application.validator.Validation;
import javafx.application.Platform;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

@Component
public class SettingsController extends FxController {
    BuildSettings buildSettings = BuildSettings.getInstance();
    Validation validator = new Validation();

    @Autowired
    public void setLoader(SpringFXMLLoader loader) {
        setSpringFXMLLoader(loader);
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private CheckBox createShortcut;

    @FXML
    private Button btnConvert;
    @FXML
    private Button exit;
    @FXML
    private TextField pathToMainClassJar;

    @FXML
    private Button filesButton;

    @FXML
    private Button informationButton;

    @FXML
    private ImageView logo;
    @FXML
    private Label showErrorField;

    @FXML
    private Button saveBtn;

    @FXML
    private Button settingsButton;

    private void validateFields() {
        validator.validate();
    }
    private void setLastEnteredValues(){
        pathToMainClassJar.setText(buildSettings.getMainClassJarFile());
        createShortcut.setSelected(buildSettings.isCreateDesktopShortcut());
    }
    private void setupValidationListeners() {
        pathToMainClassJar.textProperty().addListener((observable, oldValue, newValue) -> validateFields());
    }

    private boolean dataIsNotNull(){
        if (buildSettings.getJarFilePath() == null ||
                buildSettings.getOutputName() == null ||
                buildSettings.getOutputPath() == null ||
                buildSettings.getMainClassJarFile() == null) {

            showErrorField.setText("Check the entered data");
            showErrorField.setStyle("-fx-text-fill: red;");
            return false;
        }
        return true;
    }
    private boolean dataSave() {
        validator.addFieldToValidationMap("pathToMainClassJar", pathToMainClassJar);
        if (validator.validate()) {
            buildSettings.setMainClassJarFile(pathToMainClassJar.getText());
            buildSettings.setCreateDesktopShortcut(createShortcut.isSelected());
            buildSettings.setJavafxLibPath(ConvertConstants.JavaFX_LIB_PATH);
            buildSettings.setLaunch4jPath(ConvertConstants.LAUNCH4J_PATH);
            buildSettings.setConfigFilePath(ConvertConstants.CONFIG_FILE_PATH);
            return true;
        } else {
            return false;
        }
    }
    private void startConversion() {
        ConversionCallback callback = (success, message) -> {
            if (success) {
                showErrorField.setText(message);
                showErrorField.setStyle("-fx-text-fill: #04a404;");
            } else {
                showErrorField.setText(message);
                showErrorField.setStyle("-fx-text-fill: red;");
            }
        };

        ConversionFacade facade = new ConversionFacade();
        facade.processConversion(buildSettings, callback);
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

        informationButton.setOnAction(e -> {
            dataSave();
            Stage currentStage = getStageFromButton(informationButton);
            Platform.runLater(() -> {
                switchScene(currentStage, "/application/infoPage-view.fxml");
            });
        });

        saveBtn.setOnAction(e -> {
            handleSave(showErrorField, this::dataSave);
        });

        btnConvert.setOnAction(e -> {
            dataSave();
            if(dataIsNotNull()){
                startConversion();
            }
        });
        exit.setOnAction(e->{
            Platform.exit();
            this.applicationContext.close();
        });
    }

}
