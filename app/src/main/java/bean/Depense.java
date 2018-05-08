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
    private String commentaire;

    public Depense() {
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

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
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
                ", commentaire='" + commentaire + '\'' +
                '}';
    }
}

