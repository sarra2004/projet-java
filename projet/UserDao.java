package projet;

import java.sql.*;
import java.util.*;

public class UserDao {
    
    // charger tous les utilisateurs
    public List<User> loadAllUsers() {
        List<User> users = new ArrayList<User>();
        try(Connection conn = DatabaseConnection.connect();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from users")) 
        {
            while (rs.next()) {
                users.add(infoUser(rs));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return users;
    }
          
    // charger les utilisateurs qui ont loue
    public List<User> loadRenters() {
        List<User> users = new ArrayList<User>();
        String sql = """
                select distinct u.*
                from users u
                join bookings b on u.id = b.user_id
                """;
        try(Connection conn = DatabaseConnection.connect();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql))
        {
            while (rs.next()) {
                users.add(infoUser(rs));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return users;
    }

    // charger les logins
    public List<String> loadLogins() {
        List<String> logins = new ArrayList<String>();
        try(Connection conn = DatabaseConnection.connect();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select username from users")) 
        {
            while (rs.next()) {
                logins.add(rs.getString("username"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return logins;
    }

    // charger les details par login
    public User loadUserByLogin(String login) {
        try(Connection conn = DatabaseConnection.connect();
            PreparedStatement ps = conn.prepareStatement("select * from users where username = ?")) 
        {
            ps.setString(1, login);
            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return infoUser(rs);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    // methode pour les info
    private User infoUser(ResultSet rs) throws SQLException {
        return new User(
        rs.getString("username"),
        rs.getString("name"),
        rs.getString("surname"),
        rs.getString("phone"),
        rs.getString("address"),
        rs.getString("email")
        );
    }

    // methode pour ajouter un utilisateur
    public boolean addUser(String login, String pwd) {
        try(Connection conn = DatabaseConnection.connect();
            PreparedStatement ps = conn.prepareStatement("insert into users (username, password) values (?, ?)")) 
        {
            ps.setString(1, login);
            ps.setString(2, pwd);
            ps.executeUpdate();
            return true;
        } catch (Exception ex) {
            if (ex.getMessage().contains("UNIQUE constraint failed")) {
                return false;
            }
            ex.printStackTrace();
            return false;
        }
    }

    // verif login et pwd
    public User loadUser(String login, String pwd) {
        try(Connection conn = DatabaseConnection.connect();
            PreparedStatement ps = conn.prepareStatement("select * from users where username = ? and password = ?")) 
        {
            ps.setString(1, login);
            ps.setString(2, pwd);
            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return infoUser(rs);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    // update utilisateur
    public void updateUser(User user) {
        String sql = """
            update users
            set name = ?,
                surname = ?,
                phone = ?,
                address = ?,
                email = ?
            where username = ?
            """;
        try(Connection conn = DatabaseConnection.connect();
            PreparedStatement ps = conn.prepareStatement(sql)) 
        {
            ps.setString(1, user.getName());
            ps.setString(2, user.getSurname());
            ps.setString(3, user.getTel());
            ps.setString(4, user.getAddress());
            ps.setString(5, user.getEmail());
            ps.setString(6, user.getUsername());
            ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

