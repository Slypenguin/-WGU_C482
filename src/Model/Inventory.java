package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 Inventory class holds the Part and Product lists and functions.
 */
public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();


    /**
     *  Adds a new part to inventory. Adds it to the allParts ObservableList.
     */
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }

    /**
     * Adds a new product to inventory. Adds it to the allProducts ObservableList.
     * */
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }

    /**
     * This method looks up a part. It iterates through a loop to look for a matching part ID.
     * */
    public static Part lookupPart(int partToLookup) {
        if (!allParts.isEmpty()) {
            for (int i = 0; i < allParts.size(); i++) {
                if (allParts.get(i).getId() == partToLookup) {
                    return allParts.get(i);
                }
            }
        }
        return null;
    }

    /**
     * This method looks up a product. It iterates through a loop to look for a matching product ID.
     * */
    public static Product lookupProduct(int productToLookup) {
        if (!allProducts.isEmpty()) {
            for (int i = 0; i < allProducts.size(); i++) {
                if (allProducts.get(i).getId() == productToLookup) {
                    return allProducts.get(i);
                }
            }
        }
        return null;
    }

    /**
     * This method creates a list for the parts searched. It is used in the various parts tableview.
     * */
    public static ObservableList<Part> lookupPart(String PartToLookup) {
        ObservableList<Part> searchList = FXCollections.observableArrayList();

        ObservableList<Part> allParts = getAllParts();

        for(Part p : allParts){
            if(p.getName().contains(PartToLookup)){
                searchList.add(p);
            }

        }

        return searchList;
    }

    /**
     * Creates a list for the products searched. It is used on the main form's product tableview.
     * */
    public static ObservableList<Product> lookupProduct(String ProductToLookup) {
       ObservableList<Product> searchList = FXCollections.observableArrayList();
       ObservableList<Product> allProds = getAllProducts();


       for(Product p : allProds){
           if(p.getName().contains(ProductToLookup)){
               searchList.add(p);
           }
       }
       return searchList;
    }

    /**
     * This method updates a part based on the index. It puts the new part in place of the old on the array.
     */
    public static void updatePart(int index, Part newPart) {
        allParts.set(index, newPart);
    }

    /**
     * This method updates a product based on the index. It puts a new product in place of the old on the array.
     * */
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /**
     * This method deletes a selected Part. It removes it from the all parts list.
     * */
    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    /**
     * This method deletes a selected Product. It removes it from the all products list.*/
    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    /**
     * @return  all parts
     * */
    public static ObservableList<Part> getAllParts() {return allParts; }

    /**
     * @return all products
     * */
    public static ObservableList<Product> getAllProducts() {return allProducts;}



}
