package modules;

public class Property {
    private Long id; // Unique identifier for the property
    private String name; // Name or title of the property
    private String location; // Location of the property
    private double price; // Price of the property
    private boolean isArchived; // Flag to indicate if the property is archived

    // Constructor
    public Property(Long id, String name, String location, double price, boolean isArchived2) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.price = price;
        this.isArchived = false; // By default, a new property is not archived
    }

    public Property(long timeMillis, String name2, String location2, double price2) {
        //TODO Auto-generated constructor stub
    }

    // Getters and setters
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isArchived() {
        return isArchived;
    }

    public void setArchived(boolean archived) {
        isArchived = archived;
    }

    // toString method for displaying property details
    @Override
    public String toString() {
        return "Property{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", price=" + price +
                ", isArchived=" + isArchived +
                '}';
    }
}
