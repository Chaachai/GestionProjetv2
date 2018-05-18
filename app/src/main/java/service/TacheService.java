package service;

import android.content.Context;

import java.math.BigDecimal;
import java.util.Date;

import bean.Depense;
import bean.Societe;
import bean.Tache;
import dao.DepenseDao;
import dao.TacheDao;
import helper.Session;


public class TacheService extends TacheDao {

    public TacheService(Context context) {
        super(context);
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
