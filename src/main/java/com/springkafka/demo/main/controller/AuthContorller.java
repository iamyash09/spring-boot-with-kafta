package com.springkafka.demo.main.controller;

import com.google.common.base.Strings;
import com.springkafka.demo.main.config.component.AuthTokenUtil;
import com.springkafka.demo.main.dto.LoginReqDTO;
import com.springkafka.demo.main.dto.LoginRespDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static com.google.common.base.Preconditions.checkArgument;

@RestController
@CrossOrigin
@RequestMapping("/rest/auth")
public class AuthContorller {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthTokenUtil authTokenUtil;

    /*
     * Perform user authentication
     *
     * @param username
     * @param username
     *
     * @return authorized token
     */
    @PostMapping(value = "/login")
    public ResponseEntity<?> authenticateLogin(@RequestBody LoginReqDTO authenticationRequest) {
        // Check valid input
        checkArgument(!Strings.isNullOrEmpty(authenticationRequest.getUsername()));
        checkArgument(!Strings.isNullOrEmpty(authenticationRequest.getPassword()));

        // Perform Authentication
        Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword())
                    );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate authorized user token
        String token = authTokenUtil.generateJwtToken(authentication);
        return ResponseEntity.ok(new LoginRespDTO(authenticationRequest.getUsername(), token));
    }
}