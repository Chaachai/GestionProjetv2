package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import bean.Projet;
import dao.helper.AbstractDao;
import dao.helper.DbStructure;

public class ProjetDao extends AbstractDao<Projet> {

    public ProjetDao(Context context) {
        super(context);
        columns = new String[]{
                DbStructure.Projet.C_ID,
                DbStructure.Projet.C_NOM,
                DbStructure.Projet.C_BUDGET,
                DbStructure.Projet.C_DATE,
                DbStructure.Projet.C_DESCRIPTION,
                DbStructure.Projet.C_ID_SOCIETE,
        };
        tableName = DbStructure.Projet.T_NAME;
        idName = DbStructure.Projet.C_ID;
    }

    @Override
    public long create(Projet projet) {
        open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbStructure.Projet.C_ID, projet.getId());
        contentValues.put(DbStructure.Projet.C_NOM, projet.getNom());
        contentValues.put(DbStructure.Projet.C_BUDGET, projet.getBudget().intValue());
        contentValues.put(DbStructure.Projet.C_DESCRIPTION, projet.getDescription());
        contentValues.put(DbStructure.Projet.C_DATE, projet.getDateDebut().getTime());
        contentValues.put(DbStructure.Projet.C_ID_SOCIETE, projet.getSociete().getId());
        return getDb().insert(DbStructure.Projet.T_NAME, null, contentValues);

    }

    @Override
    public long edit(Projet projet) {
        open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbStructure.Projet.C_ID, projet.getId());
        contentValues.put(DbStructure.Projet.C_NOM, projet.getNom());
        contentValues.put(DbStructure.Projet.C_BUDGET, projet.getBudget().intValue());
        contentValues.put(DbStructure.Projet.C_DESCRIPTION, projet.getDescription());
        contentValues.put(DbStructure.Projet.C_DATE, projet.getDateDebut().getTime());
        contentValues.put(DbStructure.Projet.C_ID_SOCIETE, projet.getSociete().getId());
        return getDb().update(DbStructure.Projet.T_NAME, contentValues, DbStructure.Projet.C_ID + "=" + projet.getId(), null);

    }

    public long remove(Projet projet) {
        return db.delete(DbStructure.Projet.T_NAME, DbStructure.Projet.C_ID + "=" + projet.getId(), null);
    }

    protected Projet transformeCursorToBean(Cursor cursor) {
        //budget
        int s = cursor.getInt(cursor.getColumnIndex(DbStructure.Projet.C_BUDGET));
        BigDecimal budget = new BigDecimal(s);


        Long dateFromCursor = cursor.getLong(cursor.getColumnIndex(DbStructure.Projet.C_DATE));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Date dateDebut = new Date(dateFromCursor);
//        try {
//            dateDebut = dateFormat.parse(dateFromCursor);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        return new Projet(
                cursor.getLong(cursor.getColumnIndex(DbStructure.Projet.C_ID)),
                cursor.getString(cursor.getColumnIndex(DbStructure.Projet.C_NOM)),
                cursor.getString(cursor.getColumnIndex(DbStructure.Projet.C_DESCRIPTION)),
                dateDebut,
                budget,
                cursor.getLong(cursor.getColumnIndex(DbStructure.Projet.C_ID_SOCIETE))
        );
    }

}
