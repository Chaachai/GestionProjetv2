package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import bean.Tache;
import dao.helper.AbstractDao;
import dao.helper.DbStructure;

public class TacheDao extends AbstractDao<Tache> {
    public TacheDao(Context context) {
        super(context);
        columns = new String[]{
                DbStructure.Tache.C_ID,
                DbStructure.Tache.C_DATE,
                DbStructure.Tache.C_NBRHEURES,
                DbStructure.Tache.C_COMMENTAIRE,
        };
        tableName = DbStructure.Tache.T_NAME;
        idName = DbStructure.Tache.C_ID;
    }

    public long remove(Tache tache) {
        return db.delete(DbStructure.Tache.T_NAME, DbStructure.Projet.C_ID + "=" + tache.getId(), null);
    }

    @Override
    public long create(Tache tache) {
        open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbStructure.Tache.C_ID, tache.getId());
        contentValues.put(DbStructure.Tache.C_DATE, tache.getDate().getTime());
        contentValues.put(DbStructure.Tache.C_NBRHEURES, tache.getNbrHeures());
        contentValues.put(DbStructure.Tache.C_COMMENTAIRE, tache.getCommentaire());
        return getDb().insert(DbStructure.Tache.T_NAME, null, contentValues);

    }

    @Override
    public long edit(Tache tache) {
        open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbStructure.Tache.C_ID, tache.getId());
        contentValues.put(DbStructure.Tache.C_DATE, tache.getDate().getTime());
        contentValues.put(DbStructure.Tache.C_NBRHEURES, tache.getNbrHeures());
        contentValues.put(DbStructure.Tache.C_COMMENTAIRE, tache.getCommentaire());
        return getDb().update(DbStructure.Tache.T_NAME, contentValues, DbStructure.Tache.C_ID + "=" + tache.getId(), null);

    }


    protected Tache transformeCursorToBean(Cursor cursor) {
//    public Tache(Long id, Date date, int nbrHeures, String commentaire) {

        //date
        Long dateFromCursor = cursor.getLong(cursor.getColumnIndex(DbStructure.Tache.C_DATE));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Date date = new Date(dateFromCursor);
//        try {
//            date = dateFormat.parse(dateFromCursor);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        //
        return new Tache(
                cursor.getLong(cursor.getColumnIndex(DbStructure.Tache.C_ID)),
                date,
                cursor.getInt(cursor.getColumnIndex(DbStructure.Tache.C_NBRHEURES)),
                cursor.getString(cursor.getColumnIndex(DbStructure.Tache.C_COMMENTAIRE)));
    }

}
