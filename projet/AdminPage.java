package projet;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class AdminPage extends JFrame implements ActionListener{
    
    // creation des label
    JLabel login = new JLabel("Username:");
    JLabel pwd = new JLabel("Password:");

    // creation du text field et pwd field
    JTextField t1 = new JTextField(20);
    JPasswordField t2 = new JPasswordField(20);

    // creation des bouttons
    JButton b1 = new JButton("OK");
    JButton b2 = new JButton("Cancel");

    // constructeur
    public AdminPage(){
        
        super("Admin Login");
        setSize(400, 300 );
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fermer uniquement cette fenêtre
        setLocationRelativeTo(null); // Centrer la fenêtre
        
        // main panel 
        JPanel p = new JPanel(new BorderLayout());

        // panel lform 
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets=new Insets(10, 10, 10, 10);
        gbc.fill=GridBagConstraints.HORIZONTAL;

        // ajout des labels et textfields de username
        gbc.gridx=0;
        gbc.gridy=0;
        login.setFont(new Font("Arial", Font.BOLD, 14));
        form.add(login, gbc);
        gbc.gridx=1;
        t1.setFont(new Font("Arial", Font.PLAIN, 14));
        form.add(t1, gbc);

        // ajout des labels et textfields de password 
        gbc.gridx=0;
        gbc.gridy=1;
        pwd.setFont(new Font("Arial", Font.BOLD, 14));
        form.add(pwd, gbc);
        gbc.gridx=1;
        t2.setFont(new Font("Arial", Font.PLAIN, 14));
        form.add(t2, gbc);

        // panel of the button 
        JPanel button = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        b1.setPreferredSize(new Dimension(100, 35));
        b1.setFont(new Font("Arial", Font.BOLD, 14));
        b1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.add(b1);
        b2.setPreferredSize(new Dimension(100, 35));
        b2.setFont(new Font("Arial", Font.BOLD, 14));
        b2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.add(b2);

        p.add(form, BorderLayout.CENTER);
        p.add(button, BorderLayout.SOUTH);

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
            String pwdError = PwdValid.validpwd(ch2);
            if (ch1.isEmpty()) {
                JOptionPane.showMessageDialog(this, "You have to fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else if (pwdError != null) {
                JOptionPane.showMessageDialog(this, pwdError, "Error", JOptionPane.ERROR_MESSAGE);
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
