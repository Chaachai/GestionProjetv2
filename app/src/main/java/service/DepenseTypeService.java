package service;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.Locale;

import bean.DepenseType;
import dao.DepenseTypeDao;
import dao.helper.DbStructure;

public class DepenseTypeService extends DepenseTypeDao {

    public DepenseTypeService(Context context) {
        super(context);
    }


    public int create(String nom) {
        Log.d("depenseService", nom);
        String nomLowerCase = nom.toLowerCase();
        open();
        Cursor mCount = db.rawQuery("SELECT count(*) FROM " + DbStructure.DepenseType.T_NAME + " where " + DbStructure.DepenseType.C_NOM + "='" + nomLowerCase + "'", null);
        mCount.moveToFirst();
        int count = mCount.getInt(0);
        mCount.close();
        if (count == 0) {
            //aucune ligne n'a ce nom
            DepenseType depenseType = new DepenseType();
            depenseType.setNom(nom);
            create(depenseType);
            return 1;
        } else {
            // une ligne existe avec le meme nom
            return -1;
        }
    }


}
