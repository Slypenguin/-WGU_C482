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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**allows the modify product form to be controlled.  */
public class ModifyProductController implements Initializable {

    /** controls the search button. */
    public Button searchPart;
    /**creates an array list of parts associated with a product. */
    private ObservableList<Part> assocParts = FXCollections.observableArrayList();
    /** makes an anchor pane for the form. */
    public AnchorPane ModifyInvPane;
    /** controls a button to add an associated part. */
    public Button ModifyAddButton;
    /** controls a button to removed an associated part. */
    public Button ModRemoveAssocButton;
    /** controls a button to cancel modification. */
    public Button ModCancelButton;
    /** controls a button to save a modified product. */
    public Button ModSaveButton;
    /** controls the ID text field. */
    public TextField ModifyIDField;
    /** controls the Name text field. */
    public TextField ModifyNameField;
    /** controls the price text field. */
    public TextField ModifyPriceField;
    /** controls the Inv text field. */
    public TextField ModifyInvField;
    /**controls the Max text field. */
    public TextField ModifyMaxField;
    /** controls the min text field. */
    public TextField ModifyMinField;
    /** controls the parts available to be associated table(the top table).  */
    public TableView ModifyAddTable;
    /**controls the id column of the ModifyAddTable. */
    public TableColumn AddModPartIDColumn;
    /** controls the name column of the ModifyAddTable. */
    public TableColumn AddModPartNameColumn;
    /** controls the inv column of the ModifyAddTable. */
    public TableColumn AddModInvLevelColumn;
    /** controls the price column of the ModifyAddTable. */
    public TableColumn AddModPriceCostColumn;
    /** controls the search field for the parts table. */
    public TextField ModProductSearchField;
    /** controls the associated parts table.  */
    public TableView ModRemovedAssocTable;
    /** controls the id column of the ModRemovedAssocTable. */
    public TableColumn RemoveModPartIDColumn;
    /** controls the name column of the ModRemovedAssocTable. */
    public TableColumn RemoveModPartNameColumn;
    /** controls the inv column of the ModRemovedAssocTable. */
    public TableColumn RemoveModInvLevelColumn;
    /** controls the price column of the ModRemovedAssocTable. */
    public TableColumn RemoveModPriceCostColumn;
    /** pulls the product. */
    private Product product;
    
    @Override
    /** Sets up the window. The initialize function fills the tables, and makes the id uneditable. */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ModifyAddTable.setItems(Inventory.getAllParts());
        AddModPartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        AddModPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        AddModInvLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AddModPriceCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));


        RemoveModPartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        RemoveModPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        RemoveModInvLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        RemoveModPriceCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        ModifyIDField.setEditable(false);
        ModRemovedAssocTable.setItems(assocParts);
    }

    /**@return SelectedProduct*/
    public void getSelectedProduct(Product selectedProduct){
        this.product = selectedProduct;

        ModifyIDField.setText(Integer.toString(product.getId()));
        ModifyNameField.setText(product.getName());
        ModifyInvField.setText(Integer.toString(product.getStock()));
        ModifyPriceField.setText(Double.toString(product.getPrice()));
        ModifyMaxField.setText(Integer.toString(product.getMax()));
        ModifyMinField.setText(Integer.toString(product.getMin()));

        assocParts.addAll(selectedProduct.getAssociatedParts());
    }

    /**Adds an associated part to a product on click. */
    public void AddModProductOnClick(ActionEvent actionEvent) {
        Part selectedPart = (Part) ModifyAddTable.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            assocParts.add(selectedPart);
            ModRemovedAssocTable.setItems(assocParts);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Select a part to add.");
            alert.showAndWait();
        }
    }

    /**Removes an associated part from a product on click. */
    public void ModRemoveOnClick(ActionEvent actionEvent) {
        Part selectedPart = (Part) ModRemovedAssocTable.getSelectionModel().getSelectedItem();
        if(selectedPart != null) {
            assocParts.remove(selectedPart);
            ModRemovedAssocTable.setItems(assocParts);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Select a part to remove.");
            alert.showAndWait();
        }
    }

    /**Cancels product modification on click. */
    public void ModCancelOnClick(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ModCancelButton.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("/View_Controller/MainForm.fxml"));
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    /**Saves a modified product on click. */
    public void ModSaveOnClick(ActionEvent actionEvent) throws IOException {
        try {
            String name = ModifyNameField.getText();
            int id = Integer.parseInt(ModifyIDField.getText());
            double price = Double.parseDouble(ModifyPriceField.getText());
            int stock = Integer.parseInt(ModifyInvField.getText());
            int min = Integer.parseInt(ModifyMinField.getText());
            int max = Integer.parseInt(ModifyMaxField.getText());
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
            } else{
                Product newProduct = new Product(id, name, price, stock, min, max);
                newProduct.getAssociatedParts().addAll(assocParts);
                int index = Inventory.getAllProducts().indexOf(product);
                Inventory.updateProduct(index, newProduct);

                Stage stage = (Stage) ModCancelButton.getScene().getWindow();
                Parent parent = FXMLLoader.load(getClass().getResource("/View_Controller/MainForm.fxml"));
                Scene scene = new Scene(parent);
                stage.setScene(scene);
                stage.show();
            }
        }  catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Field must have a value.");
            alert.showAndWait();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Price, inv, min, and max must be a number value.");
            alert.showAndWait();
        }
    }

    /** This method searches parts by ID or name. It will filter the parts when the search button is clicked.*/
    public void searchonclick(ActionEvent actionEvent) {
        String q = ModProductSearchField.getText();
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
        ModifyAddTable.setItems(parts);
        ModProductSearchField.setText("");
        if(parts.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No part found");
            alert.showAndWait();
            ModifyAddTable.setItems(Inventory.getAllParts());
        }
    }
}

