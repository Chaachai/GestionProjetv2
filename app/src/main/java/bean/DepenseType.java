package bean;

import java.io.Serializable;
import java.util.Objects;

public class DepenseType implements Serializable {


    private static final Long serialVersionUID = 1L;
    private Long id;
    private String nom;

    public DepenseType(Long id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public DepenseType() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepenseType that = (DepenseType) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "DepenseType{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                '}';
    }
}
