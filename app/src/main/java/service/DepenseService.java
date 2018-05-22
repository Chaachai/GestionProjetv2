package service;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bean.Depense;
import bean.Projet;
import bean.Societe;
import dao.DepenseDao;
import dao.helper.DbStructure;
import helper.Session;


public class DepenseService extends DepenseDao {

    public DepenseService(Context context) {
        super(context);
    }

    public List<Depense> findBySociete(Societe societe) {
        List<Depense> depensesBySociete = new ArrayList<>();
        List<Depense> depenses = findAll();
        for (Depense depense : depenses) {
            if (depense.getSociete() != null && depense.getSociete().getId().equals(societe.getId())) {
                depensesBySociete.add(depense);
            }
        }
        return depensesBySociete;
    }

    public BigDecimal totalDepense() {
        open();
        Cursor mCount = getDb().rawQuery("SELECT SUM(montant) FROM " + DbStructure.Depense.T_NAME + " where " + DbStructure.Depense.C_ID_SOCIETE + " IS NOT NULL", null);
        mCount.moveToFirst();
        String s = mCount.getString(0);
        BigDecimal montant;
        if (s == null) {
            montant = BigDecimal.ZERO;
        } else {
            montant = new BigDecimal(s);
        }
        close();
        return montant;
    }

    public BigDecimal totalDepenseProjet() {
        open();
        Cursor mCount = getDb().rawQuery("SELECT SUM(montant) FROM depense where " + DbStructure.Depense.C_ID_PROJET + " IS NOT NULL", null);
        mCount.moveToFirst();
        String s = mCount.getString(0);
        BigDecimal montant;
        Log.d("depenseservice", s + "");
        if (s == null) {
            montant = BigDecimal.ZERO;
        } else {
            montant = new BigDecimal(s);
        }
        close();
        return montant;
    }

    public BigDecimal depenseBySociete(Societe societe) {
        open();
        Cursor mCount = getDb().rawQuery("SELECT SUM(montant) FROM depense where " + DbStructure.Depense.C_ID_SOCIETE + "=" + societe.getId(), null);
        mCount.moveToFirst();
        String s = mCount.getString(0);
        BigDecimal montant;
        Log.d("depenseservice", s + "");
        if (s == null) {
            montant = BigDecimal.ZERO;
        } else {
            montant = new BigDecimal(s);
        }
        Log.d("tag montant", "" + montant);
        close();
        return montant;
    }

    public BigDecimal depenseByProjet(Projet projet) {
        open();
        Cursor mCount = getDb().rawQuery("SELECT SUM(montant) FROM depense where " + DbStructure.Depense.C_ID_PROJET + "=" + projet.getId(), null);
        mCount.moveToFirst();
        String s = mCount.getString(0);
        BigDecimal montant;
        Log.d("depenseservice", s + "");
        if (s == null) {
            montant = BigDecimal.ZERO;
        } else {
            montant = new BigDecimal(s);
        }
        Log.d("tag montant", "" + montant);
        close();
        return montant;
    }

    public void deleteByProjet(Projet projet) {
        open();
        getDb().delete(DbStructure.Depense.T_NAME, DbStructure.Depense.C_ID_PROJET + "=" + projet.getId(), null);
        close();
    }

    public void deleteBySociete(Societe societe) {
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
        Session.delete("depenseContext");
    }
}
