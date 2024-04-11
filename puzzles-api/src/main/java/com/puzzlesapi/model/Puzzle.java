package com.puzzlesapi.model;



import java.util.logging.Logger;

/**
 * Represents a puzzle product. A puzzle has a name, quantity, description, price, difficulty, and an image URL.
 * The puzzle's id is unique and is used to identify the puzzle. The puzzle's name is also unique.
 */
public class Puzzle extends ModelTemplate implements Comparable<Puzzle>{
    private static final Logger LOG = Logger.getLogger(Puzzle.class.getName());

    //mention every field in the form P[id....] in the string format below
    static final String STRING_FORMAT = "P[%d, %s, %d, %s, %.2f, %s, %s]";


    private String name;
    private int quantity;
    private String description;
    private double price;
    private Difficulty difficulty;
    private String imageURL;

    public Puzzle(int id, String name, int quantity, String description, double price, Difficulty difficulty,
            String imageURL, int accountID) {
        super(id);
        this.name = name;
        this.quantity = quantity;
        this.description = description;
        this.price = price;
        this.difficulty = difficulty;
        this.imageURL = imageURL;
    }
    public Puzzle(int id, String name, int quantity, String description, double price, Difficulty difficulty, String imageURL) {
        this(id, name, quantity, description, price, difficulty, imageURL, -1);
    }
    // Default constructor to satisfy Jackson
    public Puzzle() {
        this(0, "Default", 0, "Default", 0.0, Difficulty.EASY, "Default");
    }
    public Puzzle(String name, double price, int quantity){
        this(0, name, quantity, "", price, Difficulty.EASY, "");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if(quantity < 0)
            throw new IllegalArgumentException("Quantity cannot be negative");
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public String getImageURL() {
        return imageURL;
    }
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public String toString() {
        return String.format(STRING_FORMAT,getId(), name, quantity, description, price, difficulty, imageURL);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Puzzle) {
            Puzzle other = (Puzzle) obj;
            return name.equals(other.name) || getId() == other.getId();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getId();
    }

    @Override
    public int compareTo(Puzzle puzzle) {
        return this.name.compareTo(puzzle.name);
    }
}

