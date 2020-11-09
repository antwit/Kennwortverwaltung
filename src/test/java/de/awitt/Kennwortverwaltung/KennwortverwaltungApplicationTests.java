package de.awitt.Kennwortverwaltung;

import de.awitt.Kennwortverwaltung.controller.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest
class KennwortverwaltungApplicationTests {

	@Autowired
	BenutzerController benutzerController;

	@Autowired
	BenutzerGruppeController benutzerGruppeController;

	@Autowired
	GruppeController gruppeController;

	@Autowired
	OrdnerController ordnerController;

	@Autowired
	PasswortController passwortController;

	@Test
	void contextLoads() {
		Assert.notNull(benutzerController, "Cant load benutzerController");
		Assert.notNull(benutzerGruppeController, "Cant load benutzerGruppeController");
		Assert.notNull(gruppeController, "Cant load gruppeController");
		Assert.notNull(ordnerController, "Cant load ordnerController");
		Assert.notNull(passwortController, "Cant load passwortController");
	}

}
