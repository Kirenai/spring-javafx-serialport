package pe.une.serial.app;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import pe.une.serial.SerialConnectionApplication;
import pe.une.serial.controllers.SerialController;

public class JavaFXApplication extends Application {

    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init() throws Exception {
        String[] args = super.getParameters().getRaw().toArray(new String[0]);

        this.applicationContext = new SpringApplicationBuilder()
            .sources(SerialConnectionApplication.class)
            .run(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FxWeaver fxWeaver = this.applicationContext.getBean(FxWeaver.class);
        Parent root = fxWeaver.loadView(SerialController.class);
        stage.setTitle("Serial Control App");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        this.applicationContext.close();
        Platform.exit();
    }

}
