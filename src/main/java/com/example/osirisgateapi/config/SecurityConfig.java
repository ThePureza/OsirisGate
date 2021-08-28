package com.example.osirisgateapi.api.config;

import com.example.osirisgateapi.security.JwtAuthFilter;
import com.example.osirisgateapi.security.JwtService;
import com.example.osirisgateapi.service.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CredentialService credentialService;

    @Autowired
    private JwtService jwtService;

    @Bean
    public OncePerRequestFilter jwtFilter(){
        return new JwtAuthFilter(jwtService, credentialService);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(credentialService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/v1/cargos/**")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/v1/causaDaMortes/**")
                .hasAnyRole( "ADMIN")
                .antMatchers("/api/v1/falecidos/**")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/v1/familias/**")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/v1/funerarias/**")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/v1/gavetas/**")
                .hasAnyRole("ADMIN")
                .antMatchers("/api/v1/lotes/**")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/v1/ossuarios/**")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/v1/programacoes/**")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/v1/quadras/**")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/v1/servicos/**")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/v1/setores/**")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/v1/usuarios/**")
                .hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/v1/credentials/**")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
        ;
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }
}
