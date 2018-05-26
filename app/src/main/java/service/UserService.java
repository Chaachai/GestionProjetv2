package service;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.util.Log;

import bean.User;
import dao.UserDao;
import dao.helper.DbStructure;
import helper.Session;

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

//    public void createUser(User user) {
//        User u = new User();
//        u.setId(user.getId());
//        u.setPassword(user.getPassword());
//        u.setLastName(user.getLastName());
//        u.setFirstName(user.getFirstName());
//        u.setNbrConnection(user.getNbrConnection());
//        create(u);
//    }

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
        user.setNbrConnection(0);
        create(user);
    }

    public int changePassword(String oldPassword, String newPassword, String confirmPassword) {
        User user = (User) Session.getAttribut("connectedUser");
        Log.d("tag", "Username ========= " + user.getId());
        Log.d("tag", "password ========= " + user.getPassword());
        String userPassword = user.getPassword();
        if (!oldPassword.equals(userPassword)) {
            return -1;
        } else if (!newPassword.equals(confirmPassword)) {
            return -2;
        } else {
            user.setPassword(newPassword);
            edit(user);
            return 1;
        }
    }

    public void editProfile(String nom, String prenom){
        User user = (User) Session.getAttribut("connectedUser");
        user.setLastName(nom);
        user.setFirstName(prenom);
        edit(user);
    }

}
