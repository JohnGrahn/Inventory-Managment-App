/**
 * John Grahn
 * Student ID #000961901
 * C482 Software 1
 */

package grahn.c482;

import grahn.c482.Model.InHouse;
import grahn.c482.Model.Part;
import grahn.c482.Model.Inventory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import grahn.c482.Model.*;

/**
 * Main class creates inventory application and loads with dummy  data for testing
 */

public class Main extends Application {
    /**
     * Initializes application with Main.fxml
     * @param stage
     * @throws IOException
     */

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("John's Electronics");
        stage.setScene(scene);
        stage.show();
    }
/**
 * Main method loads dummy data and launches application
@param args
 */
    public static void main(String[] args) {
        Part batteries = new InHouse(33, "Batteries", 9.99, 10,2, 20, 1);
        Inventory.addPart(batteries);

        Part chargingCable = new InHouse(53,"Charging Cable", 4.99, 20, 5,30, 2);
        Inventory.addPart(chargingCable);

        Product xboxController = new Product(42, "Xbox Controller", 29.99, 10, 5, 20);
        xboxController.addAssociatedParts(batteries);
        Inventory.addProduct(xboxController);
        launch();
    }
}

