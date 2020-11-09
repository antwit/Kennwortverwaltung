package de.awitt.Kennwortverwaltung.service;

import de.awitt.Kennwortverwaltung.model.Benutzer;
import de.awitt.Kennwortverwaltung.model.BenutzerGruppe;
import de.awitt.Kennwortverwaltung.model.Gruppe;
import org.springframework.data.repository.CrudRepository;

public interface BenutzerGruppeService extends CrudRepository<BenutzerGruppe, Integer> {
    BenutzerGruppe findByBenutzerAndGruppe(
            Benutzer benutzer,
            Gruppe gruppe
    );

    Iterable<BenutzerGruppe> findByBenutzerOrGruppe(
            Benutzer benutzer,
            Gruppe gruppe
    );

    Iterable<BenutzerGruppe> findByBenutzer(
            Benutzer benutzer
    );

    Iterable<BenutzerGruppe> findByGruppe(
            Gruppe gruppe
    );
}
