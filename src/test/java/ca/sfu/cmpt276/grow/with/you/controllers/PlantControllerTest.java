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

import ca.sfu.cmpt276.grow.with.you.models.Grower;
import ca.sfu.cmpt276.grow.with.you.models.Plant;
import ca.sfu.cmpt276.grow.with.you.models.Sponsor;
import ca.sfu.cmpt276.grow.with.you.services.PlantService;
import ca.sfu.cmpt276.grow.with.you.services.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class PlantControllerTest {
    
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
               
    @MockBean
    private PlantService plantService;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;
    
    @Test
    void testAddPlant() throws Exception {
        Grower grower = new Grower("grower", "grower", "email@email.com", 1000, 1, 0, 0);
        Plant plant = new Plant(grower, null, "rose", "rose","rose", 5.5, "BC", "Burnaby");
        plant.setPlantId(1);

        String newName = "lilac";
        String newDesc = "purple flowers";
        Double newPrice = 10.0;

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("session_user", grower);

        mockMvc.perform(MockMvcRequestBuilders.post("/plants/add")
            .session(session))
            
            .andExpect(MockMvcResultMatchers.status().isCreated());
            //perhaps checking the content of json content is good 
    }

    //test that grower gets redirected to "protected/user/addPlant"
    @Test
    void testGetAddPlant() throws Exception {
        Grower grower = new Grower("grower", "grower", "email@email.com", 1000, 1, 0, 0);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("session_user", grower);

        mockMvc.perform(MockMvcRequestBuilders.get("/plants/add").session(session))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("protected/user/addPlant"));
    }

    @Test
    void testGetAllPlants() throws Exception {
        //test for grower only 
        Grower grower = new Grower("grower", "grower", "email@email.com", 1000, 1, 0, 0);
        Plant plant1 = new Plant(grower, null, "rose", "rose","rose", 5.5, "BC", "Burnaby");
        Plant plant2 = new Plant(grower, null, "rose", "rose","rose", 5.5, "BC", "Burnaby");

        //get plants list
        List<Plant> plants = new ArrayList<>();
        plants.add(plant1);
        plants.add(plant2);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("session_usergrower", plants);

        mockMvc.perform(MockMvcRequestBuilders.get("/plants").session(session))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.view().name("protected/user/growerPlants.html"))
        .andExpect(MockMvcResultMatchers.model().attribute("plants", Matchers.is(plants)))
        .andExpect(MockMvcResultMatchers.model().attribute("selectedPlant", plant1));

    }

    @Test
    void testGetEditPlant() throws Exception {
        //ensure the right endpoint is there
        //button to direct to edit is not available to users who do not own the plant
        Grower grower = new Grower("grower", "grower", "email@email.com", 1000, 1, 0, 0);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("session_user", grower);

        mockMvc.perform(MockMvcRequestBuilders.get("/plants/edit/1").session(session))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("preotected/user/editPlant.html"));
        
    }

    //test null user when going to all pages
    @Test
    void testNullUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/plants/add"))
                .andExpect(MockMvcResultMatchers.status().is(302))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/"));

                mockMvc.perform(MockMvcRequestBuilders.get("/plants/edit/1"))
                .andExpect(MockMvcResultMatchers.status().is(302))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/"));

                mockMvc.perform(MockMvcRequestBuilders.get("/plants/1"))
                .andExpect(MockMvcResultMatchers.status().is(302))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/"));

                mockMvc.perform(MockMvcRequestBuilders.get("/plants"))
                .andExpect(MockMvcResultMatchers.status().is(302))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/"));
    }

    @Test
    void testGetOnePlantGrower() throws Exception {
        Grower grower = new Grower("grower", "grower", "email@email.com", 1000, 1, 0, 0);
        Plant plant = new Plant(grower, null, "rose", "rose","rose", 5.5, "BC", "Burnaby");
        plant.setPlantId(1);

        //get plants list
        List<Plant> plants = new ArrayList<>();
        plants.add(plant);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("session_user", grower);

        //mock get from plantService
        when(plantService.getPlantsByGrower(grower)).thenReturn(plants);

        //ensure endpoint is protected/user/growerPlants.html
        mockMvc.perform(MockMvcRequestBuilders.get("/plants/1").session(session))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("protected/user/growerPlants.html"))
            //ensure model has attrivutes plants and selectedPlant
            .andExpect(MockMvcResultMatchers.model().attribute("plants", Matchers.is(plants)))
            .andExpect(MockMvcResultMatchers.model().attribute("selectedPlant", Matchers.is(plant)))
            ;
    }

    @Test
    void testGetOnePlantSponsor() throws Exception {
        Sponsor sponsor = new Sponsor("sponsor", "sponsor", "email@email.com", 1000, 1, 5.5);
        Plant plant = new Plant(null, sponsor, "rose", "rose","rose", 5.5, "BC", "Burnaby");
        plant.setPlantId(1);

        //get plants list
        List<Plant> plants = new ArrayList<>();
        plants.add(plant);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("session_user", sponsor);

        //mock get from plantService
        when(plantService.getPlantsBySponsor(sponsor)).thenReturn(plants);

        //ensure endpoint is protected/user/growerPlants.html
        mockMvc.perform(MockMvcRequestBuilders.get("/plants/1").session(session))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("protected/user/growerPlants.html"))
            //ensure model has attrivutes plants and selectedPlant
            .andExpect(MockMvcResultMatchers.model().attribute("plants", Matchers.is(plants)))
            .andExpect(MockMvcResultMatchers.model().attribute("selectedPlant", Matchers.is(plant)))
            ;
    }

    @Test
    void testPutPlant() {
        Grower grower = new Grower("grower", "grower", "email@email.com", 1000, 1, 0, 0);
        Plant plant = new Plant(grower, null, "rose", "rose","rose", 5.5, "BC", "Burnaby");
        plant.setPlantId(1);

        MockHttpSession session = new MockHttpSession();

        when(userService.getUserBySession(session)).thenReturn(grower);
    }
}
