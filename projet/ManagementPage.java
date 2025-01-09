package projet;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class ManagementPage extends JFrame implements ActionListener{
    
    // creation des icons qui vont etre dans les buttons 
    ImageIcon adminIcon = new ImageIcon(new ImageIcon("projet\\image\\software-engineer.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
    ImageIcon userIcon = new ImageIcon(new ImageIcon("projet\\image\\group.png").getImage().getScaledInstance(50,50, Image.SCALE_SMOOTH));

    // creation des buttons
    JButton admin = new JButton("Admin", adminIcon);
    JButton user = new JButton("User", userIcon);

    // constructeur
    public ManagementPage(){
        
        super("Admin/User");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrer la fenêtre
        
        JPanel p = new JPanel();
        p.setLayout(new GridBagLayout()); //centrer les boutons

        // panel pour contenir les boutons pour styling
        JPanel button = new JPanel();
        button.setLayout(new GridLayout(2, 1, 10, 10));
        button.add(admin);
        button.add(user);

        p.add(button);

        // ajout styling
        Font buttonFont = new Font("Arial", Font.BOLD,16 );
        admin.setFont(buttonFont);
        user.setFont(buttonFont);
        admin.setPreferredSize(new Dimension(200, 80));
        user.setPreferredSize(new Dimension(200, 80));
        admin.setFocusPainted(false);
        user.setFocusPainted(false);
        
        // positionnement bouton et icon
        admin.setHorizontalTextPosition(SwingConstants.LEFT);
        user.setHorizontalTextPosition(SwingConstants.LEFT);

        // cursor 
        admin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        user.setCursor(new Cursor(Cursor.HAND_CURSOR));

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
        }
        else if (e.getSource()==user) {
            // ouvrir nouvelle fenetre pour user
            new UserPage().setVisible(true);
        }
        // fermer fenetre actuelle
        dispose();
    }

    // main
    public static void main(String[] args) {
        ManagementPage m= new ManagementPage();
        m.setVisible(true);
    }  
}