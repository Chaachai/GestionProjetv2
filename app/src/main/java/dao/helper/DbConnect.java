package dao.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbConnect extends SQLiteOpenHelper {


    public DbConnect(Context context) {
        super(context, DbStructure.dbName, null, DbStructure.DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DbStructure.User.SQL_CREATE);
        db.execSQL(DbStructure.Societe.SQL_CREATE);
        db.execSQL(DbStructure.Manager.SQL_CREATE);
        db.execSQL(DbStructure.Depense.SQL_CREATE);
        db.execSQL(DbStructure.Tache.SQL_CREATE);
        db.execSQL(DbStructure.Projet.SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DbStructure.User.SQL_DROP);
        db.execSQL(DbStructure.Societe.SQL_DROP);
        db.execSQL(DbStructure.Manager.SQL_DROP);
        db.execSQL(DbStructure.Depense.SQL_DROP);
        db.execSQL(DbStructure.Tache.SQL_DROP);
        db.execSQL(DbStructure.Projet.SQL_DROP);
        onCreate(db);
    }


}
