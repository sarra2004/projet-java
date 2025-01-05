package projet;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class ManagementPage extends JFrame implements ActionListener{
    
    // creation des icons qui vont etre dans les buttons 
    ImageIcon adminIcon = new ImageIcon("projet\\image\\software-engineer.png");
    ImageIcon userIcon = new ImageIcon("projet\\image\\group.png");

    // creation des buttons
    JButton admin = new JButton("Admin", adminIcon);
    JButton user = new JButton("User", userIcon);

    // constructeur
    public ManagementPage(){
        
        super("Admin/User");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrer la fenêtre
        
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(2,1,10,10));
        
        p.add(admin);
        p.add(user);
        
        // positionnement bouton et icon
        admin.setHorizontalTextPosition(SwingConstants.LEFT);
        user.setHorizontalTextPosition(SwingConstants.LEFT);

        // ajout des listeners
        admin.addActionListener(this);
        user.addActionListener(this);

        setContentPane(p);
    }

    // lorsque les boutons sont cliques 
    public void actionPerformed (ActionEvent e){
        if (e.getSource()==admin) {
            // ouvrir nouvelle fenetre pour admin
            new AdminPage().setVisible(true);
            //fermer fenetre actuelle
            dispose(); 
        }
        else if (e.getSource()==user) {
            // ouvrir nouvelle fenetre pour user
            new UserPage().setVisible(true);
            //fermer fenetre actuelle
            dispose(); 
        }
    }

    // main
    public static void main(String[] args) {
        ManagementPage m= new ManagementPage();
        m.setVisible(true);
    }  
}