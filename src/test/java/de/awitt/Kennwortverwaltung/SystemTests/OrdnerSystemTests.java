package de.awitt.Kennwortverwaltung.SystemTests;

import de.awitt.Kennwortverwaltung.model.Ordner;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class OrdnerSystemTests {

    @Test
    public void testCreateReadDelete(){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/ordner";
        Ordner object = new Ordner("/var/user/passwords");
        ResponseEntity<Ordner> entity = restTemplate.postForEntity(url, object, Ordner.class);

        Ordner[] objectArray = restTemplate.getForObject(url, Ordner[].class);
        Assertions.assertThat(objectArray).extracting(Ordner::getPfad).contains("/var/user/passwords");

        restTemplate.delete(url + "/" + entity.getBody().getId());
        Assertions.assertThat(restTemplate.getForObject(url, Ordner[].class)).isNotIn(objectArray);
    }
}
