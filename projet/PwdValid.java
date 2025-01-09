package projet;

public class PwdValid {
    
    public static String validpwd (String pwd){
        
        if (pwd.isEmpty()) {
            return "You have to fill all fields";
        } 
        if (pwd.length()<8) {
            return "The password must contain at least 8 characters";
        }
        if (!pwd.matches(".*[A-Z].*")) {
            return "The password must have at least a capital letter";
        }
        if (!pwd.matches(".*[0-9].*")) {
            return "The password must have at least a number";
        }
        if (!pwd.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
            return "The password must have at least a special character";
        }
        return null;
    }
}
