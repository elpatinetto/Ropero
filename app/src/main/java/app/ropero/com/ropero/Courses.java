package app.ropero.com.ropero;

/**
 * Created by noellodou on 03/08/2017.
 */

public class Courses {
    private String name;
    private double price;
    private String category;

    public Courses() {

    }

    public Courses( String nom, double prix){
        this.name = nom;
        this.price = prix;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
