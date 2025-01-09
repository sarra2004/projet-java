package projet;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class AddCars extends JFrame implements ActionListener{

    // champs pour les informations de la voiture
    JTextField brand, model, color, price, av;
    JLabel availability;
    JButton a= new JButton("Add");
    JButton r= new JButton("Return");

    // constructeur
    public AddCars(){
        
        super("Add Cars");
        setSize(400, 300 );
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fermer uniquement cette fenêtre
        setLocationRelativeTo(null); // Centrer la fenêtre

        // init des composants
        brand= createText();
        model= createText();
        color= createText();
        price= createText();
        av=createText();
        av.setText("Available");
        av.setEditable(false);

        // main panel
        JPanel panel= new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets= new Insets(10, 10, 10, 10);
        gbc.fill= GridBagConstraints.HORIZONTAL;
        
        // ajout composants
        addComposant(panel, "Brand:", brand, gbc, 0);
        addComposant(panel, "Model:", model, gbc, 1);
        addComposant(panel, "Color:", color, gbc, 2);
        addComposant(panel, "Price:", price, gbc, 3);
        addComposant(panel, "Availability:", av, gbc, 4);

        // button panel
        JPanel button = new JPanel();
        button.add(a);
        button.add(r);
        a.setFont(new Font("Arial", Font.BOLD, 14));
        r.setFont(new Font("Arial", Font.BOLD, 14));
        a.setCursor(new Cursor(Cursor.HAND_CURSOR));
        r.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridy=5;
        gbc.gridx=0;
        gbc.gridwidth=2;
        panel.add(button, gbc);
        
        // ajout listeners
        a.addActionListener(this);
        r.addActionListener(this);

        setContentPane(panel);  
        pack(); 
    }

    // styling textFields
    private JTextField createText(){
        JTextField text = new JTextField(20);
        text.setFont(new Font("Arial", Font.PLAIN, 14));
        return text;
    }

    // styling label
    private void addComposant(JPanel p, String label, JTextField text, GridBagConstraints gbc, int y){
        JLabel l=new JLabel(label);
        l.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx=0;
        gbc.gridy=y;
        p.add(l, gbc);
        gbc.gridx=1;
        p.add(text, gbc);
    }

    // lorsque les boutons sont cliques
    public void actionPerformed (ActionEvent e){
        
        if (e.getSource()==a) {

            // ajouter details de voiture
            String b= brand.getText();
            String m= model.getText();
            String c= color.getText();
            double p;
            try {
                p= Double.parseDouble(price.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid price");
                return;
            }
            String av= "available";

            // validation des champs
            if (b.isEmpty()|| m.isEmpty()|| c.isEmpty()|| price.getText().isEmpty()) {
                 JOptionPane.showMessageDialog(this, "Please fill all fields");
                 return;
            }
            try {
                CarDao carDao = new CarDao();
                carDao.addCar(b, m, c, p, av);
                availability.setVisible(true);
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

    // vider les champs
    private void clearFields(){
        brand.setText("");
        model.setText("");
        color.setText("");
        price.setText("");
    }

    // main
    public static void main(String[] args) {
        AddCars ac = new AddCars();
        ac.setVisible(true);
    }
}
