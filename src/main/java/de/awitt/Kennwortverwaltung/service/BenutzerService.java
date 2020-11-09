package de.awitt.Kennwortverwaltung.service;

import de.awitt.Kennwortverwaltung.model.Benutzer;
import org.springframework.data.repository.CrudRepository;

public interface BenutzerService extends CrudRepository<Benutzer, Integer> {
    Benutzer findByName(
            String name
    );
}
