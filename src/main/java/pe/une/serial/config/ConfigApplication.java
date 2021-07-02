package pe.une.serial.config;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.stage.Stage;
import pe.une.serial.SerialConnectionApplication;

public class ConfigApplication extends Application {

    private ConfigurableApplicationContext context;

    @Override
    public void init() throws Exception {
        ApplicationContextInitializer<GenericApplicationContext> initializer = 
            ac -> {
                ac.registerBean(Application.class, () -> ConfigApplication.this);
                ac.registerBean(Parameters.class, this::getParameters);
                ac.registerBean(HostServices.class, this::getHostServices);
            };

        this.context = new SpringApplicationBuilder()
            .sources(SerialConnectionApplication.class)
            .initializers(initializer)
            .run(getParameters().getRaw().toArray(new String[0]));
    }

    @Override
	public void start(Stage primaryStage) throws Exception {
        this.context.publishEvent(new ConfigStageReadyEvent(primaryStage));
	}

    @Override
    public void stop() throws Exception {
        this.context.close();
        Platform.exit();
    }
    
}

