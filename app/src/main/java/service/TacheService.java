package service;

import android.content.Context;

import java.math.BigDecimal;
import java.util.Date;

import bean.Depense;
import bean.Projet;
import bean.Societe;
import dao.DepenseDao;
import dao.TacheDao;
import dao.helper.DbStructure;


public class TacheService extends TacheDao {

    public TacheService(Context context) {
        super(context);
    }

    public void deleteByProjet(Projet projet) {
//        getDb().execSQL("DELETE FROM "+ DbStructure.Tache.T_NAME+" WHERE ");
        open();
        getDb().delete(DbStructure.Tache.T_NAME, DbStructure.Tache.C_ID_PROJET + "=" + projet.getId(), null);
        close();
    }


    public void deleteBySociete(Societe societe) {
//        getDb().execSQL("DELETE FROM "+ DbStructure.Tache.T_NAME+" WHERE ");
        open();
        getDb().delete(DbStructure.Tache.T_NAME, DbStructure.Tache.C_ID_SOCIETE + "=" + societe.getId(), null);
        close();
    }

//    public int create(Date date, BigDecimal montant, String commentaire) {
//        Depense depense = new Depense();
//        depense.setProjet(null);
//        depense.setSociete(null);
//        depense.setDate(date);
//        depense.setMontant(montant);
//        depense.setCommentaire(commentaire);
//        create(depense);
//        return 1;
//    }
}
