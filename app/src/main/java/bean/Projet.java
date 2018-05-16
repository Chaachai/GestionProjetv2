package bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Projet implements Serializable {
    private static final Long serialVersionUID = 1L;
    private Long id;
    private String nom;
    private String description;
    private Date dateDebut;
    private Societe societe;
    private BigDecimal budget;
    private List<Depense> depenses;
    private List<Tache> taches;


    public Projet() {
    }

    public Projet(Long id) {
        this.id = id;
    }

    public Projet(Long id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public Projet(Long id, String nom, String description, Date dateDebut, BigDecimal budget) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.dateDebut = dateDebut;
        this.budget = budget;
    }

    public Projet(Long id, String nom, String description, BigDecimal budget) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.budget = budget;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        if (nom == null) {
            nom = " ";
        }
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Societe getSociete() {
        if (societe == null) {
            societe = new Societe();
        }
        return societe;
    }

    public void setSociete(Societe societe) {
        this.societe = societe;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    @Override
    public String toString() {
        return nom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Projet projet = (Projet) o;
        return Objects.equals(id, projet.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
