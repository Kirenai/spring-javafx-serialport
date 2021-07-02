package pe.une.serial;

import static javafx.application.Application.launch;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import pe.une.serial.app.JavaFXApplication;

@SpringBootApplication
public class SerialConnectionApplication {

    public static void main(String[] args) {
        launch(JavaFXApplication.class, args);
    }
}
