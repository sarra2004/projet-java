package projet;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class UserPage extends JFrame implements ActionListener {
    
    // creation des label
    JLabel login = new JLabel("Username:");
    JLabel pwd = new JLabel("Password:");

    // creationd du text field et pwd field
    JTextField t1 = new JTextField(20);
    JPasswordField t2 = new JPasswordField(20);

    // creation des bouttons
    JButton b1 = new JButton("Log In");
    JButton b2 = new JButton("Sign up");
    JButton r = new JButton("Cancel");

    // constructeur 
    public UserPage(){
        
        super("User Login");
        setSize(400, 300 );
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fermer uniquement cette fenêtre
        setLocationRelativeTo(null); // Centrer la fenêtre
        
        // main panel 
        JPanel p = new JPanel(new BorderLayout());
        
        // panel form
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
        r.setPreferredSize(new Dimension(100, 35));
        r.setFont(new Font("Arial", Font.BOLD, 14));
        r.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.add(r);

        p.add(form, BorderLayout.CENTER);
        p.add(button, BorderLayout.SOUTH);

        // ajout listeners 
        b1.addActionListener(this);
        b2.addActionListener(this);
        r.addActionListener(this);

        setContentPane(p);
    }

    // lorsque les boutons sont cliques
    @Override
    public void actionPerformed(ActionEvent ev) {
        if (ev.getSource() == b1) {
            loginMethod();
        } 
        else if (ev.getSource() == b2) {
            signupMethod();
        }
        else if (ev.getSource()==r) {
            // retour a la fenetre ManagementPage
            new ManagementPage().setVisible(true);
            // fermer la fentre actuelle
            this.dispose();
        }
    }

    // login
    private void loginMethod(){

        // recup  login et pwd
        String ch1 = t1.getText();
        String ch2 = new String(t2.getPassword());

        // Conditions verif pwd
        if (ch1.isEmpty() || ch2.isEmpty()) {
            JOptionPane.showMessageDialog(this, "You have to fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            UserDao userDao = new UserDao();
            User user = userDao.loadUser(ch1, ch2);

            if (user != null) {
                JOptionPane.showMessageDialog(this, "Connection successful !", "Success", JOptionPane.INFORMATION_MESSAGE);
                new ContentUser(ch1).setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Incorrect login or password please re-register.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred during login, please try again", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // sign up
    private void signupMethod(){

        // recup  login et pwd
        String ch1 = t1.getText();
        String ch2 = new String(t2.getPassword());

        // Conditions verif pwd
        if (ch1.isEmpty() || ch2.isEmpty()) {
            JOptionPane.showMessageDialog(this, "You have to fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } 
        String valid = PwdValid.validpwd(ch2);
        if (valid != null) {
            JOptionPane.showMessageDialog(this, valid, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            UserDao userDao = new UserDao();
            if (!userDao.addUser(ch1, ch2)) {
                JOptionPane.showMessageDialog(this, "This username already exists please choose another one", "Error", JOptionPane.ERROR_MESSAGE);
                return;   
            }
            new SignUp(ch1).setVisible(true);
            this.dispose();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred during signup, please try again", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // main
    public static void main(String[] args) {
        UserPage a = new UserPage();
        a.setVisible(true);
    }
}
