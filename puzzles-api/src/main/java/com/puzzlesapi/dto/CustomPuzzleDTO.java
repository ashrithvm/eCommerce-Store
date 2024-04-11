package com.puzzlesapi.dto;

import com.puzzlesapi.model.Account;
import com.puzzlesapi.model.Difficulty;

public class CustomPuzzleDTO {
    private int id;
    private final Account account;
    private final String name;
    private final int quantity;
    private final Difficulty difficulty;
    private final int price;
    private final String imageURL;

    public CustomPuzzleDTO(int id, Account account, String name, int quantity, Difficulty difficulty, int price, String imageURL) {
        this.id = id;
        this.account = account;
        this.name = name;
        this.quantity = quantity;
        this.difficulty = difficulty;
        this.price = price;
        this.imageURL = imageURL;
    }

    public CustomPuzzleDTO(){
        this(0, null, "", 0, Difficulty.EASY, 0, "");
    }
    public int getId() {
        return id;
    }
    public Account getAccount() {
        return account;
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

    public int getPrice() {
        return price;
    }

    public String getImageURL() {
        return imageURL;
    }

    @Override
    public String toString(){
        return String.format("CustomPuzzleDTO{id=%d, account=%s, name=%s, quantity=%d, difficulty=%s, price=%d, imageURL=%s}", id, account, name, quantity, difficulty, price, imageURL);
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof CustomPuzzleDTO c)
            return c.getId() == this.getId() && c.getAccount().equals(this.getAccount()) && c.getName().equals(this.getName()) && c.getQuantity() == this.getQuantity() && c.getDifficulty() == this.getDifficulty() && c.getPrice() == this.getPrice() && c.getImageURL().equals(this.getImageURL());
        return false;
    }
}
