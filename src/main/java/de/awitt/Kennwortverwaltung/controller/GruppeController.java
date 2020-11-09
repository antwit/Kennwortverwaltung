package de.awitt.Kennwortverwaltung.controller;

import de.awitt.Kennwortverwaltung.model.Gruppe;
import de.awitt.Kennwortverwaltung.service.GruppeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@PreAuthorize("hasRole('keymanager')")
@RestController
public class GruppeController {

    @Autowired
    private GruppeService gruppeService;

    @PostMapping(path = "/gruppe")
    public Iterable<Gruppe> create(Authentication authentication) {
        DefaultOidcUser principal = (DefaultOidcUser)authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authoritiesTest = principal.getAuthorities();
        List<String> listAuthorities = new ArrayList<>();
        authoritiesTest.forEach(x -> listAuthorities.add(x.getAuthority()));
        List<Gruppe> gruppe = new ArrayList<>();
        listAuthorities.forEach(x -> gruppe.add(new Gruppe(x)));

        gruppe.forEach(x-> {
            if (x.getGruppe() != null && gruppeService.findByGruppe(x.getGruppe()) != null)
                gruppeService.save(x);
            });

        return gruppe;
    }


    @GetMapping(path = "/gruppe")
    public Iterable<Gruppe> readAll(Authentication authentication){
        DefaultOidcUser principal = (DefaultOidcUser)authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authoritiesTest = principal.getAuthorities();
        List<String> listAuthorities = new ArrayList<>();
        authoritiesTest.forEach(x -> listAuthorities.add(x.getAuthority()));
        List<Gruppe> gruppe = new ArrayList<>();
        listAuthorities.forEach(x -> gruppe.add(new Gruppe(x)));

        List<Gruppe> found = new ArrayList<>();

        gruppe.forEach(x-> {
            if (gruppeService.findByGruppe(x.getGruppe()) != null)
                found.add(gruppeService.findByGruppe(x.getGruppe()));
        });

        return found;
    }

    @PreAuthorize("hasRole('keymanageradmin')")
    @GetMapping(path = "/gruppe/all")
    public Iterable<Gruppe> read(){
        return gruppeService.findAll();
    }

    @PreAuthorize("hasRole('keymanageradmin')")
    @GetMapping(path = "/gruppe/{id}")
    Optional<Gruppe> findById(@PathVariable Integer id){
        return gruppeService.findById(id);
    }

    @PreAuthorize("hasRole('keymanageradmin')")
    @GetMapping("/gruppe/search")
    Gruppe findByGruppe(@RequestParam("gruppe") String gruppe) {
        return gruppeService.findByGruppe(gruppe);
    }

    @PreAuthorize("hasRole('keymanageradmin')")
    @PutMapping(path = "/gruppe")
    Gruppe update(@RequestBody Gruppe gruppe){
        if(gruppeService.findById(gruppe.getId()).isPresent())
            return gruppeService.save(gruppe);
        else
            throw new ValidationException("Group cannot be updated");
    }

    @PreAuthorize("hasRole('keymanageradmin')")
    @DeleteMapping(path = "/gruppe/{id}")
    public void delete(@PathVariable Integer id){
        gruppeService.deleteById(id);
    }
}
