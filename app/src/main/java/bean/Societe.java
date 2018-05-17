package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Societe implements Serializable {

    private static final Long serialVersionUID = 1L;
    private Long id;
    private String raisonSociale;
    private Date dateFondation;
    private Manager manager = new Manager();
    private List<Depense> depenses;
    private List<Tache> taches;

    public Societe(Long id, String raisonSociale) {
        this.id = id;
        this.raisonSociale = raisonSociale;
    }

    public Societe(Long id, String raisonSociale, Date dateFondation, Long idManager) {
        this.id = id;
        this.raisonSociale = raisonSociale;
        this.dateFondation = dateFondation;
        manager.setId(idManager);
    }

    public Societe() {
    }

    public List<Tache> getTaches() {
        if (taches == null) {
            taches = new ArrayList<>();
        }
        return taches;
    }

    public void setTaches(List<Tache> taches) {
        this.taches = taches;
    }

    public List<Depense> getDepenses() {
        if (depenses == null) {
            depenses = new ArrayList<>();
        }
        return depenses;
    }

    public void setDepenses(List<Depense> depenses) {
        this.depenses = depenses;
    }

    public Societe(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRaisonSociale() {
        if (raisonSociale == null) {
            raisonSociale = " ";
        }
        return raisonSociale;
    }

    public void setRaisonSociale(String raisonSociale) {
        this.raisonSociale = raisonSociale;
    }

    public Date getDateFondation() {
        if (dateFondation == null) {
            dateFondation = new Date();
        }
        return dateFondation;
    }

    public void setDateFondation(Date dateFondation) {
        this.dateFondation = dateFondation;
    }

    public Manager getManager() {
        if (manager == null) {
            manager = new Manager();
        }
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Societe societe = (Societe) o;
        return Objects.equals(id, societe.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return raisonSociale;
    }
}
