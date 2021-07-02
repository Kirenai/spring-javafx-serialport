package pe.une.serial.config;

import org.springframework.context.ApplicationEvent;

import javafx.stage.Stage;

public class ConfigStageReadyEvent extends ApplicationEvent {

    public Stage getStage() {
        return Stage.class.cast(getSource());   
    }

    public ConfigStageReadyEvent(Stage source) {
        super(source);
    }
}
