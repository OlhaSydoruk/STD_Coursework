package application.services;

import application.entity.BuildSettings;
import application.services.convert.ConversionCallback;
import application.services.convert.JarToExeConverter;
import application.services.convert.RunLaunch4jCommand;

public class ConversionFacade {

    public void processConversion(BuildSettings settings, ConversionCallback callback) {
        JarToExeConverter converter = new JarToExeConverter();

        converter.setJarFilePath(settings.getJarFilePath());
        converter.setLaunch4jPath(settings.getLaunch4jPath());
        converter.setOutputName(settings.getOutputName());
        converter.setOutputPath(settings.getOutputPath());
        converter.setConfigFilePath(settings.getConfigFilePath());
        converter.setMainClassJarFile(settings.getMainClassJarFile());
        converter.setJavafxLibPath(settings.getJavafxLibPath());

        converter.setCreateDesktopShortcut(settings.isCreateDesktopShortcut());
        converter.setIconPath(settings.getIconPath());
        converter.convertJarToExe();


        RunLaunch4jCommand runLaunch4jCommand = new RunLaunch4jCommand(settings.getLaunch4jPath(), settings.getConfigFilePath(), settings.isCreateDesktopShortcut(), settings.getOutputName(), settings.getOutputPath(), callback);
        runLaunch4jCommand.execute();
    }
}

