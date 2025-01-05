package projet;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class SignUp extends JFrame implements ActionListener{
    
    // creation labels et text fields
    JLabel name = new JLabel("Name: ");
    JTextField nf =new JTextField(40);

    JLabel surname = new JLabel("Surname: "); 
    JTextField sf = new JTextField(40);

    JLabel email = new JLabel("Email: ");
    JTextField ef = new JTextField(50);

    JLabel address = new JLabel("Address: ");
    JTextField af = new JTextField(50);

    JLabel number = new JLabel("Phone number: ");
    JTextField pf = new JTextField(20);

    // creation bouton
    JButton valid = new JButton("OK");

    String login;

    // constructeur
    public SignUp(String login){
        
        super("User informations ");
        this.login=login;
        setSize(400, 450 );
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fermer uniquement cette fenêtre
        setLocationRelativeTo(null); // Centrer la fenêtre
        
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(6,2,10,10));

        p.add(name);
        p.add(nf);
        p.add(surname);
        p.add(sf);
        p.add(email);
        p.add(ef);
        p.add(address);
        p.add(af);
        p.add(number);
        p.add(pf);
        p.add(valid);

        // ajout listeners
        valid.addActionListener(this);

        setContentPane(p);
    }

    // lorque le bouton est clique
    public void actionPerformed (ActionEvent e){

        // recup donnees
        String n=nf.getText();
        String s=sf.getText();
        String em=ef.getText();
        String a=af.getText();
        String nb=pf.getText();

        // verifs
        if (n.isEmpty() || s.isEmpty() || em.isEmpty() || a.isEmpty() || nb.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // msg info dans bd
        try (Connection conn =DatabaseConnection.connect();
             PreparedStatement ps =conn.prepareStatement("update users set name=?, surname=?, tel=?, address=?, email=? where login=?"))
        {
            ps.setString(1, n);
            ps.setString(2, s);
            ps.setString(3, nb);
            ps.setString(4, a);
            ps.setString(5, em);
            ps.setString(6,login);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Informations saved successfully! !", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose(); // Fermer cette fenêtre
    
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving informations", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }    
}
