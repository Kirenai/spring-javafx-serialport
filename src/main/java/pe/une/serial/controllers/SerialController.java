package pe.une.serial.controllers;

import java.io.PrintWriter;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.fazecast.jSerialComm.SerialPort;

import org.springframework.stereotype.Component;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import net.rgielen.fxweaver.core.FxmlView;

@Component
@FxmlView("serial.fxml")
public class SerialController implements Initializable {

    @FXML private ComboBox<String> cbPorts;
    @FXML private Button btnConnection;
    @FXML private Button btnSend;
    @FXML private MenuItem miQuit;
    @FXML private Label lblMessagePort;
    @FXML private CheckBox cbxMotorOne;
    @FXML private CheckBox cbxMotorTwo;
    @FXML private CheckBox cbxMotorOneAndTwo;

    private PrintWriter out;
    private SerialPort port;

    @Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
        this.loadSerialPorts();
        this.disableCbxs(true);
    }

    @FXML
    public void btnConnectionAction(ActionEvent e) {
        if (this.btnConnection.getText().equals("Connect")) {
            String selected = this.cbPorts.getSelectionModel().getSelectedItem();
            if (selected == null) {
                this.lblMessagePort.setText("Select a port");
            } else {
                this.lblMessagePort.setText("");
                this.btnConnection.setText("Disconnect");
                this.cbPorts.setDisable(true);

                this.disableCbxs(false);

                this.port = SerialPort.getCommPort(selected);
                this.port.setComPortParameters(9600, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
                this.port.openPort();
                this.out = new PrintWriter(port.getOutputStream());
            } 
        } else {
            btnConnection.setText("Connect");
            cbPorts.setDisable(false);
            this.disableCbxs(true);
            this.out.close();
            this.port.closePort();
        }
    }

    @FXML
    public void cbxMotorOneAction(ActionEvent e) {
        if (cbxMotorOne.isSelected()) {
            this.cbxMotorOneAndTwo.setDisable(true);
            this.cbxMotorTwo.setDisable(true);
            this.sendCharToSerialPort('a');
        } else {
            this.cbxMotorOneAndTwo.setDisable(false);
            this.cbxMotorTwo.setDisable(false);
            this.sendCharToSerialPort('b');
        }
    }

    @FXML
    public void cbxMotorTwoAction(ActionEvent e) {
        if (cbxMotorTwo.isSelected()) {
            this.cbxMotorOneAndTwo.setDisable(true);
            this.cbxMotorOne.setDisable(true);
            this.sendCharToSerialPort('c');
        } else {
            this.cbxMotorOneAndTwo.setDisable(false);
            this.cbxMotorOne.setDisable(false);
            this.sendCharToSerialPort('d');
        }
    }
    
    @FXML
    public void cbxMotorOneAndTwoAction(ActionEvent e) {
        if (cbxMotorOneAndTwo.isSelected()) {
            this.cbxMotorOne.setDisable(true);
            this.cbxMotorTwo.setDisable(true);
            this.sendCharToSerialPort('e');
        } else {
            this.cbxMotorOne.setDisable(false);
            this.cbxMotorTwo.setDisable(false);
            this.sendCharToSerialPort('f');
        }
    }

    @FXML
    public void miQuitActtion(ActionEvent e) {
        Platform.exit();
    }
    
    private void loadSerialPorts() {
        ObservableList<String> items =  Arrays.stream(SerialPort.getCommPorts())
            .map(SerialPort::getSystemPortName)
            .collect(Collectors.toCollection(FXCollections::observableArrayList));
        this.cbPorts.setItems(items);
    }

    private void sendCharToSerialPort(char c) {
        this.out.print(c);
        this.out.flush();
    }

    private void disableCbxs(boolean isActive) {
        this.cbxMotorOne.setDisable(isActive);
        this.cbxMotorTwo.setDisable(isActive);
        this.cbxMotorOneAndTwo.setDisable(isActive);
    }

}
