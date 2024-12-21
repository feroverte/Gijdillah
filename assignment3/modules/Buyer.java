package modules;

import services.PropertyManager;
import java.util.List;
import java.util.Scanner;

public class Buyer extends User {
    private PropertyManager propertyManager;

    public Buyer(Long id, String name, String email) {
        super(id, name, email, "buyer");
        this.propertyManager = new PropertyManager();
    }

    @Override
    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Buyer Menu ---");
            System.out.println("1. Search Properties");
            System.out.println("2. Contact Seller/Agent");
            System.out.println("3. Negotiate Contract");
            System.out.println("4. Make Payment");
            System.out.println("5. View Messages");
            System.out.println("6. Logout");

            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> searchProperties();
                case 2 -> contactSellerOrAgent();
                case 3 -> negotiateContract();
                case 4 -> makePayment();
                case 5 -> viewMessages();
                case 6 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void searchProperties() {
        System.out.println("\n--- Search Properties ---");
        propertyManager.getProperties().forEach(System.out::println);
    }

    private void contactSellerOrAgent() {
        Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the property ID you are interested in: ");
            Long propertyId = scanner.nextLong();
            scanner.nextLine();

            System.out.print("Enter the recipient's email: ");
            String recipientEmail = scanner.nextLine();

            System.out.print("Enter your message: ");
            String message = scanner.nextLine();

            mailService.sendMessage(recipientEmail, "Property ID: " + propertyId + " - " + message);
        
        System.out.println("Message sent successfully.");
    }

    private void negotiateContract() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter property ID to negotiate: ");
            Long propertyId = scanner.nextLong();

            System.out.println("Negotiating contract for Property ID: " + propertyId);
        }
        System.out.println("Contract successfully negotiated. Proceed to payments.");
    }

    private void makePayment() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter property ID for payment: ");
            Long propertyId = scanner.nextLong();
        }
        System.out.println("Making an initial deposit of 10%...");
        System.out.println("Payment processed. Remaining amount will be deducted monthly.");
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
}
