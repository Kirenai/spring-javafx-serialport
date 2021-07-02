package pe.une.serial.config;

import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

@Component
public class ConfigStageListenner implements ApplicationListener<ConfigStageReadyEvent>{

    private final String title; 
    private final Resource fxml; 
    private final ApplicationContext applicationContext;

	ConfigStageListenner(
            @Value(value = "${ui.title}") String title, 
            @Value(value = "classpath:/serial.fxml") Resource fxml, 
            ApplicationContext ac) {
        this.title = title;
        this.fxml = fxml;
        this.applicationContext = ac;
    }

    @Override
	public void onApplicationEvent(ConfigStageReadyEvent stageReadyEvent) {
		Stage stage = stageReadyEvent.getStage();
        try {
            URL url = this.fxml.getURL();
            FXMLLoader loader = new FXMLLoader(url);
            loader.setControllerFactory(applicationContext::getBean);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(this.title);
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException();
        }
	}


}
