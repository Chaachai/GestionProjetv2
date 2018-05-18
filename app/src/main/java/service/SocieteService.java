package service;

import android.content.Context;
import android.util.Log;

import java.util.Date;
import java.util.List;

import bean.Projet;
import bean.Societe;
import dao.SocieteDao;
import dao.helper.DbStructure;
import helper.Session;

public class SocieteService extends SocieteDao {


    public SocieteService(Context context) {
        super(context);
    }

    public void removeSocieteAndProjet(Societe societe) {
        Context context = (Context) Session.getAttribut("ContextSocieteAdapter");
        ProjetService projetService = new ProjetService(context);
        DepenseService depenseService = new DepenseService(context);
        TacheService tacheService = new TacheService(context);

        List<Projet> projets = projetService.findBySociete(societe);
        for (Projet projet : projets) {
            depenseService.deleteByProjet(projet);
            tacheService.deleteByProjet(projet);
        }
        projetService.deleteBySociete(societe);
        depenseService.deleteBySociete(societe);
        tacheService.deleteBySociete(societe);
        remove(societe);
    }


    public int createSociete(Societe societe) {
        create(societe);
        return 1;
    }


//    public void deleteSociete(Societe societe) {
//        Log.d("tag", societe.getRaisonSociale() + " " + societe.getId());
//        remove(societe);
////     return db.delete(DbStructure.Societe.T_NAME, DbStructure.Societe.C_ID + "=?" + societe.getId(), null) > 0;
//    }

    public int create(String raisonSociale, Date dateFondation) {
        Societe societe = new Societe();
        societe.setRaisonSociale(raisonSociale);
        societe.setDateFondation(dateFondation);
        societe.setManager(null);
        create(societe);
        return 1;
    }
}
