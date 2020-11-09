package de.awitt.Kennwortverwaltung.service;

import de.awitt.Kennwortverwaltung.model.Ordner;
import org.springframework.data.repository.CrudRepository;

public interface OrdnerService extends CrudRepository<Ordner, Integer> {
    Ordner findByPfad(
            String pfad
    );
}
