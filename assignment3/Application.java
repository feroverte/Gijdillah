import services.AuthService;
import services.MailService;
import services.PropertyManager;
import modules.*;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        AuthService authService = new AuthService();
        PropertyManager propertyManager = new PropertyManager();
        MailService mailService = new MailService();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Parallax Real Estate System!");

        while (true) {
            try {
                System.out.print("Enter your email to login (or type 'exit' to quit): ");
                String email = scanner.nextLine();

                if (email.equalsIgnoreCase("exit")) {
                    System.out.println("Exiting the application. Goodbye!");
                    break;
                }

                User loggedInUser = authService.login(email);
                if (loggedInUser != null) {
                    loggedInUser.setMailService(mailService); // Set shared MailService
                    loggedInUser.showMenu();
                } else {
                    System.out.println("Invalid email. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred. Please try again.");
            }
        }

        scanner.close();
    }
}





