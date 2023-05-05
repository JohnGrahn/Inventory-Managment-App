/**
 * John Grahn
 * Student ID #000961901
 * C482 Software 1
 */

package grahn.c482.Controller;

import grahn.c482.Model.Inventory;
import grahn.c482.Model.Part;
import grahn.c482.Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Creates ModifyProductController Class
 */
public class ModifyProductController  implements Initializable {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private ObservableList<Part> originalAssociatedParts = FXCollections.observableArrayList();

    private Product modifiedProduct;


    @FXML
    private TextField modifyProductSearchField;
    @FXML
    private TableView<Part> modifyProductTable;
    @FXML
    private TableColumn<Part, Integer> modifyProductPartIdColumn;
    @FXML
    private TableColumn<Part, String> modifyPartNameColumn;
    @FXML
    private TableColumn<Part, Integer> modifyProductInventoryColumn;
    @FXML
    private TableColumn<Part, Double> modifyProductPriceColumn;
    @FXML
    private TableView<Part> modifyAssociatedProductTable;
    @FXML
    private TableColumn<Part, Integer> modifyAssociatedProductIdColumn;
    @FXML
    private TableColumn<Part, String> modifyAssociatedPartNameColumn;
    @FXML
    private TableColumn<Part, Integer> modifyAssociatedInventoryColumn;
    @FXML
    private TableColumn<Part, Double> modifyAssociatedPriceColumn;
    @FXML
    private Button modifyAddProductModifyButton;
    @FXML
    private Button modifyRemoveAssociatedPartButton;
    @FXML
    private Button modifyProductSaveButton;
    @FXML
    private Button modifyProductCancelButton;
    @FXML
    private TextField modifyProductID;
    @FXML
    private TextField modifyProductName;
    @FXML
    private TextField modifyProductInventory;
    @FXML
    private TextField modifyProductPrice;
    @FXML
    private TextField modifyProductMaximum;
    @FXML
    private TextField modifyProductMinimum;

    /**
     * Receives data from main screen
     * @param product
     */

    public void sendProduct (Product product){

        modifiedProduct = product;
        modifyProductID.setText(String.valueOf(modifiedProduct.getId()));
        modifyProductName.setText(modifiedProduct.getName());
        modifyProductInventory.setText(String.valueOf(modifiedProduct.getStock()));
        modifyProductPrice.setText(String.valueOf(modifiedProduct.getPrice()));
        modifyProductMaximum.setText(String.valueOf(modifiedProduct.getMax()));
        modifyProductMinimum.setText(String.valueOf(modifiedProduct.getMin()));
        modifyAssociatedProductTable.setItems(modifiedProduct.getAllAssociatedParts());

        modifyAssociatedProductTable.setItems(product.getAllAssociatedParts());
        modifyAssociatedProductIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyAssociatedPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyAssociatedInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyAssociatedPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        originalAssociatedParts.setAll(product.getAllAssociatedParts());

    }
    /**
     * This method searches the parts table based on text input
     * @param event
     */

    @FXML
    void ModifyProductPartSearchAction(ActionEvent event) {
        String partSearch = modifyProductSearchField.getText();
        ObservableList<Part> searchResult = Inventory.lookupPart(partSearch);
        try {
            while (searchResult.size() == 0) {
                int partId = Integer.parseInt(partSearch);
                searchResult.add(Inventory.lookupPart(partId));
            }
            modifyProductTable.setItems(searchResult);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERROR");
            alert.setContentText("Match for part not found");
            alert.showAndWait();
        }
    }
    /**
     * On click adds selected part to associated parts for product
     * @param event
     */
    @FXML
    void ModifyAddPartProductModifyAction(ActionEvent event) {
        Part selectedPart = modifyProductTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Improper Input");
            alert.setContentText("Select a part from the table");
            alert.showAndWait();
            return;
        } else {
            modifiedProduct.addAssociatedParts(selectedPart);

        }
    }
    /**
     * On click removes selected part from associated parts
     * @param event
     */
    @FXML
    void ModifyRemoveAssociatedPartButton(ActionEvent event) {
        Part selectedPart = modifyAssociatedProductTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Improper Input");
            alert.setContentText("Select a part from the table");
            alert.showAndWait();
            return;
        } else {
            modifiedProduct.deleteAssociatedParts(selectedPart);
        }
    }

    /**
     * On click saves Modified Product
     * @param event
     * @throws IOException
     */
    @FXML
    void ModifyProductSaveAction(ActionEvent event) throws IOException {
        try {
            modifiedProduct.setId(Integer.parseInt(modifyProductID.getText()));
            modifiedProduct.setName(modifyProductName.getText());
            modifiedProduct.setPrice(Double.parseDouble(modifyProductPrice.getText()));
            modifiedProduct.setStock(Integer.parseInt(modifyProductInventory.getText()));
            modifiedProduct.setMax(Integer.parseInt(modifyProductMaximum.getText()));
            modifiedProduct.setMin(Integer.parseInt(modifyProductMinimum.getText()));


            if (modifyProductName.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setContentText("ERROR: Name cannot be empty");
                alert.showAndWait();
            } else if (Integer.parseInt(modifyProductMinimum.getText()) > Integer.parseInt(modifyProductMaximum.getText())) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setContentText("Maximum must be a larger number than minimum");
                alert.showAndWait();
            } else if (Integer.parseInt(modifyProductInventory.getText()) > Integer.parseInt(modifyProductMaximum.getText()) || Integer.parseInt(modifyProductInventory.getText()) < Integer.parseInt(modifyProductMinimum.getText())) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setContentText("Inventory must be a number between minimum and maximum");
                alert.showAndWait();
            } else {

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


        }
    }

    /**
     * On click cancels modify product and returns user to main screen
     * @param event
     * @throws IOException
     */
    @FXML void ModifyProductActionCancel (ActionEvent event) throws IOException {
        modifiedProduct.getAllAssociatedParts().clear();
        modifiedProduct.getAllAssociatedParts().setAll(originalAssociatedParts);
        Parent homeReturn = FXMLLoader.load(getClass().getResource("/grahn/c482/Main.fxml"));
        Scene scene = new Scene(homeReturn);
        Stage MainReturn = (Stage) ((Node)event.getSource()).getScene().getWindow();
        MainReturn.setScene(scene);
        MainReturn.show();
    }

    /**
     * Loads views and populates tables
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modifyProductTable.setItems(Inventory.getAllParts());
        modifyProductPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProductInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyProductPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));


    }
}