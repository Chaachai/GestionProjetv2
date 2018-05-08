package bean;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Societe implements Serializable {

    private static final Long serialVersionUID = 1L;
    private Long id;
    private String raisonSociale;
    private Date dateFondation;
    private Manager manager;

    public Societe(Long id, String raisonSociale) {
        this.id = id;
        this.raisonSociale = raisonSociale;
    }

    public Societe(Long id, String raisonSociale, Date dateFondation) {
        this.id = id;
        this.raisonSociale = raisonSociale;
        this.dateFondation = dateFondation;
    }

    public Societe() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRaisonSociale() {
        return raisonSociale;
    }

    public void setRaisonSociale(String raisonSociale) {
        this.raisonSociale = raisonSociale;
    }

    public Date getDateFondation() {
        return dateFondation;
    }

    public void setDateFondation(Date dateFondation) {
        this.dateFondation = dateFondation;
    }

    public Manager getManager() {
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
        return "Societe{" +
                "id=" + id +
                ", raisonSociale='" + raisonSociale + '\'' +
                ", dateFondation=" + dateFondation +
                '}';
    }
}
