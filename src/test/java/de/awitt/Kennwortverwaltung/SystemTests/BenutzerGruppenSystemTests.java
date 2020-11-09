package de.awitt.Kennwortverwaltung.SystemTests;

import de.awitt.Kennwortverwaltung.model.Benutzer;
import de.awitt.Kennwortverwaltung.model.BenutzerGruppe;
import de.awitt.Kennwortverwaltung.model.Gruppe;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class BenutzerGruppenSystemTests {

    @Test
    public void testCreateReadDelete(){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/benutzerGruppe";
        String urlBenutzer = "http://localhost:8080/benutzer";
        String urlGruppe = "http://localhost:8080/gruppe";
        Benutzer benutzer = new Benutzer("admin");
        Gruppe gruppe = new Gruppe("Administrator");

        ResponseEntity<Benutzer> entityBenutzer = restTemplate.postForEntity(urlBenutzer, benutzer, Benutzer.class);
        ResponseEntity<Gruppe> entityGruppe = restTemplate.postForEntity(urlGruppe, gruppe, Gruppe.class);

        BenutzerGruppe object = new BenutzerGruppe(entityGruppe.getBody(), entityBenutzer.getBody());
        ResponseEntity<BenutzerGruppe> entity = restTemplate.postForEntity(url, object, BenutzerGruppe.class);

        BenutzerGruppe[] objectArray = restTemplate.getForObject(url, BenutzerGruppe[].class);
        Assertions.assertThat(objectArray).extracting(BenutzerGruppe::getBenutzer).extracting(Benutzer::getId).contains(entityBenutzer.getBody().getId());
        //Gruppen können auch leer sein!!
        //Assertions.assertThat(objectArray).extracting(BenutzerGruppe::getGruppe).extracting(Gruppe::getGruppe).contains("Administrator");

        restTemplate.delete(url + "/" + entity.getBody().getId());
        restTemplate.delete(urlBenutzer + "/" + entityBenutzer.getBody().getId());
        restTemplate.delete(urlGruppe + "/" + entityGruppe.getBody().getId());
        Assertions.assertThat(restTemplate.getForObject(url, BenutzerGruppe[].class)).isNotIn(objectArray);
    }
}
