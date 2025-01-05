package projet;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class AddCars extends JFrame implements ActionListener{

    // champs pour les informations de la voiture
    JTextField brand, model, color, price;
    JCheckBox availability;
    JButton a= new JButton("Add");
    JButton r= new JButton("Return");

    // constructeur
    public AddCars(){
        
        super("Add Cars");
        setSize(400, 450 );
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fermer uniquement cette fenêtre
        setLocationRelativeTo(null); // Centrer la fenêtre

        // init des composants
        brand= new JTextField();
        model= new JTextField();
        color= new JTextField();
        price= new JTextField();
        availability= new JCheckBox("Available");

        JPanel panel= new JPanel();
        panel.setLayout(new GridLayout(6,2,10,10));

        panel.add(new JLabel("Brand: "));
        panel.add(brand);
        panel.add(new JLabel("Model: "));
        panel.add(model);
        panel.add(new JLabel("Color: "));
        panel.add(color);
        panel.add(new JLabel("Price: "));
        panel.add(price);
        panel.add(availability);
        panel.add(a);
        panel.add(r);

        // ajout listeners
        a.addActionListener(this);
        r.addActionListener(this);

        setContentPane(panel);   
    }

    // lorsque les boutons sont cliques
    public void actionPerformed (ActionEvent e){
        
        if (e.getSource()==a) {

            // ajouter details de voiture
            String b= brand.getText();
            String m= model.getText();
            String c= color.getText();
            double p= Double.parseDouble(price.getText());
            boolean av= availability.isSelected();

            // validation des champs
            if (b.isEmpty()|| m.isEmpty()|| c.isEmpty()|| price.getText().isEmpty()) {
                 JOptionPane.showMessageDialog(this, "Please fill all fields");
                 return;
            }
            try {
                addCar(b, m, c, p, av);
                JOptionPane.showMessageDialog(this, "Car added with success");
                clearFields();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error when adding", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        else if (e.getSource()==r) {
            // fermer fenetre
            this.dispose();
            // retour a la fenetre ContentPage
            new ContentPage().setVisible(true);
        }
    }
    
    // ajouter voiture a la bd
    private void addCar(String b, String m, String c, double p, boolean av) throws SQLException{
        
        String sql = "insert into cars (brand, model, color, price, availability) values (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, b);
            ps.setString(2, m);
            ps.setString(3, c);
            ps.setDouble(4, p);
            ps.setBoolean(5, av);
            ps.executeUpdate();
        }
    }

    // vider les champs
    private void clearFields(){
        brand.setText("");
        model.setText("");
        color.setText("");
        price.setText("");
        availability.setSelected(false);
    }

    // main
    public static void main(String[] args) {
        AddCars ac = new AddCars();
        ac.setVisible(true);
    }
}
