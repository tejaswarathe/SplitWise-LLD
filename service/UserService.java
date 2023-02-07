package service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import data.Data;
import models.User;
import models.UserList;

public class UserService {

    UserList userList = Data.userList;

    public void addUser(String userId, String name, String email) {
        User newUser = new User(userId, name, email);
        Set<String> previousUsers = fetchAllUsers();
        Map<String, Float> newUserBalances = new HashMap<>();
        // add 0 balances for previous users
        // add 0 balances for new user
        for (String previousUserId : previousUsers) {
            User previousUser = userList.getUserMap().get(previousUserId);
            previousUser.getBalances().put(userId, (float) 0);
        }

        for (String id : previousUsers) {
            newUserBalances.put(id, (float) 0);
        }
        newUser.setBalances(newUserBalances);
        userList.getUserMap().put(userId, newUser);
    }

    private Set<String> fetchAllUsers() {
        return userList.getUserMap().keySet();
    }
}
