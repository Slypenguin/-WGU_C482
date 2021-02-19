package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This class handles creation and access to products.*/
public class Product {
    public ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @return the id*/
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set*/
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name*/
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set*/
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the price*/
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set*/
    public void setPrice(double price) {
        this.price = price;
    }

   /**
    * @return stock*/
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set*/
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return min*/
    public int getMin() {
        return min;
    }

    /**
     * @param min the min to set*/
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return max*/
    public int getMax() {
        return max;
    }

    /**
     * @param max the max to set*/
    public void setMax(int max) {
        this.max = max;
    }

    /**
     *This method adds associated parts. It assigns specific parts to a product. */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * This method deletes associated parts. It removes specific parts from a product.*/
    public boolean deleteAssociatedPart(Part part){
        return associatedParts.remove(part);
    }

    /**
     * @return the associated parts*/
    public ObservableList<Part> getAssociatedParts() {
        return associatedParts;
    }



}
