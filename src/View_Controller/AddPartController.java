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

/** This class implements the addpart controller. */
public class AddPartController implements Initializable {
    /** controls the inhouse radio button. */
    public RadioButton AddPartInHouseRadio;
    /** controls the outsourced radio button. */
    public RadioButton AddPartOutsourcedRadio;
    /** controls the save button. */
    public Button AddPartSaveButton;
    /** controls the cancel button. */
    public Button AddPartCancelButton;
    /** controls the ID text field. */
    public TextField AddPartIDField;
    /** controls the name text field. */
    public TextField AddPartNameField;
    /** controls the inventory text field. */
    public TextField AddPartInvField;
    /** controls the price text field. */
    public TextField AddPartPriceCostField;
    /** controls the max text field. */
    public TextField AddPartMaxField;
    /** controls the min text field. */
    public TextField AddPartMinField;
    /** controls the machine id/ company name text field. */
    public TextField AddPartMachineIDField;
    /** controls a label that changes; dependant on which radio button is selected. */
    public Label radioButtonLabel;
    /** controls the toggle group for the radio buttons. */
    private ToggleGroup InOutTG;
    /** creates the ability to auto generate part IDs. */
    public static int PartId = 0;

    @Override
    /** initializes the toggle group, the part id, and sets the id to uneditable. */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        InOutTG = new ToggleGroup();
        this.AddPartInHouseRadio.setToggleGroup(InOutTG);
        this.AddPartOutsourcedRadio.setToggleGroup(InOutTG);
        AddPartIDField.setText(Integer.toString(PartId));
        AddPartIDField.setEditable(false);
        AddPartInHouseRadio.setSelected(true);
        radioButtonLabel.setText("Machine ID");
    }

    /** sets the radioButtonLabel to Machine ID when the inhouse radiobutton is clicked. */
    public void InHouseOnClick(ActionEvent actionEvent) {
        radioButtonLabel.setText("Machine ID");

    }

    /** sets the radioButtonLabel to Company Name when the outsourced radiobutton is clicked.*/
    public void OutsourcedOnClick(ActionEvent actionEvent) {
        radioButtonLabel.setText("Company Name");
    }

    /** This method saves the new part. It puts it in the allParts list. */
    public void SaveOnClick(ActionEvent actionEvent) throws IOException {

        try {
            String name = AddPartNameField.getText();
            double price = Double.parseDouble(AddPartPriceCostField.getText());
            int stock = Integer.parseInt(AddPartInvField.getText());
            int min = Integer.parseInt(AddPartMinField.getText());
            int max = Integer.parseInt(AddPartMaxField.getText());

            if (name.isEmpty()){
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
                if (AddPartInHouseRadio.isSelected()) {

                    int machineId = Integer.parseInt(AddPartMachineIDField.getText());
                    Part newPart = new InhousePart(PartId, AddPartNameField.getText(), price, stock, min, max, machineId);
                    Inventory.addPart(newPart);
                    PartId++;

                    Stage stage = (Stage) AddPartCancelButton.getScene().getWindow();
                    Parent parent = FXMLLoader.load(getClass().getResource("/View_Controller/MainForm.fxml"));
                    Scene scene = new Scene(parent);
                    stage.setScene(scene);
                    stage.show();
                } else {
                    String companyName = AddPartMachineIDField.getText();

                    Part newPart = new OutsourcedPart(PartId, AddPartNameField.getText(), price, stock, min, max, companyName);

                    Inventory.addPart(newPart);
                    PartId++;


                    Stage stage = (Stage) AddPartCancelButton.getScene().getWindow();
                    Parent parent = FXMLLoader.load(getClass().getResource("/View_Controller/MainForm.fxml"));
                    Scene scene = new Scene(parent);
                    stage.setScene(scene);
                    stage.show();
                }
}

        }  catch (NullPointerException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Field must have a value.");
            alert.showAndWait();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Price, Inv, min, and max must be a number value.");
            alert.showAndWait();
        }
        }

    /** This method cancels out of the add part form. It takes you back to the main form. */
        public void CancelOnClick (ActionEvent actionEvent) throws IOException {
            Stage stage = (Stage) AddPartCancelButton.getScene().getWindow();
            Parent parent = FXMLLoader.load(getClass().getResource("/View_Controller/MainForm.fxml"));
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.show();
        }
    }

