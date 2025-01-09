package projet;

public class Car {
    
    // les donnees
    private String brand;
    private String model;
    private String color;
    private double price;
    private String availability;
    private String username;

    // constructeur
    public Car(String brand, String model, String color, double price, String availability, String username) {
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.price = price;
        this.availability = availability;
        this.username = username;
    }

    // getters and setters
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public String getAvailability() {
        return availability;
    }
    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}
