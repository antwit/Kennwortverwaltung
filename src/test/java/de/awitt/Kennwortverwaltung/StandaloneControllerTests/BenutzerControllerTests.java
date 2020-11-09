package de.awitt.Kennwortverwaltung.StandaloneControllerTests;

import de.awitt.Kennwortverwaltung.model.Benutzer;
import de.awitt.Kennwortverwaltung.service.BenutzerService;
import de.awitt.Kennwortverwaltung.controller.BenutzerController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.hamcrest.Matchers;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BenutzerController.class)
public class BenutzerControllerTests {

    @MockBean
    BenutzerService benutzerService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testCreateReadDelete() throws Exception {
        Benutzer benutzer = new Benutzer("da@asd.com");
        List<Benutzer> benutzers = Arrays.asList(benutzer);

        Mockito.when(benutzerService.findAll()).thenReturn(benutzers);

        mockMvc.perform(get("/benutzer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].name", Matchers.is("da@asd.com")));
    }

}
