package projet;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class ContentPage extends JFrame implements ActionListener{
    
    // creation des bouttons
    JButton c= new JButton("List Cars");
    JButton u = new JButton("List Users");
    JButton a = new JButton("Add Cars");
    
    // constructeur
    public ContentPage(){
        
        super("Content page");
        setSize(400, 450 );
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fermer uniquement cette fenêtre
        setLocationRelativeTo(null); // Centrer la fenêtre

        JPanel p= new JPanel();
        p.setLayout(new GridLayout(3,1,10,10));

        p.add(c);
        p.add(u);
        p.add(a);

        // ajout listeners
        c.addActionListener(this);
        u.addActionListener(this);
        a.addActionListener(this);

        setContentPane(p);
    }

    // lorsque les boutons sont cliques 
    public void actionPerformed (ActionEvent e){
        if (e.getSource()==c) {
            // ouvrir nouvelle fenetre 
            new ListCars().setVisible(true);
        }
        else if (e.getSource()==u) {
            // ouvrir nouvelle fenetre 
            new ListUsers().setVisible(true);
        }
        else if (e.getSource()==a){
            // ouvrir nouvelle fenetre 
            new AddCars().setVisible(true);
        }
    }

    // main
    public static void main(String[] args) {
        ContentPage cp = new ContentPage();
        cp.setVisible(true);
    }
}
