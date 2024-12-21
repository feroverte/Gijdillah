package assignment3.modules;

import services.PropertyManager;
import java.util.List;
import java.util.Scanner;

public class Seller extends User {
    private PropertyManager propertyManager;

    public Seller(Long id, String name, String email) {
        super(id, name, email, "seller");
        this.propertyManager = new PropertyManager();
    }

    @Override
    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Seller Menu ---");
            System.out.println("1. Create Property");
            System.out.println("2. Edit Property");
            System.out.println("3. Archive Property");
            System.out.println("4. Sign Contract");
            System.out.println("5. View Messages");
            System.out.println("6. Logout");

            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> createProperty();
                case 2 -> editProperty();
                case 3 -> archiveProperty();
                case 4 -> signContract();
                case 5 -> viewMessages();
                case 6 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void createProperty() {
        Scanner scanner = new Scanner(System.in);
            System.out.print("Enter property name: ");
            String name = scanner.nextLine();
            System.out.print("Enter property location: ");
            String location = scanner.nextLine();
            System.out.print("Enter property price: ");
            double price = scanner.nextDouble();

            Property property = new Property(System.currentTimeMillis(), name, location, price, false);
            propertyManager.addProperty(property);
            System.out.println("Property created: " + property);
        }
    

    private void editProperty() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter property ID to edit: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        System.out.print("Enter new name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new location: ");
        String location = scanner.nextLine();
        System.out.print("Enter new price: ");
        double price = scanner.nextDouble();

        propertyManager.editProperty(id, name, location, price);
        System.out.println("Property updated.");
    }

    private void archiveProperty() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter property ID to archive: ");
        Long id = scanner.nextLong();

        propertyManager.archiveProperty(id);
        System.out.println("Property archived.");
    }

    private void signContract() {
        System.out.println("Signing contract...");
        System.out.println("Contract signed successfully.");
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
