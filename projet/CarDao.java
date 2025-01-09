package projet;

import java.sql.*;
import java.util.*;

public class CarDao {
    
    // charger les brands a partir de la bd 
    public List<String> loadBrands(){
        return loadDistinctValues("brand");
    }

    // charger les models a partir de la bd
    public List<String> loadModels(){
        return loadDistinctValues("model");
    }

    // charger les colors a partir de la bd
    public List<String> loadColors(){
        return loadDistinctValues("color");
    }

    // charger les prices a partir de la bd
    public List<String> loadPrices(){
        return loadDistinctValues("price");
    }

    // charger les availabilities a partir de la bd
    public List<String> loadAvailabilities(){
        return loadDistinctValues("availability");
    }

    // charger les valeurs a partir de filtre choisi
    private List<String> loadDistinctValues (String column){
        
        List<String> values = new ArrayList<>();

        try (Connection conn = DatabaseConnection.connect();
           
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select distinct " +column+ " from cars")) {

            while (rs.next()) {
                values.add(rs.getString(column));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return values;
    }
    
    // charger les details d'une voiture a partir de la bd
    public List<Car> loadCarsByFilter(String filter, String value){
        
        List<Car> cars = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pst = conn.prepareStatement("SELECT * FROM cars WHERE " + filter.toLowerCase() + " = ?")) {
            
            pst.setString(1, value);
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                Car car = new Car(
                    rs.getString("brand"),
                    rs.getString("model"),
                    rs.getString("color"),
                    rs.getDouble("price"),
                    rs.getString("availability"),
                    rs.getString("username")
                );
                cars.add(car);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return cars;
    }

    // charger toutes les voitures
    public List<Car> loadAllCars(){
        
        List<Car> cars = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.connect();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM cars")) {
            
            while (rs.next()) {
                Car car = new Car(
                    rs.getString("brand"),
                    rs.getString("model"),
                    rs.getString("color"),
                    rs.getDouble("price"),
                    rs.getString("availability"),
                    rs.getString("username")
                );
                cars.add(car);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return cars;
    }
    
    // voir les utilisateurs qui ont loue une voiture
    public String getUser(String brand){
        
        String username =null;
        String sql = """
                select u.username
                from booking b
                join users u on b.user_id=u.id
                join cars c on b.car_id=c.id
                where c.brand = ? and c.availability = 'lent'
                """;
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            pst.setString(1, brand);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                username = rs.getString("username");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return username;
    }

    // ajouter voiture a la bd
    public void addCar(String b, String m, String c, double p, String av) throws SQLException{
        
        String sql = "insert into cars (brand, model, color, price, availability) values (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, b);
            ps.setString(2, m);
            ps.setString(3, c);
            ps.setDouble(4, p);
            ps.setString(5, av);
            ps.executeUpdate();
        }
    }

    // charger les voitures disponibles seulement
    public List<Car> getAvailableCars(){
        
        List<Car> Avcars = new ArrayList<>();
        try (Connection conn = DatabaseConnection.connect();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM cars WHERE availability = 'available'")) {
            
            while (rs.next()) {
                Car car = new Car(
                    rs.getString("brand"),
                    rs.getString("model"),
                    rs.getString("color"),
                    rs.getDouble("price"),
                    rs.getString("availability"),
                    rs.getString("username")
                );
                Avcars.add(car);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Avcars;
    }

    // louer une voiture
    public boolean bookCar(Car car, String username){
        
        boolean success = false;
        try (Connection conn = DatabaseConnection.connect();
            PreparedStatement pst = conn.prepareStatement("update cars set availability ='lent' where brand = ? and model = ? and color = ? and price =?")) 
        {
            pst.setString(1, car.getBrand());
            pst.setString(2, car.getModel());
            pst.setString(3, car.getColor());
            pst.setDouble(4, car.getPrice());
            int rowsUpdate = pst.executeUpdate();
            if (rowsUpdate > 0) {
                String insertBookings = """
                        insert into bookings (car_id, user_id)
                        select c.id,u.id 
                        from cars c
                        join users u on u.username = ?
                        where c.brand = ?
                        and c.model = ?
                        and c.color= ?
                        and c.price=?
                        """;
                try (PreparedStatement pstB = conn.prepareStatement(insertBookings)) 
                {
                    pstB.setString(1, username);
                    pstB.setString(2, car.getBrand());
                    pstB.setString(3, car.getModel());
                    pstB.setString(4, car.getColor());
                    pstB.setDouble(5, car.getPrice());
                    int rowsInserted = pstB.executeUpdate();
                    if (rowsInserted > 0) {
                        success = true;
                    }
                    
                } 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }
}
