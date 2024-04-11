package com.puzzlesapi.model;


public class Account extends ModelTemplate implements Comparable<Account>{
    private String username;
    final static String STRING_FORMAT = "Account[id=%d, username='%s']";
    public Account(int id, String username){
        super(id);
        this.username = username.toLowerCase();
    }
    public Account(){
        this(0, "Default");
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return String.format(STRING_FORMAT, getId(), username);
    }
    @Override
    public boolean equals(Object o) {
        if(o instanceof Account){
            Account u = (Account) o;
            return u.getId() == this.getId() && u.username.equals(this.username);
        }
        return false;
    }
    @Override
    public int hashCode() {
        return getId();
    }
    @Override
    public int compareTo(Account account) {

        if (this.username.equals("admin") && !account.username.equals("admin"))
            return 1;
        else if(account.username.equals("admin") && !this.username.equals("admin"))
            return -1;

        return this.username.compareTo(account.username);
    }
}
