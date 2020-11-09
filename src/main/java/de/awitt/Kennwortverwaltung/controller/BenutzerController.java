package de.awitt.Kennwortverwaltung.controller;

import de.awitt.Kennwortverwaltung.model.Benutzer;
import de.awitt.Kennwortverwaltung.service.BenutzerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.Map;
import java.util.Optional;

@PreAuthorize("hasRole('keymanager')")
@RestController
public class BenutzerController {

    @Autowired
    private BenutzerService benutzerService;

    @PostMapping(path = "/benutzer")
    public Benutzer create(Authentication authentication) {
        DefaultOidcUser principal = (DefaultOidcUser)authentication.getPrincipal();
        Map<String, Object> claims = principal.getClaims();
        String stringUPN = (String)claims.get("upn");
        Benutzer user = new Benutzer(stringUPN);

        if(!benutzerService.findById(user.getId()).isPresent())
            return benutzerService.save(user);
        else
            throw new ValidationException("User cannot be created");
    }

    @GetMapping(path = "/benutzer")
    public Benutzer read(Authentication authentication){
        DefaultOidcUser principal = (DefaultOidcUser)authentication.getPrincipal();
        Map<String, Object> claims = principal.getClaims();
        String stringUPN = (String)claims.get("upn");

        return benutzerService.findByName(stringUPN);
    }

    @PreAuthorize("hasRole('keymanageradmin')")
    @GetMapping(path = "/benutzer/all")
    public Iterable<Benutzer> readAll(){
        return benutzerService.findAll();
    }

    @PreAuthorize("hasRole('keymanageradmin')")
    @GetMapping(path = "/benutzer/{id}")
    Optional<Benutzer> findById(@PathVariable Integer id){
        return benutzerService.findById(id);
    }

    @PreAuthorize("hasRole('keymanageradmin')")
    @GetMapping("/benutzer/search")
    Benutzer findByName(@RequestParam("name") String name) {
        return benutzerService.findByName(name);
    }

    @PreAuthorize("hasRole('keymanageradmin')")
    @PutMapping(path = "/benutzer")
    Benutzer update(@RequestBody Benutzer benutzer){
        if(benutzerService.findById(benutzer.getId()).isPresent())
            return benutzerService.save(benutzer);
        else
            throw new ValidationException("User cannot be updated");
    }

    @PreAuthorize("hasRole('keymanageradmin')")
    @DeleteMapping(path = "/benutzer/{id}")
    public void delete(@PathVariable Integer id){
        benutzerService.deleteById(id);
    }

    @GetMapping(path = "/none")
    public Benutzer somethingIsWrong(){
        throw new ValidationException("Something went wrong!");
    }
}