package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import bean.Societe;
import dao.helper.AbstractDao;
import dao.helper.DbStructure;

public class SocieteDao extends AbstractDao<Societe> {

    public SocieteDao(Context context) {
        super(context);
        columns = new String[]{DbStructure.Societe.C_ID, DbStructure.Societe.C_DATE,
                DbStructure.Societe.C_RAISONSOCIALE, DbStructure.Societe.C_ID_MANAGER};
        tableName = DbStructure.Societe.T_NAME;
        idName = DbStructure.Societe.C_ID;
    }

    @Override
    public long create(Societe societe) {
        open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbStructure.Societe.C_ID, societe.getId());
        contentValues.put(DbStructure.Societe.C_DATE, societe.getDateFondation().getTime());
        contentValues.put(DbStructure.Societe.C_RAISONSOCIALE, societe.getRaisonSociale());
        contentValues.put(DbStructure.Societe.C_ID_MANAGER, societe.getManager().getId());
        return getDb().insert(DbStructure.Societe.T_NAME, null, contentValues);
    }

    @Override
    public long edit(Societe societe) {
        open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbStructure.Societe.C_ID, societe.getId());
        contentValues.put(DbStructure.Societe.C_DATE, societe.getDateFondation().toString());
        contentValues.put(DbStructure.Societe.C_RAISONSOCIALE, societe.getRaisonSociale());
        contentValues.put(DbStructure.Societe.C_ID_MANAGER, societe.getManager().getId());
        return getDb().update(DbStructure.Societe.T_NAME, contentValues, DbStructure.Societe.C_ID + "=" + societe.getId(), null);

    }


    public long removeSociete(Societe societe) {
        return getDb().delete(DbStructure.Societe.T_NAME, DbStructure.Societe.C_ID + "=" + societe.getId(), null);
    }


    protected Societe transformeCursorToBean(Cursor cursor) {

        Long dateFromCursor = cursor.getLong(cursor.getColumnIndex(DbStructure.Societe.C_DATE));

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date dateFondation = new Date(dateFromCursor);
//        try {
//            dateFondation = dateFormat.parse(dateFromCursor);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }



        return new Societe(
                cursor.getLong(cursor.getColumnIndex(DbStructure.Societe.C_ID)),
                cursor.getString(cursor.getColumnIndex(DbStructure.Societe.C_RAISONSOCIALE)),
                dateFondation
        );
//        return new Societe(cursor.getString(0), cursor.getString(1));
    }
}
