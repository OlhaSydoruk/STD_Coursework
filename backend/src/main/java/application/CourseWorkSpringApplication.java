package application;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CourseWorkSpringApplication {

    public static void main(String[] args) {
        //SpringApplication.run(CourseWorkSpringApplication.class, args);
        Application.launch(App.class, args);
    }

}
