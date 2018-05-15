package service;

import android.content.Context;

import java.math.BigDecimal;
import java.util.Date;

import bean.Depense;
import dao.DepenseDao;
import dao.TacheDao;


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
}
