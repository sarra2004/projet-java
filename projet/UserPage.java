package projet;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class UserPage extends JFrame implements ActionListener {
    
    // creation des label
    JLabel login = new JLabel("Login");
    JLabel pwd = new JLabel("Password");

    // creationd du text field et pwd field
    JTextField t1 = new JTextField(20);
    JPasswordField t2 = new JPasswordField(20);

    // creation des bouttons
    JButton b1 = new JButton("OK");
    JButton b2 = new JButton("Sign up");

    // constructeur 
    public UserPage(){
        
        super("User");
        setSize(400, 450 );
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fermer uniquement cette fenêtre
        setLocationRelativeTo(null); // Centrer la fenêtre
        
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(3,2,10,10));

        p.add(login);
        p.add(t1);
        p.add(pwd);
        p.add(t2);
        p.add(b1);
        p.add(b2);

        // ajout listeners 
        b1.addActionListener(this);
        b2.addActionListener(this);

        setContentPane(p);
    }

    // lorsque les boutons sont cliques 
    public void actionPerformed (ActionEvent x){
        
        if (x.getSource() == b1) {

            // recup  login et pwd
            String ch1 = t1.getText();
            String ch2 = new String(t2.getPassword());
            
            // Conditions verif pwd
            if (ch1.isEmpty() || ch2.isEmpty()) {
                JOptionPane.showMessageDialog(this, "You have to fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // connection avec bd
            try (Connection conn = DatabaseConnection.connect();
                 PreparedStatement ps= conn.prepareStatement("select * from users where login =? and password = ?")) 
            {
                ps.setString(1, ch1);
                ps.setString(2, ch2);
                ResultSet rs= ps.executeQuery();

                if (rs.next()) {
                    JOptionPane.showMessageDialog(this, "Connection successful !", "Success", JOptionPane.INFORMATION_MESSAGE);
                    // ouvrir nouvelle fenetre
                    new ContentUser().setVisible(true);
                    //fermer fenetre actuelle
                    dispose(); 
                }
                else{
                    JOptionPane.showMessageDialog(this, "Incorrect login or password please re-register.", "Error", JOptionPane.ERROR_MESSAGE);
                }   
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }
        else if (x.getSource()==b2) {
            
            // recup  login et pwd
            String ch1 = t1.getText();
            String ch2 = new String(t2.getPassword());

            // Conditions verif pwd
            if (ch1.isEmpty() || ch2.isEmpty()) {
                JOptionPane.showMessageDialog(this, "You have to fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            else if (ch2.length()<8) {
                JOptionPane.showMessageDialog(this, "The password must contain at least 8 characters", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            else if (!ch2.matches(".*[A-Z].*")) {
                JOptionPane.showMessageDialog(this, "The password must have at least a capital letter", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            else if (!ch2.matches(".*[0-9].*")) {
                JOptionPane.showMessageDialog(this, "The password must have at least a number", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            else if (!ch2.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
                JOptionPane.showMessageDialog(this, "The password must have at least a special character", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (Connection conn = DatabaseConnection.connect();
                  PreparedStatement ps = conn.prepareStatement("insert into users (login,password) values (?,?)")) 
            {
                ps.setString(1, ch1);
                ps.setString(2, ch2);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Successful registration ! You can now log in.", "Success", JOptionPane.INFORMATION_MESSAGE);

                // ouvrir nouvelle fenetre sign up
                new SignUp(ch1).setVisible(true);

            } catch (Exception e) {
                if (e.getMessage().contains("UNIQUE constraint failed")) {
                    JOptionPane.showMessageDialog(this, "This login already exists please choose another one", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    e.printStackTrace();
                }
            }
        }
    }
}
