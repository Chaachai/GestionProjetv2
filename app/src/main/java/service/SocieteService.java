package service;

import android.content.Context;
import android.util.Log;

import java.util.Date;

import bean.Societe;
import dao.SocieteDao;
import dao.helper.DbStructure;

public class SocieteService extends SocieteDao {


    public SocieteService(Context context) {
        super(context);
    }

    public int createSociete(Societe societe) {
        create(societe);
        return 1;
    }


    public void deleteSociete(Societe societe) {
        Log.d("tag", societe.getRaisonSociale() + " " + societe.getId());
        removeSociete(societe);
//     return db.delete(DbStructure.Societe.T_NAME, DbStructure.Societe.C_ID + "=?" + societe.getId(), null) > 0;
    }

    public int create(String raisonSociale, Date dateFondation) {
        Societe societe = new Societe();
        societe.setRaisonSociale(raisonSociale);
        societe.setDateFondation(dateFondation);
        societe.setManager(null);
        create(societe);
        return 1;
    }
}
