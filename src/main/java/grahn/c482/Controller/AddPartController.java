/**
 * John Grahn
 * Student ID #000961901
 * C482 Software 1
 */


package grahn.c482.Controller;

import grahn.c482.Model.InHouse;
import grahn.c482.Model.Inventory;
import grahn.c482.Model.Outsourced;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class creates AddPartController class
 */

public class AddPartController implements Initializable {
    Stage stage;
    Parent scene;
    private static int partId = 100;
    public static int getPartIdCount (){
        partId++;
        return partId;
    }

    @FXML private RadioButton partInHouseRadio;
    @FXML private RadioButton partOutsourcedRadio;
    @FXML private ToggleGroup togglePartType;
    @FXML private Label MachineOrCompany;
    @FXML private TextField addPartID;
    @FXML private TextField addPartName;
    @FXML private TextField addPartInventory;
    @FXML private TextField addPartPrice;
    @FXML private TextField addPartMaximum;
    @FXML private TextField addPartMinimum;
    @FXML private TextField addPartMachineID;
    @FXML private Button addPartSaveButton;
    @FXML private Button addPartCancelButton;

    /**
     *On radio button selection set text to Machine ID for InHouse
     * @param event
     */
    @FXML void OnSelectionAddPartInHouse (ActionEvent event) {
        MachineOrCompany.setText("Machine ID");
    }

    /**
     * On radio button selection set text to Company Name for Outsourced
     * @param event
     */
    @FXML void OnSelectionAddPartOutsourced (ActionEvent event) {
        MachineOrCompany.setText("Company Name");
    }

    /**
     * On button click saves part to inventory
     * RUNTIME ERROR caused by java.lang.NumberFormatException. Wrapped method in try statement with catch for NumberFormatException
     * @param event
     * @throws IOException
     */
    @FXML void AddPartSaveAction(ActionEvent event) throws IOException {
        try {
            int partId = Integer.parseInt(addPartID.getText());
            String name = addPartName.getText();
            int inventoryStock = Integer.parseInt(addPartInventory.getText());
            double price = Double.parseDouble(addPartPrice.getText());
            int maximum = Integer.parseInt(addPartMaximum.getText());
            int minimum = Integer.parseInt(addPartMinimum.getText());
            int machineId ;
            String companyName;

            if (name.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setContentText("ERROR: Name cannot be empty");
                alert.showAndWait();
                return;
            }
            else if (maximum < minimum) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setContentText("Maximum must be a larger number than minimum");
                alert.showAndWait();
                return;
            }
            else if (inventoryStock < minimum || inventoryStock > maximum) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setContentText("Maximum must be a larger number than minimum");
                alert.showAndWait();
                return;
            }
            else {
                if (partInHouseRadio.isSelected()) {
                    machineId = Integer.parseInt(addPartMachineID.getText());
                    Inventory.addPart(new InHouse(partId, name, price, inventoryStock, minimum, maximum, machineId));

                } else if (partOutsourcedRadio.isSelected()) {
                    companyName = addPartMachineID.getText();
                    Inventory.addPart(new Outsourced(partId, name, price, inventoryStock, minimum, maximum, companyName));
                }
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Object scene = FXMLLoader.load(getClass().getResource("/grahn/c482/Main.fxml"));
                stage.setScene(new Scene((Parent) scene));
                stage.show();
            }

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Input");
            alert.setContentText("Improper Value");
            alert.showAndWait();
            return;

        }

    }



    /**
     * Button click returns user to main view
     * @param event
     */
    @FXML public void AddPartCancelAction(ActionEvent event) throws IOException {
        Parent homeReturn = FXMLLoader.load(getClass().getResource("/grahn/c482/Main.fxml"));
        Scene scene = new Scene(homeReturn);
        Stage MainReturn = (Stage) ((Node)event.getSource()).getScene().getWindow();
        MainReturn.setScene(scene);
        MainReturn.show();

    }

    /**
     * Sets Radio button to InHouse by default. Also gets new part ID and assigns it to the uneditable Part ID text field
     * @param url
     * @param resourceBundle
     */
    @Override public void initialize (URL url, ResourceBundle resourceBundle) {
        partInHouseRadio.setSelected(true);
        partId = getPartIdCount();
        addPartID.setText(String.valueOf(partId));
    }
}
