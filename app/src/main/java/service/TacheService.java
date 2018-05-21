package service;

import android.content.Context;

import java.math.BigDecimal;
import java.util.Date;

import bean.Depense;
import bean.Societe;
import bean.Tache;
import bean.Projet;
import bean.Societe;
import dao.DepenseDao;
import dao.TacheDao;
import helper.Session;
import dao.helper.DbStructure;


public class TacheService extends TacheDao {

    public TacheService(Context context) {
        super(context);
    }

    public void deleteByProjet(Projet projet) {
        open();
        getDb().delete(DbStructure.Tache.T_NAME, DbStructure.Tache.C_ID_PROJET + "=" + projet.getId(), null);
        close();
    }


    public void deleteBySociete(Societe societe) {
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

    public void ajouterTache(Tache tache) {
        Context context = (Context) Session.getAttribut("tacheContext");
        SocieteService societeService = new SocieteService(context);
        if (tache.getProjet().getId() != null) {
            Societe societe = societeService.findByProjet(tache.getProjet());
            tache.setSociete(societe);
            create(tache);
        } else {
            create(tache);
        }

    }
}
