/**
 * John Grahn
 * Student ID #000961901
 * C482 Software 1
 */
package grahn.c482.Model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Creates Inventory class
 */

public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Gets and returns all parts from Part list
     * @return
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Gets and returns all products from Product List
     * @return
     */

    public static ObservableList<Product> getAllProducts(){

        return allProducts;
    }

    /**
     * Adds part to Part list
     * @param newPart
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Adds product to Product list
     * @param newProduct
     */
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }

    /**
     * Searches for parts in Inventory by ID
     * @param partId
     * @return
     */
    public static Part lookupPart(int partId){
        for(Part part: Inventory.getAllParts()){
            while (part.getId() == partId)
                return part;
        }
        return null;
    }

    /**
     * Searches for products in Inventory by ID
     * @param productId
     * @return
     */
    public static Product lookupProduct (int productId) {
        for (Product product : Inventory.getAllProducts()) {
            while (product.getId() == productId)
                return product;
        }
        return null;
    }

    /**
     * Searches for parts in Inventory by Name
     * @param partName
     * @return
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> PartName = FXCollections.observableArrayList();

        for (Part part: allParts) {
            if (part.getName().contains(partName)) {
                PartName.add(part);
            }
        }
        return PartName;
    }

    /**
     * Searches for products in Inventory by Name
     * @param productName
     * @return
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> ProductName = FXCollections.observableArrayList();

        for (Product product: allProducts) {
            if (product.getName().contains(productName)) {
                ProductName.add(product);
            }
        }
        return ProductName;
    }

    /**
     * Updates current part data with new input
     * @param id
     * @param selectedPart
     */
    public static void updatePart(int id, Part selectedPart) {

        int index = -1;
        for (Part Part : getAllParts()) {
            index++;
            if (Part.getId() == id) {
                getAllParts().set(index, selectedPart);
            }
        }
    }

    /**
     * Updates current product data with new input
     * @param id
     * @param selectedProduct
     */
    public static void updateProduct(int id, Product selectedProduct)
    {
        int index = -1;
        for (Product Product : Inventory.getAllProducts()) {
            index++;
            if (Product.getId() == id) {
                Inventory.getAllProducts().set(index, selectedProduct);
            }
        }
    }

    /**
     * When called deletes Part from Inventory
     * @param selectedPart
     * @return
     */
    public static boolean deletePart (Part selectedPart) {
        if (allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        } else {
            return false;
        }
    }

    /**
     * When called delete
     * @param selectedProduct
     * @return
     */
    public static boolean deleteProduct (Product selectedProduct) {
        if(allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        } else {
            return false;
        }
    }
}
