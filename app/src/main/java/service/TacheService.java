package service;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bean.Tache;
import bean.Projet;
import bean.Societe;
import dao.TacheDao;
import helper.Session;
import dao.helper.DbStructure;


public class TacheService extends TacheDao {

    public TacheService(Context context) {
        super(context);
    }

    public Integer tacheBySociete(Societe societe) {
        open();
        Cursor mCount = getDb().rawQuery("SELECT SUM(nbr_heures) FROM tache WHERE "
                + DbStructure.Tache.C_ID_SOCIETE + " = " + societe.getId(), null);

        mCount.moveToFirst();
        Integer sum = mCount.getInt(0);
        mCount.close();
        return sum;
    }

    public Integer tacheByProjet(Projet projet) {
        open();
        Cursor mCount = getDb().rawQuery("SELECT SUM(nbr_heures) FROM tache WHERE "
                + DbStructure.Tache.C_ID_PROJET + " = " + projet.getId(), null);

        mCount.moveToFirst();
        Integer sum = mCount.getInt(0);
        mCount.close();
        return sum;
    }

    public Integer totalTacheSociete() {
        open();
        Cursor mCount = getDb().rawQuery("SELECT SUM(nbr_heures) FROM " + DbStructure.Tache.T_NAME +
                " where " + DbStructure.Tache.C_ID_SOCIETE + " IS NOT NULL", null);
        mCount.moveToFirst();
        Integer sum = mCount.getInt(0);
        mCount.close();
        return sum;
    }

    public Integer totalTacheProjet() {
        open();
        Cursor mCount = getDb().rawQuery("SELECT SUM(nbr_heures) FROM " + DbStructure.Tache.T_NAME +
                " where " + DbStructure.Tache.C_ID_PROJET + " IS NOT NULL", null);
        mCount.moveToFirst();
        Integer sum = mCount.getInt(0);
        mCount.close();
        return sum;
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

    public List<Tache> findByCriteria(Societe societe, Projet projet, Date dateMin, Date dateMax) {
        open();
        String selection = " 1 = 1 ";
        if (societe != null) {
            selection += " AND " + DbStructure.Tache.C_ID_SOCIETE + " = " + societe.getId();
        }
        if (projet != null) {
            selection += " AND " + DbStructure.Tache.C_ID_PROJET + " = " + projet.getId();
        }
        if (dateMin != null) {
            selection += " AND " + DbStructure.Tache.C_DATE + " >= " + dateMin.getTime();
        }
        if (dateMax != null) {
            selection += " AND " + DbStructure.Tache.C_DATE + " <= " + dateMax.getTime();
        }
        Cursor cursor = db.query(DbStructure.Tache.T_NAME, columns, selection, null, null, null, null);
        List<Tache> taches = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Tache tache = transformeCursorToBean(cursor);
            taches.add(tache);
            cursor.moveToNext();
        }
        return taches;
    }

    public double totalTempsCriteria(List<Tache> taches) {
        double totale = 0.0;
        for (Tache tache : taches) {
            Log.d("tag", "Totale = " + tache.getNbrHeures());
            totale += tache.getNbrHeures();
        }
        return totale;
    }
}
