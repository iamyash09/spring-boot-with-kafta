package com.springkafka.demo.main.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service class for In memory users
 * This unpack and validate the jwt token offline
 */
@Service
public class AuthInMemoryUserService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(AuthInMemoryUserService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if("admin".equals(username)) {
            return User.builder()
                .username("admin")
                .password(new BCryptPasswordEncoder().encode("12345"))
                .roles("ADMIN")
                .build();
        }
        else if("user".equals(username)) {
            return User.builder()
                    .username("user")
                    .password(new BCryptPasswordEncoder().encode("12345"))
                    .roles("USER")
                    .build();
        }
            logger.error("No user found");
            throw new UsernameNotFoundException("Bad credentials");
    }
}
