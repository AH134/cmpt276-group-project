package ca.sfu.cmpt276.grow.with.you.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import ca.sfu.cmpt276.grow.with.you.models.Grower;
import ca.sfu.cmpt276.grow.with.you.services.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class MainControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testShowMainNull() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("main.html"));
    }

    @Test
    void testShowMainUser() throws Exception{
        Grower grower = new Grower("grower", "grower", "email@email.com", 1000, 1, 0, 0);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("session_user", grower);

        mockMvc.perform(MockMvcRequestBuilders.get("/").session(session))
            .andExpect(MockMvcResultMatchers.status().is(302))
            .andExpect(MockMvcResultMatchers.view().name("dashboard"));
    }

    @Test
    void testShowMainExplicitNull() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("session_user", null);

        mockMvc.perform(MockMvcRequestBuilders.get("/main")
            .session(session))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("main.html"));
    }

    
    @Test
    void testShowMainExplicitUser() throws Exception {
        Grower grower = new Grower("grower", "grower", "email@email.com", 1000, 1, 0, 0);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("session_user", grower);

        mockMvc.perform(MockMvcRequestBuilders.get("/main").session(session))
            .andExpect(MockMvcResultMatchers.status().is(302))
            .andExpect(MockMvcResultMatchers.view().name("dashboard"));
    }
}
