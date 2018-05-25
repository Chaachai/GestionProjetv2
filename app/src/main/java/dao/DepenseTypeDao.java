package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import bean.DepenseType;
import dao.helper.AbstractDao;
import dao.helper.DbStructure;

public class DepenseTypeDao extends AbstractDao<DepenseType> {

    public DepenseTypeDao(Context context) {
        super(context);
        columns = new String[]{
                DbStructure.DepenseType.C_ID,
                DbStructure.DepenseType.C_NOM,
        };
        tableName = DbStructure.DepenseType.T_NAME;
        idName = DbStructure.DepenseType.C_ID;

    }

    @Override
    public long create(DepenseType depenseType) {
        open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbStructure.DepenseType.C_ID, depenseType.getId());
        contentValues.put(DbStructure.DepenseType.C_NOM, depenseType.getNom());

        return getDb().insert(DbStructure.DepenseType.T_NAME, null, contentValues);
    }

    @Override
    public long edit(DepenseType depenseType) {
        open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbStructure.DepenseType.C_ID, depenseType.getId());
        contentValues.put(DbStructure.DepenseType.C_NOM, depenseType.getNom());

        return getDb().update(DbStructure.DepenseType.T_NAME, contentValues, DbStructure.DepenseType.C_ID + "=" + depenseType.getId(), null);

    }

    @Override
    protected DepenseType transformeCursorToBean(Cursor cursor) {

        return new DepenseType(
                cursor.getLong(cursor.getColumnIndex(DbStructure.DepenseType.C_ID)),
                cursor.getString(cursor.getColumnIndex(DbStructure.DepenseType.C_NOM)));
    }
}
