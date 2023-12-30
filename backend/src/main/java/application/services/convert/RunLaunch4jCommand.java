package application.services.convert;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class RunLaunch4jCommand implements Command{
    private ConversionCallback callback;
    private String launch4jPath;
    private String configFilePath;
    private boolean createDesktopShortcut;
    private String outputName;
    private String outputPath;

    public RunLaunch4jCommand(String launch4jPath, String configFilePath, boolean createDesktopShortcut,
                              String outputName, String outputPath, ConversionCallback callback) {
        this.launch4jPath = launch4jPath;
        this.configFilePath = configFilePath;
        this.createDesktopShortcut = createDesktopShortcut;
        this.outputName = outputName;
        this.outputPath = outputPath;
        this.callback = callback;
    }

    @Override
    public void execute() {
        try {
            List<String> command = new ArrayList<>();
            command.add(launch4jPath);
            command.add(configFilePath);

            ProcessBuilder builder = new ProcessBuilder(command);
            Process process = builder.start();

            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String errorLine;
            while ((errorLine = errorReader.readLine()) != null) {
                System.out.println("Error: " + errorLine);
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {

                callback.onConversionResult(true, "Conversion successful!");
                if (createDesktopShortcut) createDesktopShortcut(outputName, outputPath);
            } else {
                callback.onConversionResult(false, "Conversion failed with exit code " + exitCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            callback.onConversionResult(false, "Exception occurred: " + e.getMessage());
        }
    }

    private void createDesktopShortcut(String outputName, String outputPath) {
        try {
            String shortcutPath = System.getProperty("user.home") + "\\Desktop\\" + outputName + ".lnk";
            String targetPath = outputPath;

            String script = "powershell.exe "
                    + "$WshShell = New-Object -comObject WScript.Shell; "
                    + "$Shortcut = $WshShell.CreateShortcut('" + shortcutPath + "'); "
                    + "$Shortcut.TargetPath = '" + targetPath + "'; "
                    + "$Shortcut.Save();";

            Process powerShellProcess = Runtime.getRuntime().exec(script);
            powerShellProcess.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
