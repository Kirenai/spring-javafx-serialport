# Spring boot and JavaFX 11

Prototype for connection and communication through the COMX serial port in DOS OS

## Technologies & APIs

* Spring boot
* JavaFX 11 with FXML
* SceneBuilder 11 - 16
* jSerialComm 
* javafx-weaver-spring-boot-starter
* JDK 11
* Maven

### Objetive

create a GUI to manipulate the pic16f877a microcontroller through the serial port, 
which in turn will control the on and off of three-phase motors with already built hardware.

#### Additional

The project has two configurations, one in the app folder and the main execution and the
second configuration in the config folder that also works, 
but you have to change the application startup and stop using the javafx-weaver dependency.

