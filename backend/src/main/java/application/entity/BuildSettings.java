package application.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Getter
@Setter
public class BuildSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private static BuildSettings instance;
    private String jarFilePath;
    private String launch4jPath ;
    private String outputName ;
    private String outputPath ;
    private String javafxLibPath ;
    private String mainClassJarFile;
    private String configFilePath;

    private String projectName;
    private String nameCompany;

    private String iconPath ;
    private boolean createDesktopShortcut ;

    public static BuildSettings getInstance() {
        if (instance == null) {
            instance = new BuildSettings();
        }
        return instance;
    }

}
