package models;

import java.util.HashMap;
import java.util.Map;

public class User {
    String userId;
    String name;
    String email;
    Map<String, Float> balances = new HashMap<>();

    public User(String userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    public Map<String, Float> getBalances() {
        return balances;
    }

    public void setBalances(Map<String, Float> balances) {
        this.balances = balances;
    }

    public String getName() {
        return name;
    }

}
