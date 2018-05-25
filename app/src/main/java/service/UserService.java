package service;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.util.Log;

import bean.User;
import dao.UserDao;
import dao.helper.DbStructure;

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

    public int countUser() {
        open();
        int nbrRec = (int) DatabaseUtils.longForQuery(db, " SELECT COUNT(*) FROM user ", null);
        close();
        return nbrRec;
    }

    public void createDefaultUser() {
        User user = new User();
        user.setId("admin");
        user.setPassword("admin");
        user.setLastName("");
        user.setFirstName("");
        create(user);
    }

}
