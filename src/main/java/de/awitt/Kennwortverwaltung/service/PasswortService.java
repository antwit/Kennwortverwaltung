package de.awitt.Kennwortverwaltung.service;

import de.awitt.Kennwortverwaltung.model.BenutzerGruppe;
import de.awitt.Kennwortverwaltung.model.Passwort;
import org.springframework.data.repository.CrudRepository;

public interface PasswortService extends CrudRepository<Passwort, Integer> {
    Iterable<Passwort> findByBenutzerGruppe(
            BenutzerGruppe benutzerGruppe
    );
}
