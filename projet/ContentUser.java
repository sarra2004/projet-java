package projet;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;


public class ContentUser extends JFrame implements ActionListener {
    
    // creation des boutons
    JButton c= new JButton("Check Cars");
    JButton a = new JButton("Account settings");

    // constructeur
    public ContentUser(){
        
        super("User Content page");
        setSize(400, 450 );
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fermer uniquement cette fenêtre
        setLocationRelativeTo(null); // Centrer la fenêtre

        JPanel p= new JPanel();
        p.setLayout(new GridLayout(2,1,10,10));

        p.add(c);
        p.add(a);

        // ajout listeners
        c.addActionListener(this);
        a.addActionListener(this);

        setContentPane(p);
    }

    // lorsque les boutons sont cliques
    public void actionPerformed (ActionEvent e){
        if (e.getSource()==c) {
            // ouvrir nouvelle fenetre
            new CheckCars().setVisible(true);
        }
        else if (e.getSource()==a){
            // ouvrir nouvelle fenetre
            new AccountSettings().setVisible(true);
        }
    }

    // main
    public static void main(String[] args) {
        ContentUser cu = new ContentUser();
        cu.setVisible(true);
    }
}
