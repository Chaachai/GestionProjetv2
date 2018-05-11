package service;

import android.content.Context;

import java.util.Date;

import bean.Societe;
import dao.SocieteDao;

public class SocieteService extends SocieteDao {

    public SocieteService(Context context) {
        super(context);
    }

    public int createSociete(Societe societe) {
        create(societe);
        return 1;
    }

    public int create(String raisonSociale, Date dateFondation) {
        Societe societe = new Societe();
        societe.setRaisonSociale(raisonSociale);
        societe.setDateFondation(dateFondation);
        societe.setManager(null);
        create(societe);
        return 1;
    }
}
