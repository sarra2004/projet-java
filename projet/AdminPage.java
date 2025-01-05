package projet;

import java.awt.*;
// import javax.security.auth.login.LoginContext;
import javax.swing.*;
import java.awt.event.*;

public class AdminPage extends JFrame implements ActionListener{
    
    // creation des label
    JLabel login = new JLabel("Login");
    JLabel pwd = new JLabel("Password");

    // creation du text field et pwd field
    JTextField t1 = new JTextField(20);
    JPasswordField t2 = new JPasswordField(20);

    // creation des bouttons
    JButton b1 = new JButton("OK");
    JButton b2 = new JButton("Cancel");

    // constructeur
    public AdminPage(){
        
        super("Admin");
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
            }
            else if (ch2.length()<8) {
                JOptionPane.showMessageDialog(this, "The password must contain at least 8 characters", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else if (!ch2.matches(".*[A-Z].*")) {
                JOptionPane.showMessageDialog(this, "The password must have at least a capital letter", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else if (!ch2.matches(".*[0-9].*")) {
                JOptionPane.showMessageDialog(this, "The password must have at least a number", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else if (!ch2.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
                JOptionPane.showMessageDialog(this, "The password must have at least a special character", "Error", JOptionPane.ERROR_MESSAGE);
            }
            // toutes conditions verifs
            else{
                JOptionPane.showMessageDialog(this, "Connection successful !", "Success", JOptionPane.INFORMATION_MESSAGE);
                // ouvrir nouvelle fenetre
                new ContentPage().setVisible(true);
                //fermer fenetre actuelle
                dispose(); 
            }

        }
        else if (x.getSource()==b2) {
            // retour a la fenetre admin/user
            new ManagementPage().setVisible(true);
            // fermer fenetre actuelle
            dispose(); 
        }
    }

    // main
    public static void main(String[] args) {
        AdminPage a = new AdminPage();
        a.setVisible(true);
    }
}
