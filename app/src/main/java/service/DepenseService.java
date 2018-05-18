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
import helper.Session;


public class DepenseService extends DepenseDao {

    public DepenseService(Context context) {
        super(context);
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

    public void ajouterDepense(Depense depense) {
        Context context = (Context) Session.getAttribut("depenseContext");
        SocieteService societeService = new SocieteService(context);
        if (depense.getProjet().getId() != null) {
            Societe societe = societeService.findByProjet(depense.getProjet());
            depense.setSociete(societe);
            create(depense);
        } else {
            create(depense);
        }

    }
}
