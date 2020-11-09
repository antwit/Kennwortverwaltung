package de.awitt.Kennwortverwaltung.SystemTests;

import de.awitt.Kennwortverwaltung.model.Benutzer;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class BenutzerSystemTests {

    @Test
    public void testCreateReadDelete(){
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8080/benutzer";

        Benutzer benutzer = new Benutzer("admin");
        ResponseEntity<Benutzer> entity = restTemplate.postForEntity(url, benutzer, Benutzer.class);

        Benutzer[] benutzers = restTemplate.getForObject(url, Benutzer[].class);
        Assertions.assertThat(benutzers).extracting(Benutzer::getName).contains("admin");

        restTemplate.delete(url + "/" + entity.getBody().getId());
        Assertions.assertThat(restTemplate.getForObject(url, Benutzer[].class)).isNotIn(benutzers);
    }

    @Test
    public void testErrorHandlingReturnsBadRequest(){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/none";

        try{
            restTemplate.getForEntity(url, String.class);
        }catch (HttpClientErrorException e){
            Assert.assertEquals(HttpStatus.BAD_REQUEST, e.getStatusCode());
        }
    }
}
