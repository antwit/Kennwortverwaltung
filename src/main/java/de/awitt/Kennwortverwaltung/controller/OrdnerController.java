package de.awitt.Kennwortverwaltung.controller;

import de.awitt.Kennwortverwaltung.model.Ordner;
import de.awitt.Kennwortverwaltung.service.OrdnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.Optional;

@PreAuthorize("hasRole('keymanager')")
@RestController
public class OrdnerController {

    @Autowired
    private OrdnerService ordnerService;

    @PostMapping(path = "/ordner")
    public Ordner create(@RequestBody Ordner ordner){
        if(ordner.getPfad()!= null)
            return ordnerService.save(ordner);
        else
            throw new ValidationException("Directory cannot be created");

    }

    @GetMapping(path = "/ordner")
    public Iterable<Ordner> read(){
        return ordnerService.findAll();
    }

    @GetMapping(path = "/ordner/{id}")
    Optional<Ordner> findById(@PathVariable Integer id){
        return ordnerService.findById(id);
    }

    @GetMapping("/ordner/search")
    Ordner findByPfad(@RequestParam("pfad") String pfad) {
        return ordnerService.findByPfad(pfad);
    }

    @PutMapping(path = "/ordner")
    Ordner update(@RequestBody Ordner ordner){
        if(ordnerService.findById(ordner.getId()).isPresent())
            return ordnerService.save(ordner);
        else
            throw new ValidationException("Directory cannot be updated");
    }

    @DeleteMapping(path = "/ordner/{id}")
    public void delete(@PathVariable Integer id){
        ordnerService.deleteById(id);
    }
}
