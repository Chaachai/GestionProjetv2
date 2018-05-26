package dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import bean.User;
import dao.helper.AbstractDao;
import dao.helper.DbStructure;

public class UserDao extends AbstractDao<User> {

    @Override
    public long create(User user) {
        open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbStructure.User.C_ID, user.getId());
        contentValues.put(DbStructure.User.C_PASSWORD, user.getPassword());
        contentValues.put(DbStructure.User.C_LASTNAME, user.getLastName());
        contentValues.put(DbStructure.User.C_FIRSTNAME, user.getFirstName());
        contentValues.put(DbStructure.User.C_NBRCONNECTION, user.getNbrConnection());
        return getDb().insert(DbStructure.User.T_NAME, null, contentValues);
    }

    @Override
    public long edit(User user) {
        open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbStructure.User.C_ID, user.getId());
        contentValues.put(DbStructure.User.C_PASSWORD, user.getPassword());
        contentValues.put(DbStructure.User.C_LASTNAME, user.getLastName());
        contentValues.put(DbStructure.User.C_FIRSTNAME, user.getFirstName());
        contentValues.put(DbStructure.User.C_NBRCONNECTION, user.getNbrConnection());
        return db.update(DbStructure.User.T_NAME, contentValues, DbStructure.User.C_ID + " = '" + user.getId() + "'", null);
    }

    public long remove(User user) {
        open();
        return db.delete(DbStructure.User.T_NAME, DbStructure.User.C_ID + "=" + user.getId(), null);
    }

    protected User transformeCursorToBean(Cursor cursor) {
        return new User(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4));
    }

    public UserDao(Context context) {
        super(context);
        columns = new String[]{
                DbStructure.User.C_ID,
                DbStructure.User.C_PASSWORD,
                DbStructure.User.C_LASTNAME,
                DbStructure.User.C_FIRSTNAME,
                DbStructure.User.C_NBRCONNECTION,
        };
        tableName = DbStructure.User.T_NAME;
        idName = DbStructure.User.C_ID;
    }

}