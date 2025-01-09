package projet;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.List;

public class CheckCars extends JFrame implements ActionListener {

    private CarDao carDao;
    private Car car;
    JPanel carsP;
    JButton r;
    private String username;

    // constructeur
    public CheckCars(String username){
        
        super("Available Cars");
        carDao = new CarDao();
        this.username=username;
        setSize(400, 450 );
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fermer uniquement cette fenêtre
        setLocationRelativeTo(null); // Centrer la fenêtre
        setLayout(new BorderLayout());

        // init des composants
        carsP = new JPanel();
        carsP.setLayout(new BoxLayout(carsP, BoxLayout.Y_AXIS));
        carsP.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // charger available cars a partir de la bd
        List<Car> carsAV = carDao.getAvailableCars();
        displayCars(carsAV);

        // composant pour scroller
        JScrollPane s= new JScrollPane(carsP);
        add(s, BorderLayout.CENTER);

        // bouton retour
        r= new JButton("Return");
        add(r, BorderLayout.SOUTH);
        r.addActionListener(this);
    }

    // charger les details des voitures disponibles
    private void displayCars(List<Car> cars){
        carsP.removeAll();
        if (cars.isEmpty()) {
            JLabel noCars = new JLabel("No cars available");
            carsP.add(noCars);
            return;
        }
        for (Car car : cars) {
            JPanel carP = new JPanel();
            carP.setLayout(new GridLayout(1, 5, 10, 10));
            
            carP.add(new JLabel("Brand: " + car.getBrand()));
            carP.add(new JLabel("Model: " + car.getModel()));
            carP.add(new JLabel("Color: " + car.getColor()));
            carP.add(new JLabel("Price: " + car.getPrice()));
            
            JButton b= new JButton("Book Car");
            
            // listener
            b.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    bookCar(car); // Pass the car instance
                }
            });

            carP.add(b);
            carsP.add(carP);
        }
    }

    // pour louer la voiture
    private void bookCar (Car car){
        int confirm = JOptionPane.showConfirmDialog(this, "Do you want to book this car ?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = carDao.bookCar(car, username);
            if (success) {
                JOptionPane.showMessageDialog(this, "Car booked successfully !", "Success", JOptionPane.INFORMATION_MESSAGE);
                displayCars(carDao.getAvailableCars());
            }
            else {
                JOptionPane.showMessageDialog(this, "Failure to book.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    // lorsque les boutons sont cliques
    public void actionPerformed (ActionEvent e){
        if (e.getSource()==r) {
            // fermer la fenetre
            this.dispose();
            // retour a la fenetre ContentUser
            new ContentUser(username).setVisible(true);
        }
    }

    // main
    public static void main(String[] args) {
        CheckCars cc = new CheckCars("Guest");
        cc.setVisible(true);
    }
}
