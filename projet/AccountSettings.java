package projet;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import projet.DatabaseConnection;
import projet.ConetntUser;

public class AccountSettings extends JFrame implements ActionListener {
    
    // creation des labels et text fields et boutons 
    JTextField login, email, tel, address;
    JPasswordField pwd;
    
    JButton s = new JButton("Save");
    JButton r = new JButton("Return");

    private int userId;

    // constructeur
    public AccountSettings (int userId){

        super("Account Settings");
        this.userId=userId;
        setSize(400, 450 );
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fermer uniquement cette fenêtre
        setLocationRelativeTo(null); // Centrer la fenêtre

        // init composants
        login = new JTextField(20);
        pwd = new JPasswordField(20);
        email = new JTextField(50);
        tel = new JTextField(20);
        address = new JTextField(50);

        // charger donnees users
        loadUserData();


        JPanel p= new JPanel();
        p.setLayout(new GridLayout(2,1,10,10));

        p.add(new JLabel("Login: "));
        p.add(login);
        p.add(new JLabel("Password: "));
        p.add(pwd);
        p.add(new JLabel("Email: "));
        p.add(email);
        p.add(new JLabel("Telephone Number: "));
        p.add(tel);
        p.add(new JLabel("Address: "));
        p.add(address);
        p.add(s);
        p.add(r);

        // ajout des listeners
        s.addActionListener(this);
        r.addActionListener(this);

        setContentPane(p);
    }

    // charger les donnees a partir de la bd 
    private void loadUserData() {
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pst = conn.prepareStatement("select login, password, email, tel, address from users where id = ?")) {

            pst.setInt(1, userId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                login.setText(rs.getString("login"));
                pwd.setText(rs.getString("password"));
                email.setText(rs.getString("email"));
                tel.setText(rs.getString("tel"));
                address.setText(rs.getString("address"));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error when charging.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // enregistrer les modifs 
    private void saveUserData() {
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pst = conn.prepareStatement("update users set login = ?, password = ?, email = ?, tel=?, address=? where id = ?")) {

            pst.setString(1, login.getText());
            pst.setString(2, new String(pwd.getPassword()));
            pst.setString(3, email.getText());
            pst.setString(4,tel.getText());
            pst.setString(5, address.getText());
            pst.setInt(6, userId);

            int rows = pst.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Update successful !", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No update done.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error when updating.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // lorsque les boutons sont cliques
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == s) {
            // Enregistrer les modif
            saveUserData();
        } else if (e.getSource() == r) {
            // Fermer la fenêtre
            this.dispose();
            // retour a la fenetre ContentUser
            new ContentUser().setVisible(true);
        }
    }

    // main
    public static void main(String[] args) {
        AccountSettings as = new AccountSettings(1);
        as.setVisible(true);
    }
}
