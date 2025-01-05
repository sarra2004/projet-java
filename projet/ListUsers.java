package projet;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class ListUsers extends JFrame implements ActionListener {
    
    // creation de JcomboBox qui contient la liste des login trouves dans la bd et des labels et un bouton
    JComboBox<String> logins; 
    JLabel n, s, t, ad, e;
    JLabel nameLabel, surnameLabel, telLabel, addressLabel, emailLabel;
    JButton r= new JButton("Return");

    // constructeur
    public ListUsers(){
        
        super("List Users");
        setSize(400, 450 );
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fermer uniquement cette fenêtre
        setLocationRelativeTo(null); // Centrer la fenêtre

        // init des composants
        logins = new JComboBox<String>();
        n= new JLabel("Name: ");
        s= new JLabel("Surname: ");
        t= new JLabel("Tel: ");
        ad= new JLabel("Address: ");
        e= new JLabel("Email: ");
        // c= new JLabel("Cars: ");

        nameLabel = new JLabel();
        surnameLabel = new JLabel();
        telLabel = new JLabel();
        addressLabel = new JLabel();
        emailLabel = new JLabel();
        // carsLabel = new JLabel();

        // charger logins a partir de la bd
        loadLogins();

        JPanel panel= new JPanel();
        panel.setLayout(new GridLayout(7,2,10,10));

        panel.add(new JLabel("Login: "));
        panel.add(logins);
        panel.add(n);
        panel.add(nameLabel);
        panel.add(s);
        panel.add(surnameLabel);
        panel.add(t);
        panel.add(telLabel);
        panel.add(ad);
        panel.add(addressLabel);
        panel.add(e);
        panel.add(emailLabel);
        // panel.add(c);
        // panel.add(carsLabel);
        panel.add(r);

        // ajout listeners
        logins.addActionListener(this);
        r.addActionListener(this);

        setContentPane(panel);
    }

    // charger logins a partir de la bd
    public void loadLogins(){
        try (Connection conn = DatabaseConnection.connect();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("select login from users")) 
        {
            while (rs.next()) {
                logins.addItem(rs.getString("login"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error when charging", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // lorsque les boutons sont cliques
    public void actionPerformed (ActionEvent x){
        if (x.getSource()==logins) {
            // recuperer le login selectionne
            String Selectedlogin = (String)logins.getSelectedItem();
            loadUserDetails(Selectedlogin);
        }
        else if (x.getSource()==r) {
            // fermer la fenetre
            this.dispose();
            // retour a la fenetre ContentPage
            new ContentPage().setVisible(true);
        }
    }

    // charger les details de l utilisateur a partie de la bd 
    public void loadUserDetails(String login){
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pst = conn.prepareStatement("select * from users where login=?"))
        {
            pst.setString(1, login);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                nameLabel.setText(rs.getString("name"));
                surnameLabel.setText(rs.getString("surname"));
                telLabel.setText(rs.getString("tel"));
                addressLabel.setText(rs.getString("address"));
                emailLabel.setText(rs.getString("email"));
                // carsLabel.setText(rs.getString("cars"));
            }  
        }catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error when charging", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // main
    public static void main(String[] args) {
        ListUsers lu = new ListUsers();
        lu.setVisible(true);
    }
}
