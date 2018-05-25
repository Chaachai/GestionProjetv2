package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import bean.Depense;
import dao.helper.AbstractDao;
import dao.helper.DbStructure;

public class DepenseDao extends AbstractDao<Depense> {

    public DepenseDao(Context context) {
        super(context);
        columns = new String[]{
                DbStructure.Depense.C_ID,
                DbStructure.Depense.C_DATE,
                DbStructure.Depense.C_MONTANT,
                DbStructure.Depense.C_HEUR,
                DbStructure.Depense.C_ID_PROJET,
                DbStructure.Depense.C_ID_SOCIETE,
                DbStructure.Depense.C_ID_DEPENSE_TYPE,

        };
        tableName = DbStructure.Depense.T_NAME;
        idName = DbStructure.Depense.C_ID;
    }

    public long remove(Depense depense) {
        open();
        return getDb().delete(DbStructure.Depense.T_NAME, DbStructure.Depense.C_ID + "=" + depense.getId(), null);
    }


    @Override
    public long create(Depense depense) {
        open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbStructure.Depense.C_ID, depense.getId());
        contentValues.put(DbStructure.Depense.C_HEUR, depense.getHeur());
        contentValues.put(DbStructure.Depense.C_DATE, depense.getDate().getTime());
        contentValues.put(DbStructure.Depense.C_MONTANT, depense.getMontant().intValue());
        contentValues.put(DbStructure.Depense.C_ID_PROJET, depense.getProjet().getId());
        contentValues.put(DbStructure.Depense.C_ID_SOCIETE, depense.getSociete().getId());
        contentValues.put(DbStructure.Depense.C_ID_DEPENSE_TYPE, depense.getDepenseType().getId());
        return getDb().insert(DbStructure.Depense.T_NAME, null, contentValues);

    }

    @Override
    public long edit(Depense depense) {
        open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbStructure.Depense.C_ID, depense.getId());
        contentValues.put(DbStructure.Depense.C_HEUR, depense.getHeur());
        contentValues.put(DbStructure.Depense.C_DATE, depense.getDate().getTime());
        contentValues.put(DbStructure.Depense.C_MONTANT, depense.getMontant().intValue());
        contentValues.put(DbStructure.Depense.C_ID_PROJET, depense.getProjet().getId());
        contentValues.put(DbStructure.Depense.C_ID_SOCIETE, depense.getSociete().getId());
        contentValues.put(DbStructure.Depense.C_ID_DEPENSE_TYPE, depense.getDepenseType().getId());
        return getDb().update(DbStructure.Depense.T_NAME, contentValues, DbStructure.Depense.C_ID + "=" + depense.getId(), null);

    }
//    public Depense(Long id, BigDecimal montant, Date date, String commentaire)

    protected Depense transformeCursorToBean(Cursor cursor) {
        //montant
        String s = cursor.getString(cursor.getColumnIndex(DbStructure.Depense.C_MONTANT));
        BigDecimal montant = new BigDecimal(s);
        //date
        Long dateFromCursor = cursor.getLong(cursor.getColumnIndex(DbStructure.Depense.C_DATE));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Date date = new Date(dateFromCursor);
//        try {
//            date = dateFormat.parse(dateFromCursor);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        //
        return new Depense(
                cursor.getLong(cursor.getColumnIndex(DbStructure.Depense.C_ID)),
                montant,
                date,
                cursor.getString(cursor.getColumnIndex(DbStructure.Depense.C_HEUR)),
                cursor.getLong(cursor.getColumnIndex(DbStructure.Depense.C_ID_PROJET)),
                cursor.getLong(cursor.getColumnIndex(DbStructure.Depense.C_ID_SOCIETE)),
                cursor.getLong(cursor.getColumnIndex(DbStructure.Depense.C_ID_DEPENSE_TYPE))
        );
    }


}
