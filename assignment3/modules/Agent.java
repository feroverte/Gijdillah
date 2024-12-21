package assignment3.modules;

import services.PropertyManager;
import services.MailService;
import java.util.List;
import java.util.Scanner;

public class Agent extends User {
    private PropertyManager propertyManager;

    public Agent(Long id, String name, String email) {
        super(id, name, email, "agent");
        this.propertyManager = new PropertyManager();
    }

    @Override
    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("\n--- Agent Menu ---");
                System.out.println("1. Manage Properties");
                System.out.println("2. View Interested Buyers");
                System.out.println("3. Prepare Contracts");
                System.out.println("4. View Messages");
                System.out.println("5. Logout");

                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> manageProperties();
                    case 2 -> viewInterestedBuyers();
                    case 3 -> prepareContracts();
                    case 4 -> viewMessages();
                    case 5 -> {
                        System.out.println("Logging out...");
                        return;
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            }
        
    }

    private void manageProperties() {
        Scanner scanner = new Scanner(System.in);
            System.out.println("\n--- Manage Properties ---");
            System.out.println("1. View All Properties");
            System.out.println("2. Search Property by ID");
            System.out.println("3. Archive Property");

            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> viewAllProperties();
                case 2 -> searchPropertyById();
                case 3 -> archiveProperty();
                default -> System.out.println("Invalid choice. Please try again.");
            
        }
    }

    private void viewInterestedBuyers() {
        System.out.println("Viewing interested buyers...");
        System.out.println("This feature is a placeholder and can be extended further.");
    }

    private void prepareContracts() {
        System.out.println("Preparing contracts...");
        System.out.println("Contracts have been prepared for negotiation.");
    }

    private void viewAllProperties() {
        System.out.println("\n--- All Properties ---");
        propertyManager.getProperties().forEach(System.out::println);
    }

    private void searchPropertyById() {
        Scanner scanner = new Scanner(System.in);
            System.out.print("Enter property ID: ");
            Long id = scanner.nextLong();

            propertyManager.getPropertyById(id).ifPresentOrElse(
                    System.out::println,
                    () -> System.out.println("Property not found.")
            );
        
    }

    private void archiveProperty() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter property ID to archive: ");
        Long id = scanner.nextLong();

        propertyManager.archiveProperty(id);
        System.out.println("Property archived.");
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

