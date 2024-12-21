package assignment3.modules;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import services.MailService;

public class Admin extends User {
    private static final List<User> userAccounts = new ArrayList<>();

    public Admin(Long id, String name, String email) {
        super(id, name, email, "admin");
    }

    @Override
    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("\n--- Admin Menu ---");
                System.out.println("1. Retrieve Accounts");
                System.out.println("2. Send Message");
                System.out.println("3. View Messages");
                System.out.println("4. Logout");

                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> retrieveAccounts(null);
                    case 2 -> sendMessage();
                    case 3 -> viewMessages();
                    case 4 -> {
                        System.out.println("Logging out...");
                        return;
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            }
        
    }

    public void retrieveAccounts(String role) {
        System.out.println("Retrieving accounts...");
        boolean found = false;

        if (role == null) {
            for (User user : userAccounts) {
                System.out.println(user);
                found = true;
            }
        } else {
            for (User user : userAccounts) {
                if (user.getRole().equalsIgnoreCase(role)) {
                    System.out.println(user);
                    found = true;
                }
            }
        }

        if (!found) {
            System.out.println("No users found with the role: " + (role == null ? "All roles" : role));
        }
    }

    private void sendMessage() {
        Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the recipient's email: ");
            String recipientEmail = scanner.nextLine();

            System.out.print("Enter your message: ");
            String message = scanner.nextLine();

            mailService.sendMessage(recipientEmail, "Admin Message: " + message);
        
        System.out.println("Message sent successfully.");
    }

    private void viewMessages() {
        System.out.println("Your messages:");
        List<String> messages = mailService.getMessages(getEmail());
        if (messages.isEmpty()) {
            System.out.println("No messages.");
        } else {
            messages.forEach(System.out::println);
        }
    }

    public static void addUser(User user) {
        userAccounts.add(user);
        System.out.println("User added: " + user.getName());
    }

    public static void removeUser(Long userId) {
        userAccounts.removeIf(user -> user.getId().equals(userId));
        System.out.println("User with ID " + userId + " has been removed.");
    }
}
