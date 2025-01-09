package projet;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class ContentUser extends JFrame implements ActionListener {
    
    private String username;

    // creation des boutons
    JButton c= new JButton("Check Cars");
    JButton a = new JButton("Account settings");
    JButton r = new JButton("Return");

    // constructeur
    public ContentUser(String username){
        
        super("Welcome "+username);
        this.username=username;
        setSize(400, 450 );
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fermer uniquement cette fenêtre
        setLocationRelativeTo(null); // Centrer la fenêtre

        JPanel p= new JPanel();
        p.setLayout(new GridLayout(3,1,10,10));
        p.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        styleButton(c);
        styleButton(a);
        styleButton(r);

        p.add(c);
        p.add(a);
        p.add(r);

        // ajout listeners
        c.addActionListener(this);
        a.addActionListener(this);
        r.addActionListener(this);

        setContentPane(p);
    }

    // styling
    private void styleButton (JButton b){
        b.setFont(new Font("Arial", Font.BOLD, 16));
        b.setBackground(new Color(70, 130, 180));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    // lorsque les boutons sont cliques
    public void actionPerformed (ActionEvent e){
        if (e.getSource()==c) {
            // fermer la fenetre actuelle
            this.dispose();
            // ouvrir nouvelle fenetre
            new CheckCars(username).setVisible(true);
        }
        else if (e.getSource()==a){
            // fermer la fenetre actuelle
            this.dispose();
            // ouvrir nouvelle fenetre
            new AccountSettings(username).setVisible(true);
        }
        else if (e.getSource()==r) {
            // fermer la fenetre actuelle
            this.dispose();
            // retour a la fentre UserPage 
            new UserPage().setVisible(true );
            
        }
    }

    // main
    public static void main(String[] args) {
        ContentUser cu = new ContentUser("Guest");
        cu.setVisible(true);
    }
}
