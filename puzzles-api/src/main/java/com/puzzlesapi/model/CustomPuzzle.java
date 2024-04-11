package com.puzzlesapi.model;

public class CustomPuzzle extends ModelTemplate{
    private final int accountID;
    private final String name;
    private final int quantity;
    private final Difficulty difficulty;
    private final int price;
    private final String imageURL;

    private int setPriceBasedOnDifficulty(Difficulty difficulty) {
        return switch (difficulty) {
            case EASY -> 50;
            case MEDIUM -> 70;
            case HARD -> 100;
            default -> 0;
        };
    }

    public CustomPuzzle(int id, int accountID, String name, int quantity, Difficulty difficulty, String imageURL) {
        super(id);
        this.accountID = accountID;
        this.name = name;
        this.quantity = quantity;
        this.difficulty = difficulty;
        this.imageURL = imageURL;
        this.price = setPriceBasedOnDifficulty(difficulty);
    }

    public int getAccountID() {
        return accountID;
    }
    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public String getImageURL() {
        return imageURL;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString(){
        return String.format("CustomPuzzle{id=%d, accountID=%d, name=%s, quantity=%d, difficulty=%s, price=%d, imageURL=%s}", getId(), accountID, name, quantity, difficulty, price, imageURL);
    }

    @Override
    public boolean equals(Object obj){
        return obj instanceof CustomPuzzle c && c.getId() == this.getId();
    }

    @Override
    public int hashCode(){
        return getId();
    }

}
