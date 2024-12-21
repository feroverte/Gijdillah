package assignment3.services;


import java.io.*;
import java.util.*;

public class MailService {
    private static final String FILE_PATH = "messages.dat";
    private Map<String, List<String>> mailboxes;

    public MailService() {
        mailboxes = new HashMap<>();
        loadMessages();
    }

    private void loadMessages() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.out.println("No messages found, starting with an empty mailbox.");
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            mailboxes = (Map<String, List<String>>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading messages: " + e.getMessage());
        }
    }

    public void saveMessages() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(mailboxes);
        } catch (IOException e) {
            System.err.println("Error saving messages: " + e.getMessage());
        }
    }

    public void sendMessage(String recipientEmail, String message) {
        mailboxes.computeIfAbsent(recipientEmail, k -> new ArrayList<>()).add(message);
        saveMessages();
        System.out.println("Message sent to " + recipientEmail);
    }

    public List<String> getMessages(String userEmail) {
        return mailboxes.getOrDefault(userEmail, new ArrayList<>());
    }
}
