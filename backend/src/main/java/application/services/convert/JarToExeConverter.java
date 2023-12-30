package application.services.convert;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.nio.file.Files;
import java.nio.file.Paths;
@ToString
@Getter
@Setter
public class JarToExeConverter {
    //обов'язкові поля
    private String jarFilePath ;
    private String launch4jPath ;
    private String outputName;
    private String outputPath;
    private String javafxLibPath;
    private String mainClassJarFile;
    private String configFilePath;

    //не обов'язкові поля
    private boolean createDesktopShortcut;
    private String iconPath ;

    public void convertJarToExe() {
        try {
            LaunchConfigBuilder builder = new LaunchConfigBuilder()
                    .setJarPath(jarFilePath)
                    .setExePath(outputPath)
                    .setIconPath(iconPath)
                    .setJavafxLibPath(javafxLibPath)
                    .setMainClassJarFile(mainClassJarFile);

            CreateLaunch4jConfigCommand createConfigCommand = new CreateLaunch4jConfigCommand(builder);
            createConfigCommand.execute();
            String configXml = createConfigCommand.getConfigXml();

            Files.writeString(Paths.get(configFilePath), configXml);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

