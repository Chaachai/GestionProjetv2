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
        mCount.close();
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
        mCount.close();
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
        mCount.close();
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
        mCount.close();
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

    public List<Depense> findDepenseBySociete(Societe societe) {
        open();
        Cursor cursor = db.query(DbStructure.Depense.T_NAME, columns, DbStructure.Depense.C_ID_SOCIETE + "=" + societe.getId(), null, null, null, null);
        List<Depense> depenses = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Depense depense = transformeCursorToBean(cursor);
            depenses.add(depense);
            cursor.moveToNext();
        }
        return depenses;
    }

    public List<Depense> findByCriteria(Societe societe, Projet projet, Date dateMin, Date dateMax) {
        open();
        String selection = " 1 = 1 ";
        if (societe != null) {
            selection += " AND " + DbStructure.Depense.C_ID_SOCIETE + " = " + societe.getId();
        }
        if (projet != null) {
            selection += " AND " + DbStructure.Depense.C_ID_PROJET + " = " + projet.getId();
        }
        if (dateMin != null) {
            selection += " AND " + DbStructure.Depense.C_DATE + " >= " + dateMin.getTime();
        }
        if (dateMax != null) {
            selection += " AND " + DbStructure.Depense.C_DATE + " <= " + dateMax.getTime();
        }
        Cursor cursor = db.query(DbStructure.Depense.T_NAME, columns, selection, null, null, null, null);
        List<Depense> depenses = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Depense depense = transformeCursorToBean(cursor);
            depenses.add(depense);
            cursor.moveToNext();
        }
        return depenses;
    }

    public BigDecimal totalDepenseCriteria(List<Depense> depenses) {
        BigDecimal totale = BigDecimal.ZERO;
        BigDecimal montant;
        for (Depense depense : depenses) {
            Log.d("tag", "Totale = " + depense.getMontant());
            totale = totale.add(depense.getMontant());
        }
        return totale;
    }
}
