package com.springkafka.demo.main.config.component;

import com.springkafka.demo.main.config.AuthInMemoryUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This will validate the token
 *
 */
@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);
    @Autowired
    private AuthInMemoryUserService authInMemoryUserService;
    @Autowired
    private AuthTokenUtil authTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        try {
            // Skip check for login URL
            if( skipPreAuthUrl(request) ) {
                String token = parseToken(request);

                String username = authTokenUtil.getUsernameFromToken(token);

                // Once we get the token validate it.
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = this.authInMemoryUserService.loadUserByUsername(username);

                    // if token is valid configure Spring Security to manually set authentication
                    if (authTokenUtil.validateToken(token, userDetails)) {

                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(
                                        userDetails,
                                        null,
                                        userDetails.getAuthorities());

                        authentication.setDetails(
                                new WebAuthenticationDetailsSource().buildDetails(request)
                        );
                    /* After setting the Authentication in the context,
                        we specify that the current user is authenticated.
                        So it passes the Spring Security Configurations successfully.
                     */
                        SecurityContextHolder
                                .getContext()
                                .setAuthentication(authentication);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }
        chain.doFilter(request, response);
    }

    private String parseToken(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        // JWT Token is in the form "Bearer token". Remove Bearer word and get only the Token
        if (   StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        return null;
    }

    private boolean skipPreAuthUrl(HttpServletRequest request) {
        if( "/rest/auth/login".startsWith(request.getServletPath()) ){
            return false;
        }

        return true;
    }
}
