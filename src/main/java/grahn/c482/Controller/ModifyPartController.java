/**
 * John Grahn
 * Student ID #000961901
 * C482 Software 1
 */

package grahn.c482.Controller;

import grahn.c482.Model.InHouse;
import grahn.c482.Model.Inventory;
import grahn.c482.Model.Outsourced;
import grahn.c482.Model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Class creates ModifyPartController
 */
public class ModifyPartController {
    @FXML
    private RadioButton modifyPartInHouseRadio;
    @FXML
    private RadioButton modifyPartOutsourcedRadio;
    @FXML
    private ToggleGroup togglePartType;
    @FXML
    private Label MachineOrCompany;
    @FXML
    private TextField modifyPartID;
    @FXML
    private TextField modifyPartName;
    @FXML
    private TextField modifyPartInventory;
    @FXML
    private TextField modifyPartPrice;
    @FXML
    private TextField modifyPartMaximum;
    @FXML
    private TextField modifyPartMinimum;
    @FXML
    private TextField modifyPartMachineID;
    @FXML
    private Button modifyPartSaveButton;
    @FXML
    private Button modifyPartCancelButton;
    /**
     *On radio button selection set text to Machine ID for InHouse
     * @param event
     */
    @FXML
    void OnSelectionModifyPartInHouse(ActionEvent event) {
        MachineOrCompany.setText("Machine ID");
    }
    /**
     * On radio button selection set text to Company Name for Outsourced
     * @param event
     */

    @FXML
    void OnSelectionModifyPartOutsourced(ActionEvent event) {
        MachineOrCompany.setText("Company Name");
    }

    /**
     * Receives data from main screen
     * @param part
     */

    public void sendPart(Part part) {
        modifyPartID.setText(String.valueOf(part.getId()));
        modifyPartName.setText(String.valueOf(part.getName()));
        modifyPartInventory.setText(String.valueOf(part.getStock()));
        modifyPartPrice.setText(String.valueOf(part.getPrice()));
        modifyPartMaximum.setText(String.valueOf(part.getMax()));
        modifyPartMinimum.setText(String.valueOf(part.getMin()));


        if (part instanceof InHouse){
            modifyPartInHouseRadio.setSelected(true);
            MachineOrCompany.setText("Machine ID");
            modifyPartMachineID.setText(String.valueOf(((InHouse)part).getMachineId()));
        }
        else {
            modifyPartOutsourcedRadio.setSelected(true);
            MachineOrCompany.setText("Company Name");
            modifyPartMachineID.setText(((Outsourced) part).getCompanyName());
        }


    }

    /**
     * On button click saves Modified Part
     * @param event
     * @throws IOException
     */
    @FXML
    void ModifyPartSaveAction(ActionEvent event) throws IOException {
        try {
            int partId = Integer.parseInt(modifyPartID.getText());
            String name = modifyPartName.getText();
            int inventoryStock = Integer.parseInt(modifyPartInventory.getText());
            double price = Double.parseDouble(modifyPartPrice.getText());
            int maximum = Integer.parseInt(modifyPartMaximum.getText());
            int minimum = Integer.parseInt(modifyPartMinimum.getText());
            int machineId;
            String companyName;

            if (maximum < minimum) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Maximum has to be a larger number than minimum");
                alert.setTitle("Warning Dialog");
                alert.showAndWait();
                return;
            } else if (inventoryStock < minimum || inventoryStock > maximum) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory must be a number between minimum and maximum");
                alert.setTitle("Warning Dialog");
                alert.showAndWait();
                return;
            } else if (name.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Name cannot be empty");
                alert.setTitle("Warning Dialog");
                alert.showAndWait();
                return;
            }

            if (modifyPartInHouseRadio.isSelected()) {
                machineId = Integer.parseInt(modifyPartMachineID.getText());

                Inventory.updatePart(partId, new InHouse(partId, name, price, inventoryStock, minimum, maximum, machineId));
            }
            if (modifyPartOutsourcedRadio.isSelected()) {
                companyName = modifyPartMachineID.getText();

                Inventory.updatePart(partId, new Outsourced(partId, name, price, inventoryStock, minimum, maximum, companyName));
            }
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("/grahn/c482/Main.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.show();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Input");
            alert.setContentText("Improper Value");
            alert.showAndWait();
            return;

        }
    }

    /**
     * On Button click returns user to main screen
     * @param event
     * @throws IOException
     */

    @FXML
    public void ModifyPartCancelAction(ActionEvent event) throws IOException {
        Parent homeReturn = FXMLLoader.load(getClass().getResource("/grahn/c482/Main.fxml"));
        Scene scene = new Scene(homeReturn);
        Stage MainReturn = (Stage) ((Node) event.getSource()).getScene().getWindow();
        MainReturn.setScene(scene);
        MainReturn.show();
        }
    }
