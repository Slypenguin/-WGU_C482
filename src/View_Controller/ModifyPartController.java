package View_Controller;

import Model.InhousePart;
import Model.Inventory;
import Model.OutsourcedPart;
import Model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**allows the modify part form to be controlled*/
public class ModifyPartController implements Initializable {
    /** controls the save button. */
    public Button ModifyPartSaveButton;
    /** controls the cancel button. */
    public Button ModifyPartCancelButton;
    /** controls the inhouse radiobutton. */
    public RadioButton ModInhouse;
    /** controls the outsourced radiobutton. */
    public RadioButton ModOutsourced;
    /** controls the id text field. */
    public TextField ModPartID;
    /** controls the name text field. */
    public TextField ModPartName;
    /** controls the inv text field. */
    public TextField ModPartInv;
    /** controls the price text field. */
    public TextField ModPartPrice;
    /** controls the max text field. */
    public TextField ModPartMax;
    /** controls the machineID/Company Name text field. */
    public TextField ModPartMachID;
    /** controls the min text field. */
    public TextField ModPartMin;
    /** controls the label that changes based on radio button chosen. */
    public Label RadioLabel;
    /** controls the toggle group. */
    private ToggleGroup InOutTG;
    /** pulls the part. */
    private Part part;



    @Override
    /** Sets up the window. The initialize function sets up the toggle group and makes the id uneditable. */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        InOutTG = new ToggleGroup();
        this.ModInhouse.setToggleGroup(InOutTG);
        this.ModOutsourced.setToggleGroup(InOutTG);
        ModPartID.setEditable(false);
    }

    /**@return selectedPart*/
    public void getSelectedPart(Part selectedPart) {
        this.part = selectedPart;

        if (selectedPart instanceof InhousePart) {
            RadioLabel.setText("Machine ID");
            InhousePart inhousePart = (InhousePart) selectedPart;
            ModPartID.setText(Integer.toString(inhousePart.getId()));
            ModPartName.setText(inhousePart.getName());
            ModPartInv.setText(Integer.toString(inhousePart.getStock()));
            ModPartPrice.setText(Double.toString(inhousePart.getPrice()));
            ModPartMax.setText(Integer.toString(inhousePart.getMax()));
            ModPartMin.setText(Integer.toString(inhousePart.getMin()));
            ModPartMachID.setText(Integer.toString(inhousePart.getMachineId()));
            ModInhouse.setSelected(true);
        } else if (selectedPart instanceof OutsourcedPart) {
            RadioLabel.setText("Company Name");
            OutsourcedPart outsourcedPart = (OutsourcedPart) selectedPart;
            ModPartID.setText(Integer.toString(outsourcedPart.getId()));
            ModPartName.setText(outsourcedPart.getName());
            ModPartInv.setText(Integer.toString(outsourcedPart.getStock()));
            ModPartPrice.setText(Double.toString(outsourcedPart.getPrice()));
            ModPartMax.setText(Integer.toString(outsourcedPart.getMax()));
            ModPartMin.setText(Integer.toString(outsourcedPart.getMin()));
            ModPartMachID.setText(outsourcedPart.getCompanyName());
            ModOutsourced.setSelected(true);
            ModInhouse.setSelected(false);
        }
    }

    /** Saves the new part. This will add a new part to the all parts list.*/
    public void ModifySaveOnClick(ActionEvent actionEvent) throws IOException {
        try{
            int index = Inventory.getAllParts().indexOf(part);
            String name = ModPartName.getText();
            int id = Integer.parseInt(ModPartID.getText());
            double price = Double.parseDouble(ModPartPrice.getText());
            int stock = Integer.parseInt(ModPartInv.getText());
            int min = Integer.parseInt(ModPartMin.getText());
            int max = Integer.parseInt(ModPartMax.getText());

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
            } else {
                if (ModInhouse.isSelected()) {

                    int machineId = Integer.parseInt(ModPartMachID.getText());

                    Part newPart = new InhousePart(id, name, price, stock, min, max, machineId);
                    Inventory.updatePart(index, newPart);


                    Stage stage = (Stage) ModifyPartCancelButton.getScene().getWindow();
                    Parent parent = FXMLLoader.load(getClass().getResource("/View_Controller/MainForm.fxml"));
                    Scene scene = new Scene(parent);
                    stage.setScene(scene);
                    stage.show();

                } else {
                    String companyName = ModPartMachID.getText();

                    Part newPart = new OutsourcedPart(id, ModPartName.getText(), price, stock, min, max, companyName);
                    Inventory.updatePart(index, newPart);


                    Stage stage = (Stage) ModifyPartCancelButton.getScene().getWindow();
                    Parent parent = FXMLLoader.load(getClass().getResource("/View_Controller/MainForm.fxml"));
                    Scene scene = new Scene(parent);
                    stage.setScene(scene);
                    stage.show();
                }
            }
        }  catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Field(s) must have a value.");
            alert.showAndWait();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Price, Inv, min, and max must be a number value.");
            alert.showAndWait();
        }
    }

    /** This method cancels adding a part. It will take you back to the main form.*/
    public void ModifyCancelOnClick(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ModifyPartCancelButton.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("/View_Controller/MainForm.fxml"));
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    /** This method sets the dynamic label to Machine Id when the inhouse radiobutton is chosen. */
    public void InhouseOnClick(ActionEvent actionEvent) {
        RadioLabel.setText("Machine ID");
    }

    /** This method sets the dynamic label to Company Name when the outsourced radiobutton is chosen. */
    public void OutsourcedOnClick(ActionEvent actionEvent) {
        RadioLabel.setText("Company Name");
    }
}
