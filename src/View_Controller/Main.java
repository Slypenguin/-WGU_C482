package View_Controller;

import Model.InhousePart;
import Model.Inventory;
import Model.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/** This class creates an app that lets you create, modify, and delete parts and products.*/
public class Main extends Application {
    @Override
    /**
     * This method starts the application window. */
    public void start(Stage stage) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("/View_Controller/MainForm.fxml"));
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method starts the app. */
    public static void main (String []args){
        InhousePart p1 = new InhousePart(AddPartController.PartId++, "Capacitor", 1.23, 43, 2, 50, 23);
        Inventory.addPart(p1);
        InhousePart p2 = new InhousePart(AddPartController.PartId++, "Silicon", 5.23, 4, 2, 50, 3);
        Inventory.addPart(p2);
        InhousePart p3 = new InhousePart(AddPartController.PartId++,"Battery", 20.00, 20, 1, 20, 37);
        Inventory.addPart(p3);


        Product prod1 = new Product(AddProductController.ProductId++, "Graphics Card", 12.50, 10, 1, 15);
        Inventory.addProduct(prod1);
        Product prod2 = new Product(AddProductController.ProductId++, "CPU", 100.00, 10,1,10);
        Inventory.addProduct(prod2);
        Product prod3 = new Product(AddProductController.ProductId++,"Power Supply", 67.50, 13, 1, 13);
        Inventory.addProduct(prod3);

        launch(args);

    }
}
