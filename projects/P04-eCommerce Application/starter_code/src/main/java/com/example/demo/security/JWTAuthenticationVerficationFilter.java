package com.example.demo.security;

import com.auth0.jwt.JWT;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@Component
public class JWTAuthenticationVerficationFilter extends BasicAuthenticationFilter {

    public JWTAuthenticationVerficationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        String header = req.getHeader(SecurityConstant.HEADER_STRING);

        if (header == null || !header.startsWith(SecurityConstant.TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest req) {
        String token = req.getHeader(SecurityConstant.HEADER_STRING);
        if (token != null) {
            String user = JWT.require(HMAC512(SecurityConstant.SECRET_KEY.getBytes())).build()
                    .verify(token.replace(SecurityConstant.PREFIX_TOKEN, ""))
                    .getSubject();
            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            }
            return null;
        }
        return null;
    }
}
