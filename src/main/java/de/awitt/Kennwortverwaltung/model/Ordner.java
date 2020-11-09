package de.awitt.Kennwortverwaltung.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.*;

@Entity
@Table(name = "tbl_ordner")
public class Ordner {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Ordner_ID")
    private Integer id;

    @JsonProperty("path")
    @Column(name = "Pfad", nullable = false)
    private String pfad;

    public Ordner(String pfad) {
        this.pfad = pfad;
    }

    public Ordner() {
    }

    public Integer getId() {
        return id;
    }

    public String getPfad() {
        return pfad;
    }

    public void setPfad(String pfad) {
        this.pfad = pfad;
    }

    /*//Back Reference
    @JsonManagedReference
    @OneToMany(mappedBy = "ordner", cascade=CascadeType.ALL)
    private Set<Passwort> passwortSet;

    public Set<Passwort> getPasswortSet() {
        return passwortSet;
    }

    public void setPasswortSet(Set<Passwort> passwortSet) {
        this.passwortSet = passwortSet;
    }*/
}
