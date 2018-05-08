package bean;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Tache implements Serializable {
    private static final Long serialVersionUID = 1L;
    private Long id;
    private Date date;
    private int NbrHeures;
    private String commentaire;

    public Tache() {
    }

    public Tache(Long id, Date date, int nbrHeures, String commentaire) {
        this.id = id;
        this.date = date;
        NbrHeures = nbrHeures;
        this.commentaire = commentaire;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
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

    public int getNbrHeures() {
        return NbrHeures;
    }

    public void setNbrHeures(int nbrHeures) {
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
