package de.awitt.Kennwortverwaltung.ItegrationTests;

import de.awitt.Kennwortverwaltung.controller.BenutzerController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BenutzerIntegrationTests {

    @Autowired
    BenutzerController benutzerController;

    @Test
    public void testCreateReadDelete(Authentication authentication){
       /* DefaultOidcUser principal = (DefaultOidcUser)authentication.getPrincipal();
        Map<String, Object> claims = principal.getClaims();
        String stringUPN = (String)claims.get("upn");
        Benutzer benutzerResult = new Benutzer(stringUPN);

        Iterable<Benutzer> benutzers = benutzerController.read();
        Assertions.assertThat(benutzers).extracting(Benutzer::getName).contains("anwender");

        benutzerController.delete(benutzerResult.getId());
        Assertions.assertThat(benutzerController.read()).isNotIn(benutzerResult);
        */

    }
}
