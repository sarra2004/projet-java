package projet;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.List;

public class ListCars extends JFrame implements ActionListener{
    
    JComboBox<String> FilterOptions; //choisir une option  pour filtrer ou choisir no filter
    JComboBox<String> FilterValues; //choisir une valeur pour filtrer
    
    // creation des labels et un bouton
    JLabel userlabel;
    JButton r= new JButton("Return");
    JTextArea details;
    JScrollPane s;

    CarDao carDao = new CarDao();
    
    // constructeur
    public ListCars(){
        
        super("List Cars");
        setSize(400, 450 );
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fermer uniquement cette fenêtre
        setLocationRelativeTo(null); // Centrer la fenêtre

        // init des composants
        FilterOptions = new JComboBox<String>(new String[]{"No Filter", "Brand", "Model", "Color", "Price", "Availability"});
        FilterValues = new JComboBox<String>();

        details = new JTextArea(15, 40);
        details.setEditable(false);
        s = new JScrollPane(details);
        userlabel = new JLabel();

        JPanel panel= new JPanel();
        panel.setLayout(new GridLayout(10,2,10,10));

        panel.add(new JLabel("Filter by: "));
        panel.add(FilterOptions);
        panel.add(new JLabel("Filter Value: "));
        panel.add(FilterValues);
        panel.add(new JLabel("Rented by: "));
        panel.add(userlabel);
        panel.add(r);

        JPanel mainP= new JPanel();
        mainP.setLayout(new BorderLayout());
        mainP.add(panel, BorderLayout.NORTH);
        mainP.add(s, BorderLayout.CENTER);

        // ajout listeners
        FilterOptions.addActionListener(this);
        FilterValues.addActionListener(this);
        r.addActionListener(this);

        setContentPane(mainP);   

        loadFilterOptions();
    }

    // charger les options de filtrage
    private void loadFilterOptions(){
        FilterOptions.setSelectedIndex(0); // No Filter default
        FilterValues.setEnabled(false); // desactiver le choix des valeurs
        loadAllCars(); // charger toutes les voitures
    }

    // charger les valeurs a partir de filtre choisi
    private void loadFilterValues(String choix){
        
        FilterValues.removeAllItems();
        FilterValues.setEnabled(true);

        List<String> vlist = null;

        switch (choix) {
            case "Brand":
                vlist = carDao.loadBrands();
                break;
            case "Model":
                vlist = carDao.loadModels();
                break;
            case "Color":
                vlist = carDao.loadColors();
                break;
            case "Price":
                vlist = carDao.loadPrices();
                break;
            case "Availability":
                vlist = carDao.loadAvailabilities();
                break;
        }

        // ajouter les valeurs a la liste
        if (vlist != null){
            for (String v : vlist) {
                FilterValues.addItem(v);
            }
        } 
    }

    // charger les details d'une voiture a partir du filtre et de la valeur
    private void loadCarDetailsbyFilter(String choix, String valeur){
        List<Car> cars = carDao.loadCarsByFilter(choix, valeur);
        displayCarDetails(cars);
    }

    // charger toutes les voitures
    private void loadAllCars(){
        List<Car> cars = carDao.loadAllCars();
        displayCarDetails(cars);
    }

    // charger les details d'une voiture a partir de la bd
    private void displayCarDetails(List<Car> cars){

        details.setText("");

        if (cars.isEmpty()) {
            details.setText("No cars found");
            return;
        }
        // charger les details des car
         for (Car car : cars) {
            details.append("Brand: " + car.getBrand() + "\n");
            details.append("Model: " + car.getModel() + "\n");
            details.append("Color: " + car.getColor() + "\n");
            details.append("Price: " + car.getPrice() + "\n");
            details.append("Availability: " + car.getAvailability() + "\n");
            details.append("Rented by: " + car.getUsername() + "\n\n");
        }
    }
    // lorsque les boutons sont cliques
    public void actionPerformed (ActionEvent e){
        if (e.getSource()==FilterOptions) {
            // recuperer l option selectionnee
            String Selectedfilter= (String)FilterOptions.getSelectedItem();
            loadFilterValues(Selectedfilter);
        }
        else if (e.getSource()==FilterValues) {
            // recuperer l option selectionnee
            String Selectedfilter= (String)FilterOptions.getSelectedItem();
            // recuperer la valeur selectionnee
            String SelectedValue= (String)FilterValues.getSelectedItem();
            loadCarDetailsbyFilter(Selectedfilter, SelectedValue);
        }
        else if (e.getSource()==r) {
            // fermer la fenetre
            this.dispose();
            // retour a la fenetre ContentPage
            new ContentPage().setVisible(true);
        }
    }
        
    // main
    public static void main(String[] args) {
        ListCars lc = new ListCars();
        lc.setVisible(true);
    }
}
