package assignment3.modules;

import services.MailService;

public abstract class User {
    private Long id;
    private String name;
    private String email;
    private String role;
    protected MailService mailService; // Shared messaging service

    public User(Long id, String name, String email, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    // Set the shared MailService for messaging functionality
    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    // Getters and setters for the User fields
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // Abstract method to display the menu for the user
    public abstract void showMenu();

    // toString method to provide a string representation of the user
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    public static void add(User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'add'");
    }
}
