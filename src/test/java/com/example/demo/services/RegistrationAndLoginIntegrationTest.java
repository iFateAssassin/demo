package com.example.demo.services;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest
@AutoConfigureMockMvc
@SpringBootTest
public class RegistrationAndLoginIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    public void registerAndLoginTest() throws Exception {
        // Register a new user
        registerNewUser("newUser", "password");

        // Attempt to log in with the registered user
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .param("username", "newUser")
                        .param("password", "password")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/home"));

        // You can add more assertions or checks as needed for your specific application logic
    }

    private void registerNewUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEnabled(true);  // Assuming enabled by default in your application

        userService.save(user);
    }
}
