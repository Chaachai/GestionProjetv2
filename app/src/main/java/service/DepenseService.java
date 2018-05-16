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
}
