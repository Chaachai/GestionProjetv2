package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import bean.Manager;
import dao.helper.AbstractDao;
import dao.helper.DbStructure;

public class ManagerDao extends AbstractDao<Manager> {

    public ManagerDao(Context context) {
        super(context);
        columns = new String[]{DbStructure.Manager.C_ID,
                DbStructure.Manager.C_NOM,
                DbStructure.Manager.C_PRENOM};
        tableName = DbStructure.Manager.T_NAME;
        idName = DbStructure.Manager.C_ID;
    }

    @Override
    public long create(Manager manager) {
        open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbStructure.Manager.C_ID, manager.getId());
        contentValues.put(DbStructure.Manager.C_NOM, manager.getNom());
        contentValues.put(DbStructure.Manager.C_PRENOM, manager.getPrenom());
        return getDb().insert(DbStructure.Manager.T_NAME, null, contentValues);

    }

    @Override
    public long edit(Manager manager) {
        open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbStructure.Manager.C_ID, manager.getId());
        contentValues.put(DbStructure.Manager.C_NOM, manager.getNom());
        contentValues.put(DbStructure.Manager.C_PRENOM, manager.getPrenom());

        return getDb().update(DbStructure.Manager.T_NAME, contentValues, DbStructure.Manager.C_ID + "=" + manager.getId(), null);

    }

    public long remove(Manager manager) {
        return db.delete(DbStructure.Societe.T_NAME, DbStructure.Societe.C_ID + "=" + manager.getId(), null);
    }

    protected Manager transformeCursorToBean(Cursor cursor) {
        return new Manager(cursor.getLong(0), cursor.getString(1), cursor.getString(2));
    }
}
