package assignment3.services;


import modules.Property;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PropertyManager {
    private static final String FILE_PATH = "properties.json"; // Path to the JSON file
    private List<Property> properties; // List to store property data

    public PropertyManager() {
        properties = new ArrayList<>();
        loadProperties(); // Load properties from the JSON file
    }

    public List<Property> getProperties() {
        return properties;
    }

    // Load properties from the file
    private void loadProperties() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.out.println("No properties found, starting with an empty list.");
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
            e.printStackTrace();
        }
    }

    // Save the properties to a file
    public void saveProperties() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write(toJson());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Parse JSON string into Property objects
    private void parseJson(String json) {
        json = json.trim();
        if (json.startsWith("[") && json.endsWith("]")) {
            json = json.substring(1, json.length() - 1); // Remove brackets
            String[] propertyStrings = json.split("},\\s*\\{");
            for (String propertyString : propertyStrings) {
                propertyString = "{" + propertyString + "}";
                Property property = parseProperty(propertyString);
                if (property != null) {
                    properties.add(property);
                }
            }
        }
    }

    // Convert Property list to JSON string
    private String toJson() {
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < properties.size(); i++) {
            Property property = properties.get(i);
            json.append(propertyToJson(property));
            if (i < properties.size() - 1) {
                json.append(",");
            }
        }
        json.append("]");
        return json.toString();
    }

    // Convert a single Property to JSON
    private String propertyToJson(Property property) {
        return String.format(
                "{\"id\":%d,\"name\":\"%s\",\"location\":\"%s\",\"price\":%.2f,\"isArchived\":%b}",
                property.getId(), property.getName(), property.getLocation(), property.getPrice(), property.isArchived()
        );
    }

    // Parse a single Property from JSON
    private Property parseProperty(String json) {
        String[] fields = json.replace("{", "").replace("}", "").split(",\\s*");
        Long id = null;
        String name = null, location = null;
        double price = 0.0;
        boolean isArchived = false;

        for (String field : fields) {
            String[] keyValue = field.split(":", 2); // Ensure only two parts
            if (keyValue.length < 2) {
                System.out.println("Skipping invalid field: " + field);
                continue;
            }

            String key = keyValue[0].replace("\"", "").trim();
            String value = keyValue[1].replace("\"", "").trim();

            try {
                switch (key) {
                    case "id" -> id = Long.parseLong(value);
                    case "name" -> name = value;
                    case "location" -> location = value;
                    case "price" -> price = Double.parseDouble(value);
                    case "isArchived" -> isArchived = Boolean.parseBoolean(value);
                    default -> System.out.println("Unknown key: " + key);
                }
            } catch (NumberFormatException e) {
                System.out.println("Error parsing field: " + key + " with value: " + value);
            }
        }

        if (id == null || name == null || location == null || price == 0.0) {
            System.out.println("Invalid property data, skipping entry.");
            return null;
        }

        return new Property(id, name, location, price, isArchived);
    }


    // Add a new property
    public void addProperty(Property property) {
        properties.add(property);
        saveProperties();
    }

    // Edit an existing property
    public void editProperty(Long id, String name, String location, double price) {
        getPropertyById(id).ifPresent(property -> {
            property.setName(name);
            property.setLocation(location);
            property.setPrice(price);
            saveProperties();
        });
    }

    // Archive a property
    public void archiveProperty(Long id) {
        getPropertyById(id).ifPresent(property -> {
            property.setArchived(true);
            saveProperties();
        });
    }

    // Get a property by ID
    public Optional<Property> getPropertyById(Long id) {
        return properties.stream().filter(property -> property.getId().equals(id)).findFirst();
    }
}
