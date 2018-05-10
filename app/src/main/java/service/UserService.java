package service;

import android.content.Context;
import android.util.Log;

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

    public void createUser(User user) {
        User u = new User();
        u.setId(user.getId());
        u.setPassword(user.getPassword());
        u.setLastName(user.getLastName());
        u.setFirstName(user.getFirstName());
        create(u);
    }

}
