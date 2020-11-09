package de.awitt.Kennwortverwaltung.model;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;

@Entity
@Table(name = "tbl_benutzer_gruppe")
public class BenutzerGruppe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Benutzer_Gruppe_ID")
    private Integer id;

    @JsonProperty("group")
    @ManyToOne
    @JoinColumn(name = "Gruppe_ID")
    private Gruppe gruppe;

    @JsonProperty("user")
    @ManyToOne
    @JoinColumn(name = "Benutzer_ID", nullable = false)
    private Benutzer benutzer;

    public BenutzerGruppe(Gruppe gruppe, Benutzer benutzer) {
        this.gruppe = gruppe;
        this.benutzer = benutzer;
    }

    public BenutzerGruppe() {
    }

    public Integer getId() {
        return id;
    }

    public Gruppe getGruppe() {
        return gruppe;
    }

    public void setGruppe(Gruppe gruppe) {
        this.gruppe = gruppe;
    }

    public Benutzer getBenutzer() {
        return benutzer;
    }

    public void setBenutzer(Benutzer benutzer) {
        this.benutzer = benutzer;
    }

    /*//Back Reference
    @JsonManagedReference
    @OneToMany(mappedBy = "benutzerGruppe", cascade=CascadeType.ALL)
    private Set<Passwort> passwortSet;

    public Set<Passwort> getPasswortSet() {
        return passwortSet;
    }

    public void setPasswortSet(Set<Passwort> passwortSet) {
        this.passwortSet = passwortSet;
    }*/
}
