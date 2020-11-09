package de.awitt.Kennwortverwaltung.model;

import javax.persistence.*;

@Entity
@Table(name ="tbl_benutzer")
public class Benutzer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Benutzer_ID")
    private Integer id;

    @Column(name = "Benutzer_Name", length = 100, nullable = false, unique = true)
    private String name;

    public Benutzer(String name) {
        this.name = name;
    }

    public Benutzer() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

}

    /*//Back Reference
    @JsonManagedReference
    @OneToMany(mappedBy = "benutzer", cascade=CascadeType.ALL)
    private Set<BenutzerGruppe> benutzerGruppe;

    public Set<BenutzerGruppe> getBenutzerGruppe() {
        return benutzerGruppe;
    }

    public void setBenutzerGruppe(Set<BenutzerGruppe> benutzerGruppe) {
        this.benutzerGruppe = benutzerGruppe;
    }*/