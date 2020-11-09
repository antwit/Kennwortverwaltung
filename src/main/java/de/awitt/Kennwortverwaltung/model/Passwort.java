package de.awitt.Kennwortverwaltung.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "tbl_passwort")
public class Passwort {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Passwort_ID")
    private Integer id;

    @JsonProperty("user_group")
    @ManyToOne
    @JoinColumn(name = "Benutzer_Gruppe_ID", nullable = false)
    private BenutzerGruppe benutzerGruppe;

    @JsonProperty("directory")
    @ManyToOne
    @JoinColumn(name = "Ordner_ID", nullable = false)
    private Ordner ordner;

    @JsonProperty("date_create")
    @CreationTimestamp
    @Column(name = "Datum_Create")
    private Timestamp dateCreate;

    @JsonProperty("date_change")
    @UpdateTimestamp
    @Column(name = "Datum_Change")
    private Timestamp dateChange;

    @JsonProperty("date_expire")
    @Column(name = "Datum_Expire")
    private Timestamp dateExpire;

    @Column(name = "Notiz", length = 65535)
    private String note;

    @Column(name = "Title", nullable = false)
    private String title;

    @Column(name = "URL")
    private String url;

    @Column(name = "Passwort", length = 65535, nullable = false)
    private String password;

    @Column(name = "Benutzername", nullable = false)
    private String username;

    public Passwort(BenutzerGruppe benutzerGruppe, Ordner ordner, Timestamp dateExpire, String note, String title, String url, String password, String username) {
        this.benutzerGruppe = benutzerGruppe;
        this.ordner = ordner;
        this.dateExpire = dateExpire;
        this.note = note;
        this.title = title;
        this.url = url;
        this.password = password;
        this.username = username;
    }

    public Passwort(BenutzerGruppe benutzerGruppe, Ordner ordner, String title, String password, String username) {
        this.benutzerGruppe = benutzerGruppe;
        this.ordner = ordner;
        this.title = title;
        this.password = password;
        this.username = username;
    }

    public Passwort() {
    }

    public Integer getId() {
        return id;
    }

    public BenutzerGruppe getBenutzerGruppe() {
        return benutzerGruppe;
    }

    public void setBenutzerGruppe(BenutzerGruppe benutzerGruppe) {
        this.benutzerGruppe = benutzerGruppe;
    }

    public Ordner getOrdner() {
        return ordner;
    }

    public void setOrdner(Ordner ordner) {
        this.ordner = ordner;
    }

    public Timestamp getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Timestamp dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Timestamp getDateChange() {
        return dateChange;
    }

    public void setDateChange(Timestamp dateChange) {
        this.dateChange = dateChange;
    }

    public Timestamp getDateExpire() {
        return dateExpire;
    }

    public void setDateExpire(Timestamp dateExpire) {
        this.dateExpire = dateExpire;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
