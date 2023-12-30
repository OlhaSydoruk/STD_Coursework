package application.services.convert;

public class LaunchConfigBuilder {
    private String jarPath;
    private String exePath;
    private String iconPath;
    private String javafxLibPath;
    private String mainClassJarFile;

    public LaunchConfigBuilder setJarPath(String jarPath) {
        this.jarPath = jarPath;
        return this;
    }

    public LaunchConfigBuilder setExePath(String exePath) {
        this.exePath = exePath;
        return this;
    }

    public LaunchConfigBuilder setIconPath(String iconPath) {
        this.iconPath = iconPath;
        return this;
    }

    public LaunchConfigBuilder setJavafxLibPath(String javafxLibPath) {
        this.javafxLibPath = javafxLibPath;
        return this;
    }

    public LaunchConfigBuilder setMainClassJarFile(String mainClassJarFile) {
        this.mainClassJarFile = mainClassJarFile;
        return this;
    }

    public String build() {
        StringBuilder xmlConfig = new StringBuilder();
        xmlConfig.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
                .append("<launch4jConfig>\n")
                .append("  <dontWrapJar>false</dontWrapJar>\n")
                .append("  <headerType>gui</headerType>\n")
                .append("  <jar>").append(jarPath).append("</jar>\n")
                .append("  <outfile>").append(exePath).append("</outfile>\n");
        if (iconPath != null && !iconPath.isEmpty()) {
            xmlConfig.append("  <icon>").append(iconPath).append("</icon>\n");
        }
        xmlConfig.append("  <jre>\n")
                .append("    <path></path>\n")
                .append("    <minVersion>1.8.0</minVersion>\n")
                .append("    <maxVersion></maxVersion>\n")
                .append("    <opt>--module-path \"").append(javafxLibPath).append("\"</opt>\n")
                .append("    <opt>--add-modules javafx.controls,javafx.fxml</opt>\n")
                .append("  </jre>\n")
                .append("  <classPath>\n")
                .append("    <mainClass>\"").append(mainClassJarFile).append("\"</mainClass>\n")
                .append("  </classPath>\n")
                .append("</launch4jConfig>");
        return xmlConfig.toString();
    }
}
