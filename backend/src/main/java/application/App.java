package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;


public class App extends Application {
    private ConfigurableApplicationContext applicationContext;
    @Override
    public void init(){
        String[] args = getParameters().getRaw().toArray(new String[0]);
        applicationContext = new SpringApplicationBuilder().sources(CourseWorkSpringApplication.class).run(args);
    }

    @Override
    public void start(Stage stage) {
       applicationContext.publishEvent(new StageReadyEvent(stage));
    }
    @Override
    public void stop(){
        applicationContext.close();
        Platform.exit();
    }

    static class StageReadyEvent extends ApplicationEvent {
        public StageReadyEvent(Stage stage) {
            super(stage);
        }
        public Stage getStage(){
            return ((Stage) getSource());
        }
    }
}
