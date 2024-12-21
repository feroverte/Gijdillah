package services;

import modules.*;

import java.io.*;
import java.util.*;

public class AuthService {
    private static final String FILE_PATH = "users.json";
    private List<User> users;

    public AuthService() {
        users = new ArrayList<>();
        loadUsers();
    }

    private void loadUsers() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            initializeDefaultUsers();
            saveUsers();
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            StringBuilder json = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                json.append(line);
            }
            parseJson(json.toString());
        } catch (IOException e) {
            System.err.println("Error loading users: " + e.getMessage());
        }
    }

    private void saveUsers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write(toJson());
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }

    private void parseJson(String json) {
        json = json.trim().replace("[", "").replace("]", ""); // Strip brackets
        if (json.isEmpty()) return;

        String[] userStrings = json.split("},\\s*\\{");
        for (String userString : userStrings) {
            userString = "{" + userString.replace("{", "").replace("}", "") + "}";
            User user = parseUser(userString);
            if (user != null) {
                users.add(user);
            }
        }
    }

    private String toJson() {
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            json.append(userToJson(user));
            if (i < users.size() - 1) {
                json.append(",");
            }
        }
        json.append("]");
        return json.toString();
    }

    private String userToJson(User user) {
        return String.format(
                "{\"id\":%d,\"name\":\"%s\",\"email\":\"%s\",\"role\":\"%s\"}",
                user.getId(), user.getName(), user.getEmail(), user.getRole()
        );
    }

    private User parseUser(String json) {
        Map<String, String> fields = parseJsonFields(json);
        Long id = null;
        String name = null, email = null, role = null;

        try {
            id = Long.parseLong(fields.getOrDefault("id", "0"));
            name = fields.getOrDefault("name", null);
            email = fields.getOrDefault("email", null);
            role = fields.getOrDefault("role", null);
        } catch (NumberFormatException e) {
            System.out.println("Error parsing user ID: " + e.getMessage());
        }

        if (id == 0 || name == null || email == null || role == null) {
            System.out.println("Invalid user data: " + json);
            return null;
        }

        return switch (role.toLowerCase()) {
            case "admin" -> new Admin(id, name, email);
            case "buyer" -> new Buyer(id, name, email);
            case "seller" -> new Seller(id, name, email);
            case "agent" -> new Agent(id, name, email);
            default -> {
                System.out.println("Unknown role: " + role);
                yield null;
            }
        };
    }


    private Map<String, String> parseJsonFields(String json) {
        Map<String, String> fields = new HashMap<>();
        String[] pairs = json.replace("{", "").replace("}", "").split(",");
        for (String pair : pairs) {
            String[] keyValue = pair.split(":");
            fields.put(keyValue[0].replace("\"", "").trim(), keyValue[1].replace("\"", "").trim());
        }
        return fields;
    }

    private void initializeDefaultUsers() {
        users.add(new Admin(1L, "Admin User", "admin@example.com"));
        users.add(new Buyer(2L, "Buyer User", "buyer@example.com"));
        users.add(new Seller(3L, "Seller User", "seller@example.com"));
        users.add(new Agent(4L, "Agent User", "agent@example.com"));
    }

    public User login(String email) {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                System.out.println("Login successful for: " + user.getName());
                return user;
            }
        }
        System.out.println("Login failed for email: " + email);
        return null;
    }
}
