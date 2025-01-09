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
                            username text unique not null,
                            password text not null,
                            name text not null,
                            surname text not null,
                            tel text not null,
                            address text ,
                            email text unique not null
                            );
                            """;
                                    
        // creation table cars 
        String createCars = """
                            create table if not exists cars (
                            id integer primary key autoincrement ,
                            brand text not null,
                            model text not null,
                            color text not null,
                            price real not null check (price > 0),
                            availability text check(availability in ('available', 'lent')) default 'available' 
                            );
                            """;
        
        // creation table bookings
        String createBookings = """
                            create table if not exists bookings (
                            id integer primary key autoincrement ,
                            car_id integer not null,
                            user_id integer not null,
                            foreign key (car_id) references cars(id),
                            foreign key (user_id) references users(id)
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
                st.execute(createBookings);
                System.out.println("Bookings table created with success");

                // init des donnees pour cars
                insertCars(st);

        } catch (Exception e) {
            e.printStackTrace();
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
            double price = 15000 +  r.nextDouble() * 50000;
            String availability = "available";

            ch.append(String.format("('%s', '%s', '%s', '%.2f', '%s')", brand, model, color, price, availability));
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
