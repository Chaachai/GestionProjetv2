package bean;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Tache implements Serializable {
    private static final Long serialVersionUID = 1L;
    private Long id;
    private Date date;
    private String heur;
    private double NbrHeures;
    private String commentaire;
    private Projet projet = new Projet();
    private Societe societe = new Societe();

    public Tache() {
    }

    public Tache(Long id, Date date, String heur, double nbrHeures, String commentaire, Long idProjet, Long idSociete) {
        this.id = id;
        this.date = date;
        this.heur = heur;
        this.NbrHeures = nbrHeures;
        this.commentaire = commentaire;
        projet.setId(idProjet);
        societe.setId(idSociete);
    }

    public Tache(Long id, Date date, double nbrHeures, String commentaire) {
        this.id = id;
        this.date = date;
        this.NbrHeures = nbrHeures;
        this.commentaire = commentaire;
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

    public void setProjet(Projet projet) {
        this.projet = projet;
    }

    public Societe getSociete() {
        if (societe == null)
            societe = new Societe();
        return societe;
    }

    public void setSociete(Societe societe) {
        this.societe = societe;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        if(date == null)
            date = new Date();
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public double getNbrHeures() {
        return NbrHeures;
    }

    public void setNbrHeures(double nbrHeures) {
        NbrHeures = nbrHeures;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tache tache = (Tache) o;
        return Objects.equals(id, tache.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Tache{" +
                "id=" + id +
                ", date=" + date +
                ", NbrHeures=" + NbrHeures +
                ", commentaire='" + commentaire + '\'' +
                '}';
    }
}
