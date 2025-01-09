package projet;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.List;

public class ListUsers extends JFrame implements ActionListener {
    
    JComboBox<String> FilterOptions; //choisir une option  pour filtrer ou choisir no filter
    JComboBox<String> logins; 
    
    // creation des labels et un bouton
    JTextArea details;
    JScrollPane s;
    JButton r= new JButton("Return");

    UserDao userDao = new UserDao();

    // constructeur
    public ListUsers(){
        
        super("List Users");
        setSize(400, 450 );
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fermer uniquement cette fenêtre
        setLocationRelativeTo(null); // Centrer la fenêtre

        // init des composants
        FilterOptions = new JComboBox<String>(new String[]{"No Filter", "Username", "Rentals"});
        logins = new JComboBox<String>();
        details = new JTextArea(15, 40);
        details.setEditable(false);
        s = new JScrollPane(details);

        JPanel panel= new JPanel();
        panel.setLayout(new GridLayout(3,2,10,10));

        panel.add(new JLabel("Filter by: "));
        panel.add(FilterOptions);
        panel.add(new JLabel("Login: "));
        panel.add(logins);
        panel.add(new JLabel());
        panel.add(r);

        JPanel mainP= new JPanel();
        mainP.setLayout(new BorderLayout());
        mainP.add(panel, BorderLayout.NORTH);
        mainP.add(s, BorderLayout.CENTER);

        // ajout listeners
        FilterOptions.addActionListener(this);
        r.addActionListener(this);

        setContentPane(mainP);

        loadUserDetails("No Filter", null);
    }

    // lorsque les boutons sont cliques
    public void actionPerformed (ActionEvent x){
        if (x.getSource()==FilterOptions) {
            // recuperer le filter selectionne
            String Selectedfilter= (String)FilterOptions.getSelectedItem();
            if ("Username".equals(Selectedfilter)) {
                // voir login dropdown
                logins.setVisible(true);
                // charger les logins
                loadLogins();
            } 
            else {
                // hide login dropdown
                logins.setVisible(false);
                if ("No Filter".equals(Selectedfilter)) {
                    loadUserDetails("No Filter", null);
                } 
                else if ("Rentals".equals(Selectedfilter)) {
                    loadUserDetails("Rentals", null);
                } 
            }
        }
        else if (x.getSource()==logins) {
            // recuperer le login selectionne
            String Selectedlogin= (String)logins.getSelectedItem();
            if (Selectedlogin!=null) {
                // charger les details de l utilisateur
                loadUserDetails("Username", Selectedlogin); 
            }
        }
        else if (x.getSource()==r) {
            // fermer la fenetre
            this.dispose();
            // retour a la fenetre ContentPage
            new ContentPage().setVisible(true);
        }
    }

     // charger logins a partir de la bd
     public void loadLogins(){
        logins.removeAllItems();
        List<String> usernames = userDao.loadLogins();
        for (String username : usernames) {
            logins.addItem(username);
        }
    }

    // charger les details de l utilisateur a partie de la bd 
    public void loadUserDetails(String choix, String valeur){
        
        List<User> users;
        if ("Username".equals(choix)) {
            User user = userDao.loadUserByLogin(valeur);
            users = user != null ? List.of(user) : List.of();
        } 
        else if ("No Filter".equals(choix)) {
            users = userDao.loadAllUsers();
        } 
        else if("Rented".equals(choix)) {
            users = userDao.loadRenters();
        }
        else {
            users =List.of();
        }
        displayUserDetails(users);
    }

    // charger les details des utilisateurs
    public void displayUserDetails(List<User> users){
        details.setText("");
        if (users.isEmpty()) {
            details.setText("No users found");
            return;
        }
    
        for (User user : users) {
            details.append("Username: " + user.getUsername() + "\n");
            details.append("Name: " + user.getName() + "\n");
            details.append("Surname: " + user.getSurname() + "\n");
            details.append("Phone: " + user.getTel() + "\n");
            details.append("Email: " + user.getEmail() + "\n");
            details.append("Address: " + user.getAddress() + "\n");
        }
    }
            
    // main
    public static void main(String[] args) {
        ListUsers lu = new ListUsers();
        lu.setVisible(true);
    }
}
