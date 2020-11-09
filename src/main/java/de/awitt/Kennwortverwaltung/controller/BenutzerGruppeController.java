package de.awitt.Kennwortverwaltung.controller;

import de.awitt.Kennwortverwaltung.model.BenutzerGruppe;
import de.awitt.Kennwortverwaltung.service.BenutzerGruppeService;
import de.awitt.Kennwortverwaltung.service.BenutzerService;
import de.awitt.Kennwortverwaltung.service.GruppeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.*;

@PreAuthorize("hasRole('keymanager')")
@RestController
public class BenutzerGruppeController {

    @Autowired
    private BenutzerGruppeService benutzerGruppeService;

    @Autowired
    private BenutzerService benutzerService;

    @Autowired
    private GruppeService gruppeService;

    @PostMapping(path = "/benutzerGruppe")
    public BenutzerGruppe create(@RequestBody BenutzerGruppe benutzerGruppe, Authentication authentication){
        DefaultOidcUser principal = (DefaultOidcUser)authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authoritiesTest = principal.getAuthorities();
        Map<String, Object> claims = principal.getClaims();
        String stringUPN = (String)claims.get("upn");
        List<String> listAuthorities = new ArrayList<>();
        authoritiesTest.forEach(x -> listAuthorities.add(x.getAuthority()));

        if(benutzerGruppe.getBenutzer() != null
                && benutzerGruppe.getBenutzer().getName().equals(stringUPN)
                && benutzerGruppe.getGruppe()!=null
                    ?listAuthorities.contains(benutzerGruppe.getGruppe().getGruppe())
                    :true)
            return benutzerGruppeService.save(benutzerGruppe);
        else
            throw new ValidationException("User_Group cannot be created");
    }

    @GetMapping(path = "/benutzerGruppe")
    public Iterable<BenutzerGruppe> read(Authentication authentication) {
        DefaultOidcUser principal = (DefaultOidcUser)authentication.getPrincipal();
        Map<String, Object> claims = principal.getClaims();
        String stringUPN = (String)claims.get("upn");

        return benutzerGruppeService.findByBenutzer(benutzerService.findByName(stringUPN));
    }

    @PreAuthorize("hasRole('keymanageradmin')")
    @GetMapping(path = "/benutzerGruppe/all")
    public Iterable<BenutzerGruppe> readAll(){
        return benutzerGruppeService.findAll();
    }

    @PreAuthorize("hasRole('keymanageradmin')")
    @GetMapping(path = "/benutzerGruppe/{id}")
    Optional<BenutzerGruppe> findById(@PathVariable Integer id){
        return benutzerGruppeService.findById(id);
    }

    @PreAuthorize("hasRole('keymanageradmin')")
    @GetMapping("/benutzerGruppe/searchForOne")
    BenutzerGruppe findByBenutzerAndGruppe(@RequestParam(value = "name") String benutzer, @RequestParam(value = "gruppe") String gruppe) {
            return benutzerGruppeService.findByBenutzerAndGruppe(benutzerService.findByName(benutzer), gruppeService.findByGruppe(gruppe));
    }

    @PreAuthorize("hasRole('keymanageradmin')")
    @GetMapping("/benutzerGruppe/search")
    Iterable<BenutzerGruppe> findByBenutzerOrGruppe(@RequestParam(value = "name", required = false) String benutzer, @RequestParam(value = "gruppe", required = false) String gruppe) {
        if(benutzer != null && gruppe != null)
            return benutzerGruppeService.findByBenutzerOrGruppe(benutzerService.findByName(benutzer), gruppeService.findByGruppe(gruppe));
        else if(benutzer != null)
            return benutzerGruppeService.findByBenutzer(benutzerService.findByName(benutzer));
        else if(gruppe != null)
            return benutzerGruppeService.findByGruppe(gruppeService.findByGruppe(gruppe));
        else
            return benutzerGruppeService.findAll();
    }

    @PreAuthorize("hasRole('keymanageradmin')")
    @PutMapping(path = "/benutzerGruppe")
    BenutzerGruppe update(@RequestBody BenutzerGruppe benutzerGruppe){
        if(benutzerGruppeService.findById(benutzerGruppe.getId()).isPresent())
            return benutzerGruppeService.save(benutzerGruppe);
        else
            throw new ValidationException("User_Group cannot be updated");
    }

    @PreAuthorize("hasRole('keymanageradmin')")
    @DeleteMapping(path = "/benutzerGruppe/{id}")
    public void delete(@PathVariable Integer id){
        benutzerGruppeService.deleteById(id);
    }
}
