package service;

import android.content.Context;

import bean.User;
import dao.UserDao;

/**
 * Created by CHAACHAI Youssef on 9/4/2018.
 */

public class UserService extends UserDao {
    public UserService(Context context) {
        super(context);
    }

    public int connect(User user) {
        User loadedUser = find(user.getId());
        if (loadedUser == null)
            return -1;
        if (loadedUser.getPassword().equals(user.getPassword())) {
            return 1;
        }
        return -2;
    }

    public void createUser(String username, String password) {
        User user = new User();
        user.setId(username);
        user.setPassword(password);
        create(user);
    }

    public int testConnect(String username, String password) {
        if (username.equals("chaachai") && password.equals("8950")) {
            return 1;
        } else {
            return -1;
        }
    }

}
