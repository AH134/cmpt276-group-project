package ca.sfu.cmpt276.grow.with.you.controllers;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import ca.sfu.cmpt276.grow.with.you.models.Grower;
import ca.sfu.cmpt276.grow.with.you.models.Plant;
import ca.sfu.cmpt276.grow.with.you.models.Sponsor;
import ca.sfu.cmpt276.grow.with.you.services.PlantService;
import ca.sfu.cmpt276.grow.with.you.services.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlantService plantService;

    @MockBean UserService userService;

    @Test
    void testGetEditProfileNull() throws Exception{
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("session_user", null);

        mockMvc.perform(MockMvcRequestBuilders.get("/profile/edit")
            .session(session))
            
            .andExpect(MockMvcResultMatchers.status().is(302));

    }

    @Test
    void testGetEditProfileSuccess() throws Exception{
        Grower grower = new Grower("grower", "grower", "email@email.com", 1000, 1, 0, 0);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("session_user", grower);

        when(userService.getUserBySession(session)).thenReturn(grower);

        mockMvc.perform(MockMvcRequestBuilders.get("/profile/edit")
            .session(session))
            
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("protected/user/edit"));
    }

    @Test
    void testEditProfile() {

    }

    @Test
    void testUserProfileGrower() throws Exception {
        Grower grower = new Grower("grower", "grower", "email@email.com", 1000, 1, 0, 0);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("session_user", grower);

        List<Plant> plants = new ArrayList<>();

        when(userService.getUserBySession(session)).thenReturn(grower);
        when(plantService.getPlantsByGrower(grower)).thenReturn(plants);

        mockMvc.perform(MockMvcRequestBuilders.get("/profile/edit")
            .session(session))
            
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("protected/user/profile"))
            .andExpect(MockMvcResultMatchers.model().attribute("user", Matchers.is(grower)))
            .andExpect(MockMvcResultMatchers.model().attribute("plants", Matchers.is(plants)));
    }

    @Test
    void testUserProfileSponsor() throws Exception {
        Sponsor sponsor = new Sponsor("grower", "grower", "email@email.com", 1000, 1, 0);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("session_user", sponsor);

        List<Plant> plants = new ArrayList<>();

        when(userService.getUserBySession(session)).thenReturn(sponsor);
        when(plantService.getPlantsBySponsor(sponsor)).thenReturn(plants);

        mockMvc.perform(MockMvcRequestBuilders.get("/profile/edit")
            .session(session))
            
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("protected/user/profile"))
            .andExpect(MockMvcResultMatchers.model().attribute("user", Matchers.is(sponsor)))
            .andExpect(MockMvcResultMatchers.model().attribute("plants", Matchers.is(plants)));
    }

    @Test
    void testUserProfileElse() {

    }
}
