package projet;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class AccountSettings extends JFrame implements ActionListener {
    
    // creation des labels et text fields et boutons 
    JTextField login, email, tel, address, name, surname;
    
    JButton s = new JButton("Save");
    JButton r = new JButton("Return");

    private String username;
    private UserDao userDao;

    // constructeur
    public AccountSettings (String username){

        super("Account Settings");
        this.username=username;
        this.userDao = new UserDao();
        
        setSize(500, 400 );
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fermer uniquement cette fenêtre
        setLocationRelativeTo(null); // Centrer la fenêtre

        // init composants
        login = createText(false);
        name = createText(false);
        surname = createText(false);
        email = createText(true);
        tel = createText(true);
        address = createText(true);
        
        // charger donnees users
        loadUserData();

        // main panel
        JPanel p= new JPanel(new GridBagLayout());
        p.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        GridBagConstraints gbc=new GridBagConstraints();
        gbc.insets=new Insets(10, 10, 10, 10);
        gbc.fill=GridBagConstraints.HORIZONTAL;
        gbc.anchor=GridBagConstraints.WEST;

        // ajout composants 
        addComposant(p, "Username:", login, gbc, 0);
        addComposant(p, "Name:", name, gbc, 1);
        addComposant(p, "Surname:", surname, gbc, 2);
        addComposant(p, "Email:", email, gbc, 3);
        addComposant(p, "Number::", tel, gbc, 4);
        addComposant(p, "Address:", address, gbc, 5);
        
        // panel bouton
        JPanel button= new JPanel();
        s.setFont(new Font("Arial", Font.BOLD, 14));
        r.setFont(new Font("Arial", Font.BOLD, 14));
        s.setCursor(new Cursor(Cursor.HAND_CURSOR));
        r.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.add(s);
        button.add(r);
        gbc.gridy=6;
        gbc.gridwidth=2;
        p.add(button, gbc);
       
        // ajout des listeners
        s.addActionListener(this);
        r.addActionListener(this);

        setContentPane(p);
    }

    // styling textfields 
    private JTextField createText (boolean edit){
        JTextField text=new JTextField(20);
        text.setEditable(edit);
        text.setFont(new Font("Arial", Font.PLAIN, 14));
        return text;
    }

    // pour le styling des labels 
    private void addComposant(JPanel p, String label, JTextField text, GridBagConstraints gbc, int y){
        JLabel l= new JLabel(label);
        l.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx=-1;
        gbc.gridy=y;
        p.add(l, gbc);
        gbc.gridwidth=1;
        p.add(text, gbc);
    }

    // charger les donnees a partir de UserDao
    private void loadUserData() {
        User user = userDao.loadUserByLogin(username);
        if (user != null) {
            login.setText(user.getUsername());
            name.setText(user.getName());
            surname.setText(user.getSurname());
            email.setText(user.getEmail());
            tel.setText(user.getTel());
            address.setText(user.getAddress());
        }
    }

    // enregistrer les modifs 
    private void saveUserData() {
        
        User user = new User(
            username,
            name.getText(),
            surname.getText(),
            email.getText(),
            tel.getText(),
            address.getText()
        );
        userDao.updateUser(user);
        JOptionPane.showMessageDialog(this, "Updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    // lorsque les boutons sont cliques
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == s) {
            // Enregistrer les modif
            saveUserData();
        } else if (e.getSource() == r) {
            // Fermer la fenêtre
            this.dispose();
            // retour a la fenetre ContentUser
            new ContentUser(username).setVisible(true);
        }
    }

    // main
    public static void main(String[] args) {
        AccountSettings as = new AccountSettings("example");
        as.setVisible(true);
    }
}
