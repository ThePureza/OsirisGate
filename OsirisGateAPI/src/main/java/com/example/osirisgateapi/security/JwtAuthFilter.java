package com.example.osirisgateapi.security;

import com.example.osirisgateapi.model.entity.Credential;
import com.example.osirisgateapi.service.CredentialService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthFilter  extends OncePerRequestFilter {

    private JwtService jwtService;
    private CredentialService credentialService;

    public JwtAuthFilter( JwtService jwtService, CredentialService credentialService ) {
        this.jwtService = jwtService;
        this.credentialService = credentialService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            FilterChain filterChain) throws ServletException, IOException {

        String authorization = httpServletRequest.getHeader("Authorization");

        if( authorization != null && authorization.startsWith("Bearer")){
            String token = authorization.split(" ")[1];
            boolean isValid = jwtService.tokenValido(token);

            if(isValid){
                String loginCredential = jwtService.obterLoginCredential(token);
                UserDetails credential = credentialService.loadUserByUsername(loginCredential);
                UsernamePasswordAuthenticationToken user = new
                        UsernamePasswordAuthenticationToken(credential,null,
                        credential.getAuthorities());
                user.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(user);
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
