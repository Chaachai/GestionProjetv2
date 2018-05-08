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
        return getDb().insert(DbStructure.User.T_NAME, null, contentValues);
    }

    @Override
    public long edit(User user) {
        open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbStructure.User.C_ID, user.getId());
        contentValues.put(DbStructure.User.C_PASSWORD, user.getPassword());
        return db.update(DbStructure.User.T_NAME, contentValues, DbStructure.User.C_ID + " = '" + user.getId() + "'", null);
    }

    protected User transformeCursorToBean(Cursor cursor) {
        return new User(cursor.getString(0), cursor.getString(1));
    }

    public UserDao(Context context) {
        super(context);
        columns = new String[]{DbStructure.User.C_ID, DbStructure.User.C_PASSWORD};
        tableName = DbStructure.User.T_NAME;
        idName = DbStructure.User.C_ID;
    }

}