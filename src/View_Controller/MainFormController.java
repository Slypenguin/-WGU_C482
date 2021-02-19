package View_Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** implements the main form controller. */
public class MainFormController implements Initializable {
    /** controls the part search text field. */
    public TextField MainPartSearch;
    /** controls the parts tableview. */
    public TableView MainPartsTable;
    /** controls the part id column. */
    public TableColumn PartIDColumn;
    /** controls the part inventory column. */
    public TableColumn PartInvLevelColumn;
    /** controls the part name column. */
    public TableColumn PartNameColumn;
    /** controls the part price column. */
    public TableColumn PartPriceCostColumn;
    /** controls the add part button. */
    public Button AddPartButton;
    /** controls the modify part button. */
    public Button ModifyPartButton;
    /** controls the delete part button. */
    public Button DeletePartButton;
    /** controls the product table view. */
    public TableView MainProductsTable;
    /** controls the product id column. */
    public TableColumn ProductIDColumn;
    /** controls the product name column. */
    public TableColumn ProductNameColumn;
    /** controls the product inventory column. */
    public TableColumn ProductInvLevelColumn;
    /** controls the product price column. */
    public TableColumn ProductPriceCostColumn;
    /** controls the add product button. */
    public Button AddProductButton;
    /** controls the modify product button. */
    public Button ModifyProductButton;
    /** controls the delete product button. */
    public Button DeleteProductButton;
    /** controls the exit button. */
    public Button MainExitButton;
    /** controls the part search button. */
    public Button PartSearchButton;
    /** controls the product search button. */
    public Button ProdSearchButton;
    /** controls the product search text field. */
    public TextField ProductSearch;


    @Override
    /** initializes the tables. */
    public void initialize(URL url, ResourceBundle resourceBundle) {

        MainPartsTable.setItems(Inventory.getAllParts());
        PartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartInvLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartPriceCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        MainProductsTable.setItems(Inventory.getAllProducts());
        ProductIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        ProductNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ProductInvLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ProductPriceCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /** This method takes you to the add part form. */
    public void AddPartOnClick(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) MainExitButton.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("/View_Controller/AddPartForm.fxml"));
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    /** This method takes you to the modify part form. */
    public void ModifyPartOnClick(ActionEvent actionEvent) throws IOException {
        if (MainPartsTable.getSelectionModel().getSelectedItem() != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/ModifyPartForm.fxml"));
                Parent parent = loader.load();
                Stage stage = (Stage) MainExitButton.getScene().getWindow();
                stage.setTitle("Modify Part");
                stage.setScene(new Scene(parent, 600, 450));
                stage.show();
                ModifyPartController controller = loader.getController();
                Part selectPart = (Part) MainPartsTable.getSelectionModel().getSelectedItem();
                controller.getSelectedPart(selectPart);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Part Selected");
            alert.setContentText("Select a part from the list to modify");
            alert.showAndWait();
        }

    }

    /** This method takes you to the modify product form. */
    public void ModifyProductOnClick(ActionEvent actionEvent) throws IOException {
        if (MainProductsTable.getSelectionModel().getSelectedItem() != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/ModifyProductForm.fxml"));
                Parent parent = loader.load();
                Stage stage = (Stage) MainExitButton.getScene().getWindow();
                stage.setTitle("Modify Product");
                stage.setScene(new Scene(parent, 1200, 650));
                stage.show();
                ModifyProductController controller = loader.getController();
                Product selectProduct = (Product) MainProductsTable.getSelectionModel().getSelectedItem();
                controller.getSelectedProduct(selectProduct);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Product Selected");
            alert.setContentText("Select a product from the list to modify");
            alert.showAndWait();
        }
    }



    /** This method deletes a selected part. */
    public void DeletePartOnClick(ActionEvent actionEvent) {

        if (MainPartsTable.getSelectionModel().getSelectedItem() != null) {
            try {
                Alert confirmDelete = new Alert(Alert.AlertType.CONFIRMATION);
                confirmDelete.initModality(Modality.NONE);
                confirmDelete.setTitle("Delete?");
                confirmDelete.setHeaderText("Delete?");
                confirmDelete.setContentText("Are you sure you want to delete this Part?");
                Optional<ButtonType> userChoice = confirmDelete.showAndWait();

                if (userChoice.get() == ButtonType.OK) {
                    Part part = (Part) MainPartsTable.getSelectionModel().getSelectedItem();
                    Inventory.deletePart(part);
                    MainPartsTable.setItems(Inventory.getAllParts());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No part chosen to delete.");
            alert.showAndWait();
        }
    }

    /** This method takes you tot he add product form. */
    public void AddProductOnClick(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) MainExitButton.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("/View_Controller/AddProductForm.fxml"));
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    /** This method deletes a selected product. It won't let you delete unless the product doesn't have an associated part. */
    public void DeleteProductOnClick(ActionEvent actionEvent) {
        Product toDelete = (Product) MainProductsTable.getSelectionModel().getSelectedItem();

                    if (MainProductsTable.getSelectionModel().getSelectedItem() != null) {
                        if (!toDelete.getAssociatedParts().isEmpty()) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setContentText("Removed associated parts before deleting.");
                            alert.showAndWait();
                        } else {

                            Alert confirmDelete = new Alert(Alert.AlertType.CONFIRMATION);
                            confirmDelete.initModality(Modality.NONE);
                            confirmDelete.setTitle("Delete?");
                            confirmDelete.setHeaderText("Delete?");
                            confirmDelete.setContentText("Are you sure you want to delete this Product?");
                            Optional<ButtonType> userChoice = confirmDelete.showAndWait();

                            if (userChoice.get() == ButtonType.OK) {
                                Product product = (Product) MainProductsTable.getSelectionModel().getSelectedItem();
                                Inventory.deleteProduct(product);
                                MainProductsTable.setItems(Inventory.getAllProducts());
                            }
                        }
                        } else{
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("No product chosen to delete.");
                        alert.showAndWait();
                    }
    }

    /** This method exists the application. */
    public void ExitOnClick(ActionEvent actionEvent) {
        System.exit(0);
    }

    /** This method searches the parts table for the entered string. */
    public void PartSearchOnClick(ActionEvent actionEvent) {
        String q = MainPartSearch.getText();
        if(q.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error.");
            alert.setContentText("Enter the part name or ID to search");
            alert.showAndWait();
        }
            ObservableList<Part> parts = Inventory.lookupPart(q);
            if (parts.size() == 0) {
                try{
                    int partID = Integer.parseInt(q);
                    Part p = Inventory.lookupPart(partID);
                    if (p != null) {
                        parts.add(p);
                    }
                } catch (NumberFormatException e){
                    //ignore b/c we can search for strings too.
                }
            }
            MainPartsTable.setItems(parts);
            MainPartSearch.setText("");
            if(parts.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("No part found.");
                alert.showAndWait();
                MainPartsTable.setItems(Inventory.getAllParts());
            }
    }

    /** this method searches the products table for the entered string. */
    public void ProdSearchOnClick(ActionEvent actionEvent) {
        String q = ProductSearch.getText();
        if(q.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Enter the product name or ID to search.");
            alert.showAndWait();
        }
            ObservableList<Product> products = Inventory.lookupProduct(q);
            if (products.size() == 0) {
                try{
                    int prodID = Integer.parseInt(q);
                    Product p = Inventory.lookupProduct(prodID);
                    if (p != null) {
                        products.add(p);
                    }
                } catch (NumberFormatException e) {
                    //ignore because we can search by strings too.
                }
            }
        MainProductsTable.setItems(products);
        ProductSearch.setText("");
        if(products.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No product found.");
            alert.showAndWait();
            MainProductsTable.setItems(Inventory.getAllProducts());
        }


    }
}
