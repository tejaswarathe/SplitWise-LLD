package models;

import java.util.HashMap;
import java.util.Map;

public class UserList {
    public Map<String, User> userMap = new HashMap<>();

    public Map<String, User> getUserMap() {
        return userMap;
    }

    public void setUserMap(Map<String, User> userMap) {
        this.userMap = userMap;
    }

}
