package de.awitt.Kennwortverwaltung.SystemTests;

import de.awitt.Kennwortverwaltung.model.Gruppe;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class GruppeSystemTests {

    @Test
    public void testCreateReadDelete(){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/gruppe";
        Gruppe object = new Gruppe("Administratoren");
        ResponseEntity<Gruppe> entity = restTemplate.postForEntity(url, object, Gruppe.class);

        Gruppe[] objectArray = restTemplate.getForObject(url, Gruppe[].class);
        Assertions.assertThat(objectArray).extracting(Gruppe::getGruppe).contains("Administratoren");

        restTemplate.delete(url + "/" + entity.getBody().getId());
        Assertions.assertThat(restTemplate.getForObject(url, Gruppe[].class)).isNotIn(objectArray);
    }
}
