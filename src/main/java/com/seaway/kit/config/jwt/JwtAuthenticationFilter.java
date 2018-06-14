package com.seaway.kit.config.jwt;

import com.seaway.kit.config.Constants;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Slf4j
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            String token = request.getHeader(Constants.JWT_HEADER);
            if (token != null && token.startsWith(Constants.JWT_ENCODE_PREFIX)) {
                Authentication authentication = getAuthentication(token);
                if (authentication != null) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (Exception e) {
            log.error("Exception in doFilterInternal, reason {}", e.getMessage());
        }

        chain.doFilter(request, response);
    }

    private Authentication getAuthentication(String token) {
        String subject = Jwts.parser().setSigningKey(Constants.JWT_SECRET_KEY)
                .parseClaimsJws(token.replace(Constants.JWT_ENCODE_PREFIX, ""))
                .getBody().getSubject();
        if (subject == null) {
            return null;
        }

        String username = subject;

        return new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
    }

}