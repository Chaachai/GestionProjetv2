package service;

import android.content.Context;

import java.math.BigDecimal;
import java.util.Date;

import bean.Depense;
import bean.Projet;
import bean.Societe;
import dao.DepenseDao;


public class DepenseService extends DepenseDao {

    public DepenseService(Context context) {
        super(context);
    }

    public int create(Projet projet, Societe societe, Date date, BigDecimal montant, String commentaire) {
        Depense depense = new Depense();
        if (projet == null && societe == null) {
            return -1;
        } else {
            depense.setProjet(projet);
            depense.setSociete(societe);
            depense.setDate(date);
            depense.setMontant(montant);
            depense.setCommentaire(commentaire);
            create(depense);
            return 1;
        }
    }
}
