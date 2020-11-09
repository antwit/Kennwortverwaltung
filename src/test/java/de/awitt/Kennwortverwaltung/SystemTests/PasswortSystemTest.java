package de.awitt.Kennwortverwaltung.SystemTests;

import de.awitt.Kennwortverwaltung.model.*;
import de.idssystem.Kennwortverwaltung.model.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.Date;


public class PasswortSystemTest {

    @Test
    public void testCreateReadDelete() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/passwort";
        String urlBenutzerGruppe = "http://localhost:8080/benutzerGruppe";
        String urlBenutzer = "http://localhost:8080/benutzer";
        String urlGruppe = "http://localhost:8080/gruppe";
        String urlOrdner = "http://localhost:8080/ordner";
        Benutzer benutzer = new Benutzer("admin_1");
        Gruppe gruppe = new Gruppe("Administrator_1");
        Ordner ordner = new Ordner("/user/passswort_1");
        Timestamp dateExpire = new Timestamp((new Date()).getTime());

        ResponseEntity<Ordner> entityOrdner = restTemplate.postForEntity(urlOrdner, ordner, Ordner.class);
        ResponseEntity<Benutzer> entityBenutzer = restTemplate.postForEntity(urlBenutzer, benutzer, Benutzer.class);
        ResponseEntity<Gruppe> entityGruppe = restTemplate.postForEntity(urlGruppe, gruppe, Gruppe.class);

        BenutzerGruppe benutzerGruppe = new BenutzerGruppe(entityGruppe.getBody(), entityBenutzer.getBody());
        ResponseEntity<BenutzerGruppe> entityBenutzerGruppe = restTemplate.postForEntity(urlBenutzerGruppe, benutzerGruppe, BenutzerGruppe.class);

        Passwort passwort = new Passwort(
                entityBenutzerGruppe.getBody(),
                entityOrdner.getBody(),
                dateExpire,
                "Eine Notiz",
                "Apache Tomcat Webserver 5.4.4",
                "10.10.5.1:8794",
                "Meins51cher!!",
                "admin0815");
        ResponseEntity<Passwort> entity = restTemplate.postForEntity(url, passwort, Passwort.class);

        Passwort[] objectArray = restTemplate.getForObject(url, Passwort[].class);
        Assertions.assertThat(objectArray).extracting(Passwort::getNote).contains("Eine Notiz");
        Assertions.assertThat(objectArray).extracting(Passwort::getTitle).contains("Apache Tomcat Webserver 5.4.4");
        Assertions.assertThat(objectArray).extracting(Passwort::getUrl).contains("10.10.5.1:8794");
        Assertions.assertThat(objectArray).extracting(Passwort::getPassword).contains("Meins51cher!!");
        Assertions.assertThat(objectArray).extracting(Passwort::getUsername).contains("admin0815");
        Assertions.assertThat(objectArray).extracting(Passwort::getOrdner).extracting(Ordner::getPfad).contains("/user/passswort_1");
        Assertions.assertThat(objectArray).extracting(Passwort::getBenutzerGruppe).extracting(BenutzerGruppe::getBenutzer).extracting(Benutzer::getName).contains("admin_1");
        //Gruppe in Benutzergruppe können leer sein!!
        Assertions.assertThat(objectArray).extracting(Passwort::getBenutzerGruppe).extracting(BenutzerGruppe::getGruppe).isNotNull().extracting(Gruppe::getGruppe).contains("Administrator_1");

        restTemplate.delete(url + "/" + entity.getBody().getId());
        restTemplate.delete(urlBenutzerGruppe + "/" + entityBenutzerGruppe.getBody().getId());
        restTemplate.delete(urlBenutzer + "/" + entityBenutzer.getBody().getId());
        restTemplate.delete(urlGruppe + "/" + entityGruppe.getBody().getId());
        restTemplate.delete(urlOrdner + "/" + entityOrdner.getBody().getId());

        Assertions.assertThat(restTemplate.getForObject(url, Passwort[].class)).isNotIn(objectArray);
    }
}
