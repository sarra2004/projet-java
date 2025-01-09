package projet;

public class User {
    
    // Les données
    private String username;
    private String name;
    private String surname;
    private String tel;
    private String address;
    private String email;

    // Constructeur
    public User(String username, String name, String surname, String tel, String address, String email) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.tel = tel;
        this.address = address;
        this.email = email;
    }

    // Getters et setters
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getTel() {
        return tel;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
