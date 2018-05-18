package service;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import bean.Depense;
import bean.Projet;
import bean.Societe;
import dao.ProjetDao;
import dao.helper.DbStructure;

public class ProjetService extends ProjetDao {
    public ProjetService(Context context) {
        super(context);
    }


    public void deleteBySociete(Societe societe) {
        open();
        getDb().delete(DbStructure.Projet.T_NAME, DbStructure.Projet.C_ID_SOCIETE + "=" + societe.getId(), null);
        close();
    }

    public void removeProjet(Projet projet) {

        //remove tache
        //remove Depense
        //remove projet
        remove(projet);
    }

    public int creerProjet() {
        Projet projet = new Projet();
        projet.setNom("Gestion de Depense");
        projet.setBudget(BigDecimal.valueOf(25321.0));
        projet.setDateDebut(new Date());
        projet.setDescription("blablabla");
        projet.setSociete(new Societe(1l));
        projet.setId(9l);
        create(projet);
        return 1;
    }

}
