package de.awitt.Kennwortverwaltung.service;

import de.awitt.Kennwortverwaltung.model.Gruppe;
import org.springframework.data.repository.CrudRepository;

public interface GruppeService extends CrudRepository<Gruppe, Integer> {
    Gruppe findByGruppe(
            String gruppe
    );
}
