package de.awitt.Kennwortverwaltung.controller;

import de.awitt.Kennwortverwaltung.model.Passwort;
import de.awitt.Kennwortverwaltung.service.*;
import de.idssystem.Kennwortverwaltung.service.*;
import org.apache.commons.codec.binary.Base64;
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
public class PasswortController {

    private Base64 base64 = new Base64();

    @Autowired
    private PasswortService passwortService;

    @Autowired
    private BenutzerGruppeService benutzerGruppeService;

    @Autowired
    private BenutzerService benutzerService;

    @Autowired
    private GruppeService gruppeService;

    @Autowired
    private OrdnerService ordnerService;

    @PostMapping(path = "/passwort")
    public Passwort create(@RequestBody Passwort passwort, Authentication authentication){
        DefaultOidcUser principal = (DefaultOidcUser)authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authoritiesTest = principal.getAuthorities();
        Map<String, Object> claims = principal.getClaims();
        String stringUPN = (String)claims.get("upn");
        List<String> listAuthorities = new ArrayList<>();
        authoritiesTest.forEach(x -> listAuthorities.add(x.getAuthority()));

        if(passwort.getBenutzerGruppe().getBenutzer() != null
                && passwort.getBenutzerGruppe().getBenutzer().getName().equals(stringUPN)
                && passwort.getBenutzerGruppe().getGruppe()!=null
                ?listAuthorities.contains(passwort.getBenutzerGruppe().getGruppe().getGruppe())
                :true) {

            if (passwort.getPassword() != null
                    && passwort.getTitle() != null
                    && passwort.getBenutzerGruppe() != null
                    && passwort.getUsername() != null
                    && passwort.getOrdner() != null
                    && ordnerService.findByPfad(passwort.getOrdner().getPfad()).getId() == passwort.getOrdner().getId()) {
                passwort = encode(passwort);
                return passwortService.save(passwort);
            }
            else
                throw new ValidationException("Password cannot be created");
        }
        else
            throw new ValidationException("Password cannot be created");
    }

    @GetMapping(path = "/passwort")
    public Iterable<Passwort> read(Authentication authentication) {
        DefaultOidcUser principal = (DefaultOidcUser)authentication.getPrincipal();
        Map<String, Object> claims = principal.getClaims();
        String stringUPN = (String)claims.get("upn");

        Iterable<Passwort> passworts = findByBenutzerORGruppe(stringUPN,null);
        passworts.forEach(pass -> decode(pass));

        return passworts;
    }

    @PreAuthorize("hasRole('keymanageradmin')")
    @GetMapping(path = "/passwort/all")
    public Iterable<Passwort> readAll(){
        Iterable<Passwort> passworts = passwortService.findAll();
        passworts.forEach(pass -> decode(pass));

        return passworts;
    }

    @PreAuthorize("hasRole('keymanageradmin')")
    @GetMapping(path = "/passwort/{id}")
    Optional<Passwort> findById(@PathVariable Integer id){
        Optional<Passwort> passwort = passwortService.findById(id);
        passwort.ifPresent(pass -> decode(pass));

        return passwort;
    }

    @PreAuthorize("hasRole('keymanageradmin')")
    @GetMapping("/passwort/search")
    Iterable<Passwort> findByBenutzerORGruppe(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "gruppe", required = false) String gruppe) {
        List<Passwort> passworts = new ArrayList<>();

        if(gruppe == null && name != null) {
            benutzerGruppeService.findByBenutzer(benutzerService.findByName(name))
                    .forEach(x-> passwortService.findByBenutzerGruppe(x).forEach(y -> passworts.add(y)));
        }else if(name == null && gruppe != null){
            benutzerGruppeService.findByGruppe(gruppeService.findByGruppe(gruppe))
                    .forEach(x -> passwortService.findByBenutzerGruppe(x).forEach(y -> passworts.add(y)));
        }
        else if(name != null && gruppe != null){
            passwortService.findByBenutzerGruppe(benutzerGruppeService.findByBenutzerAndGruppe(benutzerService.findByName(name),
                    gruppeService.findByGruppe(gruppe))).forEach(y -> passworts.add(y));
        }
        else
            passwortService.findAll();

        passworts.forEach(pass -> decode(pass));

        return  passworts;
    }

    @PutMapping(path = "/passwort")
    Passwort update(@RequestBody Passwort passwort, Authentication authentication) {
        DefaultOidcUser principal = (DefaultOidcUser)authentication.getPrincipal();
        Map<String, Object> claims = principal.getClaims();
        String stringUPN = (String)claims.get("upn");

        if(passwortService.findById(passwort.getId()).isPresent() && passwortService.findById(passwort.getId()).get().getBenutzerGruppe().getBenutzer().getName().equals(stringUPN)) {
            passwort = encode(passwort);
            return passwortService.save(passwort);
        }
        else
            throw new ValidationException("Password cannot be updated");
    }

    @DeleteMapping(path = "/passwort/{id}")
    public void delete(@PathVariable Integer id, Authentication authentication) {
        DefaultOidcUser principal = (DefaultOidcUser)authentication.getPrincipal();
        Map<String, Object> claims = principal.getClaims();
        String stringUPN = (String)claims.get("upn");

        if (passwortService.findById(id).isPresent() && passwortService.findById(id).get().getBenutzerGruppe().getBenutzer().getName().equals(stringUPN))
            passwortService.deleteById(id);
        else
            throw new ValidationException("Password cannot be delete");
    }

    private Passwort encode(Passwort passwort){
        if(passwort != null) {
            byte[] byteArray = passwort.getPassword().getBytes();
            String encode = Base64.encodeBase64String(byteArray);
            passwort.setPassword(encode);
        }
        return passwort;
    }

    private Passwort decode(Passwort passwort){
        if(passwort != null) {
            byte[] byteArray = passwort.getPassword().getBytes();
            byte[] byteDecode = Base64.decodeBase64(byteArray);
            String decode = new String(byteDecode);
            passwort.setPassword(decode);
        }
        return passwort;
    }
}
