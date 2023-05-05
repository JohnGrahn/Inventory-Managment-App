/**
 * John Grahn
 * Student ID #000961901
 * C482 Software 1
 */


package grahn.c482.Controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import grahn.c482.Model.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Creates MainController class
 */

public class MainController implements Initializable {
   Stage stage;
   Parent scene;
    /**
     * Part
     */

    @FXML private TableView<Part> mainPartsTable;
    @FXML private TableColumn<Part, Integer> partIdColumn;
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, Integer> partInventoryColumn;
    @FXML private TableColumn<Part, Double> partPriceColumn;
    @FXML private TextField mainPartSearch;
    @FXML private Button addPartButton;
    @FXML private Button modifyPartButton;
    @FXML private Button deletePartButton;

    /**
     *Product
     */

    @FXML private TableView<Product> mainProductsTable;
    @FXML private TableColumn<Product, Integer> productIdColumn;
    @FXML private TableColumn<Product, String> productNameColumn;
    @FXML private TableColumn<Product, Integer> productInventoryColumn;
    @FXML private TableColumn<Product, Double> productPriceColumn;
    @FXML private TextField mainProductSearch;
    @FXML private Button addProductButton;
    @FXML private Button modifyProductButton;
    @FXML private Button deleteProductButton;

    @FXML private Button mainExit;


    /**
     * Loads AddPart.fxml when Add button under Parts section is clicked
     * @param event On click Add Part
     * @throws IOException
     */
    @FXML void MainAddPartAction(ActionEvent event) throws IOException {
        Parent addParts = FXMLLoader.load(getClass().getResource("/grahn/c482/AddPart.fxml"));
        Scene scene = new Scene(addParts);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Loads ModifyPart.fxml when Modify button under Parts section is clicked
     * @param event On click Modify Part
     * @throws IOException
     */
    @FXML void MainModifyPartAction(ActionEvent event) throws IOException {
       try {

               FXMLLoader loader = new FXMLLoader();
               loader.setLocation(getClass().getResource("/grahn/c482/ModifyPart.fxml"));
               loader.load();
               ModifyPartController ModPartController = loader.getController();
               ModPartController.sendPart(mainPartsTable.getSelectionModel().getSelectedItem());
               stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
               Parent scene = loader.getRoot();
               stage.setScene(new Scene(scene));
               stage.show();

           } catch (NullPointerException e) {
               Alert alert = new Alert(Alert.AlertType.WARNING);
               alert.setTitle("Warning");
               alert.setContentText("Must select a part");
               alert.showAndWait();
       }
    }

    /**
     * Deletes selected part when Delete button under parts
     * @param event On click Delete Part
     * @throws IOException
     */
    @FXML void MainDeletePartAction (ActionEvent event) throws IOException {
        Part selectedPart = mainPartsTable.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Part not selected");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setContentText("Do you want to delete the selected part?");
            Optional<ButtonType> deleteResult = alert.showAndWait();

            if (deleteResult.isPresent() && deleteResult.get() == ButtonType.OK) {
                Inventory.deletePart(selectedPart);
            }
        }

    }

    /**
     * Searches Parts inventory for parts with part ID or part Name
     * @param event
     */
    @FXML void MainPartSearch (ActionEvent event) {
        String partSearch =  mainPartSearch.getText();
        ObservableList<Part> searchResult = Inventory.lookupPart(partSearch);
        try {
            while (searchResult.size() == 0 ) {
                int partId = Integer.parseInt(partSearch);
                searchResult.add(Inventory.lookupPart(partId));
            }
            mainPartsTable.setItems(searchResult);
        }catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("Part was not found");
            alert.showAndWait();
        }
    }

    /**
     * Loads AddProduct screen when add button under Product section is clicked
     * @param event
     * @throws IOException
     */
    @FXML void MainAddProductAction (ActionEvent event) throws IOException{
        Parent addProducts = FXMLLoader.load(getClass().getResource("/grahn/c482/AddProduct.fxml"));
        Scene scene = new Scene(addProducts);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Loads ModifyProduct screen when modify button under Product section is clicked
     * @param event
     * @throws IOException
     */

    @FXML void MainModifyProductAction (ActionEvent event) throws IOException{
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/grahn/c482/ModifyProduct.fxml"));
            loader.load();
            ModifyProductController ModProdController = loader.getController();
            ModProdController.sendProduct(mainProductsTable.getSelectionModel().getSelectedItem());
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();

        }catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Must select a product");
            alert.showAndWait();
        }
    }

    /**
     * Deletes selected Product when delete button under Product section is clicked
     * @param event
     */
    @FXML void MainDeleteProductAction (ActionEvent event) {
        Product selectedProduct = mainProductsTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Product not selected");
            alert.showAndWait();
        } else if (!selectedProduct.getAllAssociatedParts().isEmpty()) {
            Alert associatedAlert = new Alert(Alert.AlertType.WARNING);
            associatedAlert.setTitle("Warning");
            associatedAlert.setContentText("Remove parts associated with the product before deletion");
            associatedAlert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setContentText("Do you want to delete the selected product?");
            Optional<ButtonType> deleteResult = alert.showAndWait();

            if (deleteResult.isPresent() && deleteResult.get() == ButtonType.OK) {
                Inventory.deleteProduct(selectedProduct);
                }
            }

        }

    /**
     * Searches Product inventory for products with product ID or product Name
     * @param event
     */
    @FXML void MainProductSearch (ActionEvent event) {
        String productSearch = mainProductSearch.getText();
        ObservableList<Product> searchResult = Inventory.lookupProduct(productSearch);
        try {
            while (searchResult.size() == 0 ) {
                int productId = Integer.parseInt(productSearch);
                searchResult.add(Inventory.lookupProduct(productId));
            }
            mainProductsTable.setItems(searchResult);
        }catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("Product was not found");
            alert.showAndWait();
        }
    }


    /**
     * Initializes Parts and Product Table
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainPartsTable.setItems(Inventory.getAllParts());
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        mainProductsTable.setItems(Inventory.getAllProducts());
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));


    }

    /**
     * On click closes out the Application
     * @param event
     */
    @FXML void MainExitAction (ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Confirm you wish to exit the program");
        Optional<ButtonType> confirmExit = alert.showAndWait();
        if (confirmExit.isPresent() && confirmExit.get() == ButtonType.OK) {
            System.exit(0);
        }
    }
}




