package ca.sfu.cmpt276.grow.with.you.controllers;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import ca.sfu.cmpt276.grow.with.you.models.Grower;
import ca.sfu.cmpt276.grow.with.you.models.Plant;
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

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockHttpSession session;

    //test null user
    @Test
    void testNullUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/dashboard"))
            .andExpect(MockMvcResultMatchers.status().is(302))
            .andExpect(MockMvcResultMatchers.view().name("redirect:/"));
    }

    //test that it returns to grower dashboard if the user is a grower
    @Test
    void testGetGrowerDashboard() throws Exception {
        //create the fake session after a grower has logged in
        Grower grower = new Grower("grower", "grower", "email@email.com", 1000, 1, 0, 0);
        Plant plant = new Plant(grower, null, "rose", "rose", "rose.png", 5.5,"BC","Burnaby");
        List<Plant> plantsList = new ArrayList<>();
        plantsList.add(plant);

        session.setAttribute("session_user",grower);


        //ensure the endpoint is protected/user/dashboard
        mockMvc.perform(MockMvcRequestBuilders.get("/dashboard"))
            .andExpect(MockMvcResultMatchers.status().is(302))
            .andExpect(MockMvcResultMatchers.view().name("protected/user/growerDashboard"))
            
            .andExpect(MockMvcResultMatchers.model().attribute("h", hasItem(
                allOf(
                    hasProperty("activeListings", Matchers.is(1)),
                    hasProperty("lifetimeListings", Matchers.is(1))
                )
            )));
    }

    //test that it returns to sponsor dashboard if the user is a sponsor
    @Test
    void testGetSponsorDashboard() {

    }

    //test that it returns to admin dashboard if the user is an admin
    @Test
    void testGetAdminDashboard() {
        //need to be able to create admin user
    }
}
