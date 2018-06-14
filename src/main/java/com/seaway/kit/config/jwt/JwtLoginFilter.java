package com.seaway.kit.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seaway.kit.config.Constants;
import com.seaway.kit.pojo.mongo.Users;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@Slf4j
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JwtLoginFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        try {
            Users user = new ObjectMapper().readValue(request.getInputStream(), Users.class);

            Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(),
                    user.getPassword(), new ArrayList<>());
            return authenticationManager.authenticate(authentication);
        } catch (Exception e) {
            log.error("Exception in attemptAuthentication, reason {}", e.getMessage());

            throw new BadCredentialsException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response, FilterChain chain, Authentication auth)
            throws IOException, ServletException {
        User user = (User) auth.getPrincipal();
        String token = getToken(user.getUsername());

        response.addHeader(Constants.JWT_HEADER, token);
    }

    public static String getToken(String username) {
        String subject = username;

        String token = Jwts.builder().setSubject(subject)
                .setExpiration(new Date(System.currentTimeMillis() + Constants.JWT_VALID_MS))
                .signWith(SignatureAlgorithm.HS512, Constants.JWT_SECRET_KEY)
                .compact();

        return Constants.JWT_ENCODE_PREFIX + token;
    }

}
