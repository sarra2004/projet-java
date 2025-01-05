package projet;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class CheckCars extends JFrame implements ActionListener {
    
    // creation de JComboBox qui contient la liste des marques trouves dans la bd et des labels et un bouton
    JComboBox<String> brands;
    JLabel m, c, p, a;
    JLabel modelLabel, colorLabel, priceLabel, availabilityLabel;
    JButton r= new JButton("Return");
    JButton b= new JButton("Book Car");

    // constructeur
    public CheckCars(){
        
        super("Check Cars");
        setSize(400, 450 );
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fermer uniquement cette fenêtre
        setLocationRelativeTo(null); // Centrer la fenêtre

        // init des composants
        brands = new JComboBox<String>();
        m= new JLabel("Model: ");
        c= new JLabel("Color: ");
        p= new JLabel("Price: ");
        a= new JLabel("Availability: ");

        modelLabel = new JLabel();
        colorLabel = new JLabel();
        priceLabel = new JLabel();
        availabilityLabel = new JLabel();

        // charger brands a partir de la bd
        loadBrands();

        JPanel panel= new JPanel();
        panel.setLayout(new GridLayout(7,2,10,10));

        panel.add(new JLabel("Brand: "));
        panel.add(brands);
        panel.add(m);
        panel.add(modelLabel);
        panel.add(c);
        panel.add(colorLabel);
        panel.add(p);
        panel.add(priceLabel);
        panel.add(a);
        panel.add(availabilityLabel);
        panel.add(r);
        panel.add(b);

        // ajout listeners
        brands.addActionListener(this);
        r.addActionListener(this);
        b.addActionListener(this);

        setContentPane(panel);
    }

    // charger brands a partir de la bd
    private void loadBrands(){
        try (Connection conn = DatabaseConnection.connect();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("select distinct brand from cars where availability = 1"))
        {  
            while (rs.next()) {
                brands.addItem(rs.getString("brand"));
            }

            if (brands.getItemCount() == 0) {
                JOptionPane.showMessageDialog(this, "No cars available", "Error", JOptionPane.ERROR_MESSAGE);
                dispose();
            }
        } catch (Exception ex) {
            ex.getStackTrace();
            JOptionPane.showMessageDialog(this, "Error when charging", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // lorsque les boutons sont cliques
    public void actionPerformed (ActionEvent e){
        if (e.getSource()==brands) {
            // recuperer la marque selectionnee
            String Selectedbrand = (String)brands.getSelectedItem();
            loadCarDetails(Selectedbrand);
            }
        else if (e.getSource()==r) {
            // fermer la fenetre
            this.dispose();
            // retour a la fenetre ContentUser
            new ContentUser().setVisible(true);
        }
        // else if (e.getSource()==b) {
        //     // recuperer la marque selectionnee
        //     String Selectedbrand = (String)brands.getSelectedItem();
        //     new BookCar(Selectedbrand).setVisible(true);
        // }
    }

    // charger les details de la voiture selectionnee
    private void loadCarDetails(String brand){
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pst = conn.prepareStatement("select * from cars where brand= ?"))
        {
            pst.setString(1, brand);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                modelLabel.setText(rs.getString("model"));
                colorLabel.setText(rs.getString("color"));
                priceLabel.setText(String.valueOf(rs.getDouble("price")));
                availabilityLabel.setText(rs.getBoolean("availability") ? "For rent" : "Not available");   
            }
        } catch (Exception ex) {
           ex.printStackTrace();
           JOptionPane.showMessageDialog(this, "Error when charging", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // main
    public static void main(String[] args) {
        CheckCars cc = new CheckCars();
        cc.setVisible(true);
    }
}
