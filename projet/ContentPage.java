package projet;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class ContentPage extends JFrame implements ActionListener{
    
    // creation des bouttons
    JButton c= new JButton("List Cars");
    JButton u = new JButton("List Users");
    JButton a = new JButton("Add Cars");
    JButton back = new JButton("Back");
    
    // constructeur
    public ContentPage(){
        
        super("Content page");
        setSize(400, 450 );
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fermer uniquement cette fenêtre
        setLocationRelativeTo(null); // Centrer la fenêtre

        JPanel p= new JPanel();
        p.setLayout(new GridLayout(4,1,10,10));
        p.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        styleButton(c);
        styleButton(u);
        styleButton(a);
        styleButton(back);

        p.add(c);
        p.add(u);
        p.add(a);
        p.add(back);

        // ajout listeners
        c.addActionListener(this);
        u.addActionListener(this);
        a.addActionListener(this);
        back.addActionListener(this);

        setContentPane(p);
    }

    // styling
    private void styleButton (JButton b){
        b.setFont(new Font("Arial", Font.BOLD, 16));
        b.setBackground(new Color(70, 130, 180));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    // lorsque les boutons sont cliques 
    public void actionPerformed (ActionEvent e){
        if (e.getSource()==c) {
            // ouvrir nouvelle fenetre 
            new ListCars().setVisible(true);
            // fermer cette fenetre
            dispose();
        }
        else if (e.getSource()==u) {
            // ouvrir nouvelle fenetre 
            new ListUsers().setVisible(true);
            // fermer cette fenetre
            dispose();
        }
        else if (e.getSource()==a){
            // ouvrir nouvelle fenetre 
            new AddCars().setVisible(true);
            // fermer cette fenetre
            dispose();
        }
        else if (e.getSource()==back){
            // ouvrir nouvelle fenetre 
            new AdminPage().setVisible(true);
            // fermer cette fenetre
            dispose();
        }
    }

    // main
    public static void main(String[] args) {
        ContentPage cp = new ContentPage();
        cp.setVisible(true);
    }
}
