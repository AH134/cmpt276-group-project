package ca.sfu.cmpt276.grow.with.you.controllers;

import java.nio.charset.Charset;

import static org.assertj.core.api.Assertions.assertThat;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import ca.sfu.cmpt276.grow.with.you.models.Grower;
import ca.sfu.cmpt276.grow.with.you.services.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class RegisterControllerTest {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
        MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void contextLoads() throws Exception{
        assertThat(userService).isNotNull();
    }

    @Test
    void testRegisterUser_passwordMatch() throws Exception {
        String username = "user";
        String email = "email";
        String p1 = "password";
        String p2 = "password";

        Grower user = new Grower(username, p1, email, 1000, 0, 0, 0);
        MockHttpSession session = new MockHttpSession();

        when(userService.getUserByUsername(username)).thenReturn(null);
        when(userService.getUserByEmail(email)).thenReturn(null);
        when(userService.createUser(user)).thenReturn(user);
        when(userService.getUserById(user.getUserId())).thenReturn(user);

        final ObjectMapper mapper = new ObjectMapper();
        final String jsonContent = mapper.writeValueAsString(user);
        final String compareJson = mapper.writeValueAsString(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/register")
            .content(jsonContent)
            .contentType(MediaType.APPLICATION_JSON)
            .session(session)
            .param("username", username)
            .param("password1", p1)
            .param("email", email)
            .param("roleRadio", "GROWER"))
            
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.content().json(compareJson))
            .andExpect(MockMvcResultMatchers.view().name("dashboard"));
    }

}
