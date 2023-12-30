package application.services.convert;

public class CreateLaunch4jConfigCommand implements Command {
    private LaunchConfigBuilder builder;
    private String configXml;

    public CreateLaunch4jConfigCommand(LaunchConfigBuilder builder) {
        this.builder = builder;
    }

    @Override
    public void execute() {
        this.configXml = builder.build();
    }

    public String getConfigXml() {
        return configXml;
    }
}
