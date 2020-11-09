package de.awitt.Kennwortverwaltung.ItegrationTests;

import de.awitt.Kennwortverwaltung.controller.GruppeController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GruppenIntegrationTests {

    @Autowired
    GruppeController gruppeController;

    @Test
    public void testCreateReadDelete(){
       /* Gruppe gruppe = new Gruppe("anwender");

        Gruppe gruppeResult = gruppeController.create(gruppe);

        Iterable<Gruppe> benutzers = gruppeController.read();
        Assertions.assertThat(benutzers).extracting(Gruppe::getGruppe).contains("anwender");

        gruppeController.delete(gruppeResult.getId());
        Assertions.assertThat(gruppeController.read()).isNotIn(gruppeResult);
        */

    }
}
