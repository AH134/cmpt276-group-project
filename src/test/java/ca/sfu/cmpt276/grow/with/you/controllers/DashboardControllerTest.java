package ca.sfu.cmpt276.grow.with.you.controllers;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import ca.sfu.cmpt276.grow.with.you.models.Grower;
import ca.sfu.cmpt276.grow.with.you.models.Plant;
import ca.sfu.cmpt276.grow.with.you.models.Sponsor;
import ca.sfu.cmpt276.grow.with.you.services.PlantService;
import ca.sfu.cmpt276.grow.with.you.services.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class DashboardControllerTest {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @MockBean
    private PlantService plantService;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    // test null user
    @Test
    void testNullUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/dashboard"))
                .andExpect(MockMvcResultMatchers.status().is(302))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/"));
    }

    // test that it returns to grower dashboard if the user is a grower
    @Test
    void testGetGrowerDashboard() throws Exception {
        // create the fake session after a grower has logged in
        Grower grower = new Grower("grower", "grower", "email@email.com", 1000, 1, 0, 0);
        Plant plant = new Plant(grower, null, "rose", "rose", "rose.png", 5.5, "BC", "Burnaby");

        List<Plant> plants = new ArrayList<>();
        plants.add(plant);

        // mock get from service
        when(plantService.getPlantsByGrower(grower)).thenReturn(plants);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("session_user", grower);

        // ensure the endpoint is protected/user/dashboard
        mockMvc.perform(MockMvcRequestBuilders.get("/dashboard").session(session))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.view().name("protected/user/growerDashboard"))
                .andExpect(MockMvcResultMatchers.model().attribute("activeListings", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.model().attribute("lifetimeListings", Matchers.is(1)));
    }

    // test that it returns to sponsor dashboard if the user is a sponsor
    @Test
    void testGetSponsorDashboard() {
        Sponsor sponsor = new Sponsor("sponsor", "sponsor", "sponsor@email.com", 130, 1, 5);
        Plant plant = new Plant(null, sponsor, "rose", "rose", "rose.png", 5, "BC", "Burnaby");

        List<Plant> plants = new ArrayList<>();
        plants.add(plant);

        when(plantService.getPlantsBySponsor(sponsor)).thenReturn(plants);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("session_user", sponsor);
    }

    // test that it returns to admin dashboard if the user is an admin
    @Test
    void testGetAdminDashboard() {
        // need to be able to create admin user
    }
}
