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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import ca.sfu.cmpt276.grow.with.you.models.Grower;
import ca.sfu.cmpt276.grow.with.you.models.Plant;
import ca.sfu.cmpt276.grow.with.you.services.PlantService;
import ca.sfu.cmpt276.grow.with.you.services.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class MarketplaceControllerTest {

    @Autowired
    private MockMvc mockmvc;

    @MockBean
    private PlantService plantService;

    @MockBean UserService userService;

    @Test
    void testGetAllPlantsFail() throws Exception{
        MockHttpSession session = new MockHttpSession();

        when(userService.getUserBySession(session)).thenReturn(null);
        
        mockmvc.perform(MockMvcRequestBuilders.get("/marketplace")
            .session(session))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("main"));
    }

    @Test
    void testGetAllPlantsSuccess() throws Exception{
        //initialize all varaibles
        Grower grower = new Grower("grower","password","email", 0, 0,0,0);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("session_user", grower);

        int pageNo = 0;
        int pageSize = 10;
        String pageNoString ="0";
        String pageSizeString = "10";
        String search = "";
        String province = "all";
        String city = "all";
        String price = "all";

        List<Plant> plants = new ArrayList<>();
        Plant plant1 = new Plant(null, null, "rose", "rose","rose", 5.5, "BC", "Burnaby");
        Plant plant2 = new Plant(null, null, "rose", "rose","rose", 5.5, "BC", "Burnaby");
        plants.add(plant1);
        plants.add(plant2);

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.ASC, "plantId"));
        Page<Plant> page = new PageImpl<>(plants, pageable, plants.size());

        when(plantService.getAllPlantsByFilters(pageNo, pageSize, search, province, city, price))
            .thenReturn(page);

        //ensure the models are correct and add params
        mockmvc.perform(MockMvcRequestBuilders.get("/marketplace")
            .session(session)
            .param("pageNo", pageNoString)
            .param("pageSize", pageSizeString)
            .param("search", search)
            .param("province", province)
            .param("city", city)
            .param("price", price))
            
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("protected/marketplace"))
            .andExpect(MockMvcResultMatchers.model().attribute("plants", Matchers.is(page.getContent())))
            .andExpect(MockMvcResultMatchers.model().attribute("pageNo", Matchers.is("0")))
            .andExpect(MockMvcResultMatchers.model().attribute("province", Matchers.is("all")))
            .andExpect(MockMvcResultMatchers.model().attribute("city", Matchers.is("all")))
            .andExpect(MockMvcResultMatchers.model().attribute("price", Matchers.is("all")))
            .andExpect(MockMvcResultMatchers.model().attribute("hasPrevPage", Matchers.is(page.hasPrevious())))
            .andExpect(MockMvcResultMatchers.model().attribute("hasNextPage", Matchers.is(page.hasNext())))
            .andExpect(MockMvcResultMatchers.model().attribute("totalPages", Matchers.is(page.getTotalPages())));

    }

    @Test
    void testGetonePlantOwner() throws Exception{
        Grower grower = new Grower("grower","password","email", 0, 0,0,0);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("session_user", grower);
        int pid = 1;

        Plant plant = new Plant(grower, null, "rose", "rose","rose", 5.5, "BC", "Burnaby");
        plant.setPlantId(pid);

        when(plantService.getPlantById(pid)).thenReturn(plant);

        mockmvc.perform(MockMvcRequestBuilders.get("/marketplace/plant/1")
            .session(session))
            
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("protected/viewPlant"))
            .andExpect(MockMvcResultMatchers.model().attribute("plant",Matchers.is(plant)))
            .andExpect(MockMvcResultMatchers.model().attribute("isOwner", Matchers.is(true)));
        
    }

    @Test
    void testGetonePlantNull() throws Exception{
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("session_user", null);

        mockmvc.perform(MockMvcRequestBuilders.get("/marketplace/plant/1")
        .session(session))
        .andExpect(MockMvcResultMatchers.status().is(302))
        .andExpect(MockMvcResultMatchers.view().name("main"));
    }

    @Test
    void testGetonePlant() throws Exception{
        Grower grower = new Grower("grower","password","email", 0, 0,0,0);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("session_user", grower);
        int pid = 1;

        Plant plant = new Plant(null, null, "rose", "rose","rose", 5.5, "BC", "Burnaby");
        plant.setPlantId(pid);

        when(plantService.getPlantById(pid)).thenReturn(plant);

        mockmvc.perform(MockMvcRequestBuilders.get("/marketplace/plant/1")
            .session(session))
            
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("protected/viewPlant"))
            .andExpect(MockMvcResultMatchers.model().attribute("plant",Matchers.is(plant)));
        
    }
}
