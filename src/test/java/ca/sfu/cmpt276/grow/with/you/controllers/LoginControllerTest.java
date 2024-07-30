package ca.sfu.cmpt276.grow.with.you.controllers;

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
import org.springframework.util.LinkedMultiValueMap;

import ca.sfu.cmpt276.grow.with.you.models.Grower;
import ca.sfu.cmpt276.grow.with.you.services.UserService;
import ca.sfu.cmpt276.utils.enums.UserError;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void testGetLoginNull() throws Exception{
        MockHttpSession session = new MockHttpSession();
        
        when(userService.getUserBySession(session)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/login"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("login"));

    }

    @Test
    void testGetLoginSuccess() throws Exception{
        Grower grower = new Grower("username","password","email@gmail.com",100,0,0,0);
        MockHttpSession session = new MockHttpSession();
        
        when(userService.getUserBySession(session)).thenReturn(grower);

        mockMvc.perform(MockMvcRequestBuilders.get("/login"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("dashboard"));

    }

    @Test
    void testLoginSuccess() throws Exception {
        String username = "username";
        String password = "password";

        LinkedMultiValueMap<String, String> loginData = new LinkedMultiValueMap<>();
        loginData.add("username","username");
        loginData.add("password","password");

        Grower grower = new Grower("username","password","email@gmail.com",100,0,0,0);
        
        MockHttpSession session = new MockHttpSession();

        when(userService.getUserByUsernameAndPassword(username, password)).thenReturn(grower);

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
            .session(session)
            .params(loginData))

            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("redirect:dashboard"));

    }

    @Test
    void testLoginFail() throws Exception {
        String username = "username";
        String password = "password";

        LinkedMultiValueMap<String, String> loginData = new LinkedMultiValueMap<>();
        loginData.add("username","username");
        loginData.add("password","password");

        MockHttpSession session = new MockHttpSession();
        UserError error = UserError.USER_NOT_FOUND;

        when(userService.getUserByUsernameAndPassword(username, password)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
            .session(session)
            .params(loginData))

            .andExpect(MockMvcResultMatchers.status().is(error.getStatusCode()))
            .andExpect(MockMvcResultMatchers.view().name("login"))
            .andExpect(MockMvcResultMatchers.model().attribute("error", Matchers.is(error.getMessage())));

    }

    @Test
    void testLogout() throws Exception {
        MockHttpSession session = new MockHttpSession();

        mockMvc.perform(MockMvcRequestBuilders.get("/logout").session(session))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("redirect:main"));
    }
}
