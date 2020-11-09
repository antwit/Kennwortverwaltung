package de.awitt.Kennwortverwaltung.ServiceTests;

import de.awitt.Kennwortverwaltung.model.Benutzer;
import de.awitt.Kennwortverwaltung.service.BenutzerService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BenutzerServiceTest {

    @Autowired
    BenutzerService benutzerService;

    @Test
    public void testCreateReadDelete(){
        Benutzer benutzer = new Benutzer("karl@dev.de");

        benutzerService.save(benutzer);

        Iterable<Benutzer> benutzers = benutzerService.findAll();
        Assertions.assertThat(benutzers).extracting(Benutzer::getName).contains("karl@dev.de");

        benutzerService.deleteAll();
        Assertions.assertThat(benutzerService.findAll()).isEmpty();
    }
}
