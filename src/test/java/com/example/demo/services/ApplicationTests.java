package com.example.demo.services;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserDetailsServiceImpl;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ApplicationTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Test
    public void testUserServiceSave() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("testPassword");

        Mockito.when(passwordEncoder.encode("testPassword")).thenReturn("encodedPassword");
        Mockito.when(userRepository.save(user)).thenReturn(user);

        userService.save(user);

        Mockito.verify(passwordEncoder, Mockito.times(1)).encode("testPassword");
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    public void testUserDetailsServiceImplLoadUserByUsername() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("encodedPassword");
        user.setEnabled(true);

        Mockito.when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

        assertEquals("testUser", userDetailsService.loadUserByUsername("testUser").getUsername());
    }

    @Test
    public void testUserDetailsServiceImplLoadUserByUsernameUserNotFound() {
        Mockito.when(userRepository.findByUsername("nonexistentUser")).thenReturn(Optional.empty());

        Assertions.assertThrows(UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername("nonexistentUser"));
    }

    // Add more tests for other classes as needed
}
