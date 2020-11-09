package de.awitt.Kennwortverwaltung.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.*;

@Entity
@Table(name = "tbl_gruppe")
public class Gruppe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Gruppe_ID")
    private Integer id;

    @JsonProperty("group")
    @Column(name = "Gruppe_Name", length = 100, nullable = false, unique = true)
    private String gruppe;

    public Gruppe(String gruppe) {
        this.gruppe = gruppe;
    }

    public Gruppe() {
    }

    public Integer getId() {
        return id;
    }

    public String getGruppe() {
        return gruppe;
    }

    public void setGruppe(String gruppe) {
        this.gruppe = gruppe;
    }

    /*//Back Reference
    @JsonManagedReference
    @OneToMany(mappedBy = "gruppe", cascade=CascadeType.ALL)
    private Set<BenutzerGruppe> benutzerGruppe;

    public Set<BenutzerGruppe> getBenutzerGruppe() {
        return benutzerGruppe;
    }

    public void setBenutzerGruppe(Set<BenutzerGruppe> benutzerGruppe) {
        this.benutzerGruppe = benutzerGruppe;
    }*/
}
