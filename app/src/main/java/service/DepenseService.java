package service;

import android.content.Context;
import android.util.Log;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import bean.Depense;
import bean.Projet;
import bean.Societe;
import dao.DepenseDao;
import dao.helper.DbStructure;


public class DepenseService extends DepenseDao {

    public DepenseService(Context context) {
        super(context);
    }




    public void deleteByProjet(Projet projet) {
//        getDb().execSQL("DELETE FROM "+ DbStructure.Tache.T_NAME+" WHERE ");
        open();
        getDb().delete(DbStructure.Depense.T_NAME, DbStructure.Depense.C_ID_PROJET + "=" + projet.getId(), null);
        close();
    }

    public void deleteBySociete(Societe societe) {
//        getDb().execSQL("DELETE FROM "+ DbStructure.Tache.T_NAME+" WHERE ");
        open();
        getDb().delete(DbStructure.Depense.T_NAME, DbStructure.Depense.C_ID_SOCIETE + "=" + societe.getId(), null);
        close();
    }


    public int create(Date date, BigDecimal montant, String commentaire) {
        Depense depense = new Depense();
        depense.setProjet(null);
        depense.setSociete(null);
        depense.setDate(date);
        depense.setMontant(montant);
        depense.setCommentaire(commentaire);
        create(depense);
        return 1;
    }
}
