package projet;

import java.sql.*;
import java.util.Random;

public class DatabaseSetup {
    
    public static void main(String[] args) {
        // initialiser bd
        initDatabase();
    }
    
    // initialisation
    public static void initDatabase(){

        // creation table users
        String createUsers = """
                            create table if not exists users ( 
                            id integer primary key autoincrement ,
                            login text unique not null,
                            password text not null,
                            name text not null,
                            surname text not null,
                            tel text not null,
                            address text ,
                            email text unique not null,
                            cars text 
                            );
                            """;
                                    
        // creation table cars 
        String createCars = """
                            create table if not exists cars (
                            id integer primary key autoincrement ,
                            brand text not null,
                            model text not null,
                            color text not null,
                            price real not null,
                            availability boolean default 1
                            );
                            """;
                                    
        // connexion a la base
        try (Connection conn = DatabaseConnection.connect();
             Statement st = conn.createStatement()) {
            
                // commande pour creer table
                st.execute(createUsers);
                System.out.println("Users table created with success");
                st.execute(createCars);
                System.out.println("Cars table created with success");

                // init des donnees pour cars
                insertCars(st);

        } catch (Exception e) {
            System.err.println("error:"+ e.getMessage());
        } 
    }
    
    // classe pour l insertion des donnees de cars 
    private static void insertCars(Statement st) throws SQLException{
           
        String[] brands = {"Toyota", "Honda", "Tesla", "Ford", "BMW", "Audi", "Chevrolet", "Nissan"};
        String[] models = {"Corolla", "Civic", "Model 3", "Mustang", "X5", "A4", "Camaro", "Altima"};
        String[] colors = {"Red", "Blue", "Black", "White", "Gray", "Green", "Yellow"};
        Random r = new Random();

        StringBuilder ch = new StringBuilder("insert into cars (brand, model, color, price, availability) values");

        // remplissage random
        for (int i = 0; i < 50; i++) {
            
            String brand = brands[r.nextInt(brands.length)];
            String model =models[r.nextInt(models.length)];
            String color = colors[r.nextInt(colors.length)];
            double price = 15000 + r.nextInt(50000);
            int availability = 1;

            ch.append(String.format("('%s', '%s', '%s', %.2f, %d)", brand, model, color, price, availability));
            if (i<49) {
                ch.append(", ");
            }
            else{
                ch.append(";");
            } 
        }
        st.execute(ch.toString());
        System.out.println("Data initialized successfully");
    }
}
