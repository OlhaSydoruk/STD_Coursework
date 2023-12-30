package application.pagesController;

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
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class FileController extends FxController {
    private Validation validator = new Validation();;
    private BuildSettings buildSettings = BuildSettings.getInstance();
    private String jarFilePath;
    private String outputPath;

    @Autowired
    public void setLoader(SpringFXMLLoader loader) {
        setSpringFXMLLoader(loader);
    }

    @FXML
    private TextField outputPathField;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnChoosePathToJar;

    @FXML
    private Button exit;

    @FXML
    private Button filesButton;

    @FXML
    private Button informationButton;

    @FXML
    private ImageView logo;

    @FXML
    private TextField filePathField;

    @FXML
    private Button saveBtn;

    @FXML
    private Button settingsButton;
    @FXML
    private Label showErrorField;

    @FXML
    private Button btnChooseOutputPath;
    @FXML
    private TextField outputName;

    private void validateFields() {
        validator.validate();
    }
    private void setLastEnteredValues(){
        outputPathField.setText(buildSettings.getOutputPath());
        outputName.setText(buildSettings.getOutputName());
        filePathField.setText(buildSettings.getJarFilePath());
    }
    private void setupValidationListeners() {
        outputPathField.textProperty().addListener((observable, oldValue, newValue) -> validateFields());
        outputName.textProperty().addListener((observable, oldValue, newValue) -> validateFields());
        filePathField.textProperty().addListener((observable, oldValue, newValue) -> validateFields());

    }
    private boolean dataSave() {

        validator.addFieldToValidationMap("outputPath", outputPathField);
        validator.addFieldToValidationMap("outputName", outputName);
        validator.addFieldToValidationMap("jarFilePath", filePathField);
        if(validator.validate()) {
            buildSettings.setOutputPath(outputPath);
            buildSettings.setOutputName(outputName.getText().trim());
            buildSettings.setJarFilePath(jarFilePath);
            return true;
        }else return false;
    }

    @FXML
    void initialize() {
        setLastEnteredValues();
        setupValidationListeners();
        filePathField.setEditable(false);

        informationButton.setOnAction(e -> {
            dataSave();
            Stage currentStage = getStageFromButton(informationButton);
            Platform.runLater(() -> {
                switchScene(currentStage, "/application/infoPage-view.fxml");
            });
        });
        settingsButton.setOnAction(e -> {
            dataSave();
            Stage currentStage = getStageFromButton(settingsButton);
            Platform.runLater(() -> {
                switchScene(currentStage, "/application/settingsPage-view.fxml");
            });
        });


        btnChoosePathToJar.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Оберите файл");

            File initialDirectory = new File("C:\\Users\\sid19\\Documents\\Olya\\Study\\5_Semestr\\TRPZ\\course_work");
            fileChooser.setInitialDirectory(initialDirectory);

            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JAR files (*.jar)", "*.jar");
            fileChooser.getExtensionFilters().add(extFilter);
            File selectedFile = fileChooser.showOpenDialog(new Stage());
            if (selectedFile != null) {
                jarFilePath = selectedFile.getAbsolutePath();
                filePathField.setText(jarFilePath);
            }
        });
        btnChooseOutputPath.setOnAction(e -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Оберіть директрорію для зберігання");

            File initialDirectory = new File("C:\\Users\\sid19\\Documents\\Olya\\Study\\5_Semestr\\TRPZ");
            directoryChooser.setInitialDirectory(initialDirectory);

            File selectedDirectory = directoryChooser.showDialog(new Stage());
            if (selectedDirectory != null) {
                outputPath = selectedDirectory.getAbsolutePath();
                String additionalPath = outputName.getText().trim();
                if (!additionalPath.isEmpty()) {
                    outputPath += File.separator + additionalPath + ".exe";
                }
                outputPathField.setText(outputPath);
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
