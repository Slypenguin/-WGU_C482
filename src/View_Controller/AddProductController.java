package View_Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
/**This implements the controller to be able to change the Javafx form.*/
public class AddProductController implements Initializable {

    /**creates an array list of associated parts with a product. */
    private ObservableList<Part> assocParts = FXCollections.observableArrayList();
    /** controls a button to add associated parts. */
    public Button AddAssocPartButton;
    /** controls a button to cancel out of the add product form. */
    public Button AddProductCancelButton;
    /** controls a button to removed an associated part. */
    public Button RemovedAssocPartButton;
    /** controls a tableview displaying parts you can associate to a product. */
    public TableView AddPartTable;
    /** controls the ID column*/
    public TableColumn AddPartIDColumn;
    /** controls the name column*/
    public TableColumn AddPartNameColumn;
    /** controls the inventory column. */
    public TableColumn AddPartInvColumn;
    /** controls the price column. */
    public TableColumn AddPartPriceColumn;
    /** controls a tableview displaying parts associated with a product. */
    public TableView ProductAssocPartTable;
    /** controls the ID column. */
    public TableColumn RemoveProductPartIDColumn;
    /** controls the name column. */
    public TableColumn RemoveProductPartNameColumn;
    /** controls the inventory column. */
    public TableColumn RemoveProductInvLevelColumn;
    /** controls the price column. */
    public TableColumn RemoveProductPriceCostColumn;
    /** controls the ID text field. */
    public TextField ProductIDField;
    /** controls the name text field. */
    public TextField ProductNameField;
    /** controls the inventory text field. */
    public TextField ProductInvField;
    /** controls the price text field. */
    public TextField ProductPriceField;
    /** controls the maximum text field. */
    public TextField ProductMaxField;
    /** controls the minimum text field. */
    public TextField ProductMinField;
    /** controls the search button. */
    public Button searchPartButton;
    /** controls the search text field. */
    public TextField searchPartTF;
    /** makes a product id that will automatically be unique for all products. */
    public static int ProductId = 0;

    @Override
    /** initializes the tables, the product id, and makes the id uneditable. */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AddPartTable.setItems(Inventory.getAllParts());
        AddPartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        AddPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        AddPartInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AddPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));


        RemoveProductPartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        RemoveProductPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        RemoveProductInvLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        RemoveProductPriceCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        ProductIDField.setText(Integer.toString(ProductId));
        ProductIDField.setEditable(false);
    }

    /** This method controls the cancel button. It exits to the main screen when clicked. */
    public void CancelOnClick(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) AddProductCancelButton.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("/View_Controller/MainForm.fxml"));
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    /** This method controls the save button. it will save a new product to the allProducts list. */
    public void SaveProdOnClick(ActionEvent actionEvent) throws IOException {
        try{
            String name = ProductNameField.getText();
            double price = Double.parseDouble(ProductPriceField.getText());
            int stock = Integer.parseInt(ProductInvField.getText());
            int min = Integer.parseInt(ProductMinField.getText());
            int max = Integer.parseInt(ProductMaxField.getText());
            if (name.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Name field cannot be empty");
                alert.showAndWait();
            } else if (max < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Min cannot be higher than Max.");
                alert.showAndWait();
            } else if (stock < min || stock > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Inv must be less than Max and greater than Min.");
                alert.showAndWait();
            }else{

                Product newProduct = new Product(ProductId, name, price, stock, min, max);
                newProduct.getAssociatedParts().addAll(assocParts);
                Inventory.addProduct(newProduct);

                Stage stage = (Stage) AddProductCancelButton.getScene().getWindow();
                Parent parent = FXMLLoader.load(getClass().getResource("/View_Controller/MainForm.fxml"));
                Scene scene = new Scene(parent);
                stage.setScene(scene);
                stage.show();
            }
        } catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Field must have a value.");
            alert.showAndWait();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Price, inv, min and max must be a number value.");
            alert.showAndWait();
        }
    }

    /** This method removes associated parts. it will unassign a part that was previously assigned to a product. */
    public void RemoveAssocPartOnClick(ActionEvent actionEvent) {
        Part selectedPart = (Part) ProductAssocPartTable.getSelectionModel().getSelectedItem();
        if(selectedPart != null) {
            assocParts.remove(selectedPart);
            ProductAssocPartTable.setItems(assocParts);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Select a part to remove.");
            alert.showAndWait();
        }
    }

    /** This method is used when the search button is clicked. It will search the string entered for a matching part. */
    public void SearchOnAction(ActionEvent actionEvent) {
        String q = searchPartTF.getText();
        if(q.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Enter the part name or ID to search.");
            alert.showAndWait();
        }
        ObservableList<Part> parts = Inventory.lookupPart(q);
        if (parts.size() == 0){
            try{
                int partID = Integer.parseInt(q);
                Part p = Inventory.lookupPart(partID);
                if ( p != null) {
                    parts.add(p);
                }
            } catch (NumberFormatException e) {
                //ignore b/c search is for string too.
            }
        }
        AddPartTable.setItems(parts);
        searchPartTF.setText("");
        if(parts.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No part found");
            alert.showAndWait();
            AddPartTable.setItems(Inventory.getAllParts());
        }
    }

    /** this method is used to add an associated part. It will add the part to the list of parts associated to the product. */
    public void AddAssocPartOnClick(ActionEvent actionEvent) {
        Part selectedPart = (Part) AddPartTable.getSelectionModel().getSelectedItem();
        if(selectedPart != null) {
            assocParts.add(selectedPart);
            ProductAssocPartTable.setItems(assocParts);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Select a part to add.");
            alert.showAndWait();
        }
    }
}
