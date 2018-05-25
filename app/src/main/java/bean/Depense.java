package bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class Depense implements Serializable {

    private static final Long serialVersionUID = 1L;
    private Long id;
    private BigDecimal montant;
    private Date date;
    private String heur;
    private Projet projet = new Projet();
    private Societe societe = new Societe();
    private DepenseType depenseType = new DepenseType();

    public Depense() {
    }

    public Depense(Long id, BigDecimal montant, Date date, String heur, Long idProjet, Long idSociete, Long idDepenseType) {
        this.id = id;
        this.montant = montant;
        this.date = date;
        this.heur = heur;
        projet.setId(idProjet);
        societe.setId(idSociete);
        depenseType.setId(idDepenseType);
    }

    public Depense(Long id, BigDecimal montant, Date date, String heur, Long idProjet, Long idSociete) {
        this.id = id;
        this.montant = montant;
        this.date = date;
        this.heur = heur;
        projet.setId(idProjet);
        societe.setId(idSociete);
    }

    public Depense(Long id, BigDecimal montant, Date date) {
        this.id = id;
        this.montant = montant;
        this.date = date;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public String getHeur() {
        return heur;
    }

    public void setHeur(String heur) {
        this.heur = heur;
    }

    public Projet getProjet() {
        if (projet == null) {
            projet = new Projet();
        }
        return projet;
    }

    public DepenseType getDepenseType() {
        if (depenseType == null) {
            depenseType = new DepenseType();
        }
        return depenseType;
    }

    public void setDepenseType(DepenseType depenseType) {
        this.depenseType = depenseType;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Depense depense = (Depense) o;
        return Objects.equals(id, depense.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Depense{" +
                "id=" + id +
                ", montant=" + montant +
                ", date=" + date +
                ", heur='" + heur + '\'' +
                '}';
    }
}

