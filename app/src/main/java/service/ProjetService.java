package service;

import android.content.Context;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import bean.Depense;
import bean.Projet;
import bean.Societe;
import dao.ProjetDao;

public class ProjetService extends ProjetDao {
    public ProjetService(Context context) {
        super(context);
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

    public Projet findByDepense(Depense depense) {
        List<Projet> projets = findAll();
        for (int i = 0; i < projets.size(); i++) {
            Projet projet = projets.get(i);
            if (projet.equals(depense.getProjet())) {
                return projet;
            }
        }
        return null;
    }
}
