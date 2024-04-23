package com.food.recipes.jwt;

import com.food.recipes.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomAuthenticationService implements UserDetailsService {

    private final UserRepository repository;

    public CustomAuthenticationService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = repository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("Username not found!")
        );
        log.info("Finding one user by email: {}", username);
        return new User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }
}
