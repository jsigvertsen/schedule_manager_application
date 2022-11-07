package sigvertsen.c195;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sigvertsen.c195.helper.JDBC;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/** This is the main class for the Appointment Manager application*/
public class main extends Application {

    ResourceBundle rb = ResourceBundle.getBundle("/nat", Locale.getDefault());

    /** This is the start method*/
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("view/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 300);
        stage.setTitle(rb.getString("page_title"));
        stage.setScene(scene);
        stage.show();
    }

    /** This is the main method*/
    public static void main(String[] args) {

        JDBC.openConnection();

        launch();
    }
}