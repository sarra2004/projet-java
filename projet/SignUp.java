package projet;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class SignUp extends JFrame implements ActionListener{
    
    // creation labels et text fields
    JLabel name = new JLabel("Name: ");
    JTextField nf =new JTextField(20);

    JLabel surname = new JLabel("Surname: "); 
    JTextField sf = new JTextField(20);

    JLabel email = new JLabel("Email: ");
    JTextField ef = new JTextField(20);

    JLabel address = new JLabel("Address: ");
    JTextField af = new JTextField(20);

    JLabel number = new JLabel("Phone number: ");
    JTextField pf = new JTextField(20);

    // creation bouton
    JButton valid = new JButton("Log In");
    JButton r= new JButton("Return");

    String login;

    // constructeur
    public SignUp(String login){
        
        super("User informations ");
        this.login=login;
        setSize(400, 450 );
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fermer uniquement cette fenêtre
        setLocationRelativeTo(null); // Centrer la fenêtre
        
        // main panel
        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addComposant(p, gbc, name, 0, 0);
        addComposant(p, gbc, nf, 0, 1);
        addComposant(p, gbc, surname, 1, 0);
        addComposant(p, gbc, sf, 1, 1);
        addComposant(p, gbc, email, 2, 0);
        addComposant(p, gbc, ef, 2, 1);
        addComposant(p, gbc, address, 3, 0);
        addComposant(p, gbc, af, 3, 1);
        addComposant(p, gbc, number, 4, 0);
        addComposant(p, gbc, pf, 4, 1);

        // button panel
        JPanel button = new JPanel();
        button.add(valid);
        button.add(r);
        valid.setFont(new Font("Arial", Font.BOLD, 14));
        r.setFont(new Font("Arial", Font.BOLD, 14));
        valid.setCursor(new Cursor(Cursor.HAND_CURSOR));
        r.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gbc.gridy=5;
        gbc.gridx=0;
        gbc.gridwidth=2;
        p.add(button, gbc);
        
        // ajout listeners
        valid.addActionListener(this);
        r.addActionListener(this);

        setContentPane(p);
        pack();
    }

    // styling
    private void addComposant (JPanel p, GridBagConstraints gbc, Component c, int row, int col){
        gbc.gridy=row;
        gbc.gridx=col;
        p.add(c, gbc);
    }

    // lorque le bouton est clique
    public void actionPerformed (ActionEvent e){

        if (e.getSource()==valid){
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

            // update bd
            try {
                UserDao userDao = new UserDao();
                User user = new User(login, n, s, nb, a, em);
                userDao.updateUser(user);

                JOptionPane.showMessageDialog(this, "Informations saved successfully! !", "Success", JOptionPane.INFORMATION_MESSAGE);
                this.dispose(); // Fermer cette fenêtre
                // ouvrir la fenêtre ContentUser
                new ContentUser(login).setVisible(true);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error saving informations", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        else if (e.getSource()==r){
            this.dispose();
            new ContentUser(login).setVisible(true);
        }
        
    }    

    // main
    public static void main(String[] args) {
        SignUp su = new SignUp("exemple");
        su.setVisible(true);
    }
}
