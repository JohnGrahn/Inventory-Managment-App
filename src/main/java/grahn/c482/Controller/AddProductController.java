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
 * Class creates AddProductController
 */
public class AddProductController implements Initializable {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    /**
     * Initialize local variable with Product
     */
    private Product product = new Product(0, "", 0.00, 0, 0, 0);
    private static int productId = 200;
    public static int getProductIdCount(){
        productId++;
        return productId;
    }

    @FXML
    private TextField addProductSearchField;
    @FXML
    private TableView<Part> addProductTable;
    @FXML
    private TableColumn<Part, Integer> addProductPartIdColumn;
    @FXML
    private TableColumn<Part, String> addPartNameColumn;
    @FXML
    private TableColumn<Part, Integer> addProductInventoryColumn;
    @FXML
    private TableColumn<Part, Double> addProductPriceColumn;
    @FXML
    private TableView<Part> associatedProductTable;
    @FXML
    private TableColumn<Part, Integer> associatedPartIdColumn;
    @FXML
    private TableColumn<Part, String> associatedPartNameColumn;
    @FXML
    private TableColumn<Part, Integer> associatedInventoryColumn;
    @FXML
    private TableColumn<Part, Double> associatedPriceColumn;
    @FXML
    private Button addProductAddButton;
    @FXML
    private Button removeAssociatedPartButton;
    @FXML
    private Button addProductSaveButton;
    @FXML
    private Button addProductCancelButton;
    @FXML
    private TextField addProductID;
    @FXML
    private TextField addProductName;
    @FXML
    private TextField addProductInventory;
    @FXML
    private TextField addProductPrice;
    @FXML
    private TextField addProductMaximum;
    @FXML
    private TextField addProductMinimum;

    /**
     * This method searches the parts table based on text input
     * @param event
     */

    @FXML void AddProdPartSearch(ActionEvent event) {
        String partSearch = addProductSearchField.getText();
        ObservableList<Part> searchResult = Inventory.lookupPart(partSearch);
                try {
                    while (searchResult.size() == 0) {
                        int partId = Integer.parseInt(partSearch);
                        searchResult.add(Inventory.lookupPart(partId));
                    }
                    addProductTable.setItems(searchResult);
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
    void AddProductAddAction(ActionEvent event) {
        Part selectedPart = addProductTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Improper Input");
            alert.setContentText("Select a part from the table");
            alert.showAndWait();

        } else  {
            associatedParts.add(selectedPart);

        }
    }

    /**
     * On click removes selected part from associated parts
     * @param event
     */
    @FXML
    void RemoveAssociatedPartAction(ActionEvent event) {
        Part selectedPart = addProductTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Improper Input");
            alert.setContentText("Select a part from the table");
            alert.showAndWait();

        } else  {
            associatedParts.remove(selectedPart);

        }
    }

    /**
     * On click saves new product
     * @param event
     * @throws IOException
     */
    @FXML
    void AddProductSaveAction(ActionEvent event) throws IOException {
        try {

            product.setId(Integer.parseInt(addProductID.getText()));
            product.setName(addProductName.getText());
            product.setStock(Integer.parseInt(addProductInventory.getText()));
            product.setPrice(Double.parseDouble(addProductPrice.getText()));
            product.setMax(Integer.parseInt(addProductMaximum.getText()));
            product.setMin(Integer.parseInt(addProductMinimum.getText()));


            if (addProductName.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setContentText("ERROR: Name cannot be empty");
                alert.showAndWait();
            } else if (Integer.parseInt(addProductMinimum.getText()) > Integer.parseInt(addProductMaximum.getText())) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setContentText("Maximum must be a larger number than minimum");
                alert.showAndWait();
            } else if (Integer.parseInt(addProductInventory.getText()) > Integer.parseInt(addProductMaximum.getText()) || Integer.parseInt(addProductInventory.getText()) < Integer.parseInt(addProductMinimum.getText())) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setContentText("Inventory must be a number between minimum and maximum");
                alert.showAndWait();
            } else {
                product.getAllAssociatedParts().setAll(associatedParts);
                Inventory.addProduct(product);
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
     * On click cancels add product action and returns user to main screen
     * @param event
     * @throws IOException
     */
    @FXML void AddProductCancelAction (ActionEvent event) throws IOException {
        Parent homeReturn = FXMLLoader.load(getClass().getResource("/grahn/c482/Main.fxml"));
        Scene scene = new Scene(homeReturn);
        Stage MainReturn = (Stage) ((Node)event.getSource()).getScene().getWindow();
        MainReturn.setScene(scene);
        MainReturn.show();
    }

    /**
     * Initializes Parts and Associated Parts table. Sets Product ID
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addProductTable.setItems(Inventory.getAllParts());
        addProductPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        addPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedProductTable.setItems(associatedParts);
        associatedPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        productId = getProductIdCount();
        addProductID.setText(String.valueOf(productId));
    }
}
