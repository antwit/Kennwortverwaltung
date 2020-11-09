package de.awitt.Kennwortverwaltung.ItegrationTests;

import de.awitt.Kennwortverwaltung.controller.BenutzerController;
import de.awitt.Kennwortverwaltung.controller.BenutzerGruppeController;
import de.awitt.Kennwortverwaltung.controller.GruppeController;
import de.awitt.Kennwortverwaltung.controller.PasswortController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PasswortIntegrationTests {

    @Autowired
    PasswortController passwortController;

    @Autowired
    BenutzerGruppeController benutzerGruppeController;

    @Autowired
    BenutzerController benutzerController;

    @Autowired
    GruppeController gruppeController;

    @Test
    public void testCreateReadDelete(Authentication authentication) {

        /*DefaultOidcUser principal = (DefaultOidcUser)authentication.getPrincipal();
        Map<String, Object> claims = principal.getClaims();
        String stringUPN = (String)claims.get("upn");
        Benutzer benutzerResult = new Benutzer(stringUPN);

        Gruppe gruppe = new Gruppe("local");
        Gruppe gruppeResult = gruppeController.create(gruppe);

        BenutzerGruppe benutzerGruppe = new BenutzerGruppe(gruppeResult, benutzerResult);
        BenutzerGruppe benutzerGruppeResult = benutzerGruppeController.create(benutzerGruppe);

        Iterable<BenutzerGruppe> benutzerGruppen = benutzerGruppeController.read();
        Assertions.assertThat(benutzerGruppen).extracting(BenutzerGruppe::getBenutzer).extracting(Benutzer::getId).contains(benutzerResult.getId());
        //bei Gruppe auf Null prüfen!!

        benutzerGruppeController.delete(benutzerGruppeResult.getId());
        benutzerController.delete(benutzerResult.getId());
        gruppeController.delete(gruppeResult.getId());
        Assertions.assertThat(benutzerController.read()).isNotIn(benutzerGruppeResult);
        */

    }
}
