package de.awitt.Kennwortverwaltung.ItegrationTests;

import de.awitt.Kennwortverwaltung.controller.OrdnerController;
import de.awitt.Kennwortverwaltung.model.Ordner;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrdnerIntegrationTests {

    @Autowired
    OrdnerController ordnerController;

    @Test
    public void testCreateReadDelete(){
        Ordner ordner = new Ordner("/web/anwender");

        Ordner ordnerResult = ordnerController.create(ordner);

        Iterable<Ordner> benutzers = ordnerController.read();
        Assertions.assertThat(benutzers).extracting(Ordner::getPfad).contains("/web/anwender");

        ordnerController.delete(ordnerResult.getId());
        Assertions.assertThat(ordnerController.read()).isNotIn(ordnerResult);
    }
}
