package com.proy.tecproBK.Security;

import com.proy.tecproBK.Security.Filter.JwtAuthenticationFilter;
import com.proy.tecproBK.Security.Jwt.JwtUtils;
import com.proy.tecproBK.Service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
public class SecurityConfig {
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    /**
     * metodo que configura la seguridad, es el principal metodo de todas los demas metodos
     */

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationManager authenticationManager) throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter=new JwtAuthenticationFilter(jwtUtils);
        jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);
        jwtAuthenticationFilter.setFilterProcessesUrl("/api/login");
        return httpSecurity
                .csrf(config -> config.disable()) //funcion lambda
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/api/deluser/holamundosecurity").permitAll();
                    auth.anyRequest().authenticated();
                })
                .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .addFilter(jwtAuthenticationFilter)
                .build();
    }

    /**
     * metodo de configuracion de un usuario para que tenga acceso en memoria
     */
    /*
    @Bean
    UserDetailsService userDetailsService(){
        InMemoryUserDetailsManager manager= new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("judas")
                .password("1234")
                .roles()
                .build());
        return manager;

    }*/


    /**
     * metodo que se encarga de encryptar la contrasenia  para que el metodo authenticacion mananger deje pasar y sea una
     * authenticacion correcta
     */
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * metodo de configuracion de la autenticacion mananger, es quien administra la autenticacion de los usuarios,
     * es quien dice este cumple con los requisitos, y para esto necesita que las contrasenias esten encryptadas
     * entonces creamos otro metodo passwordEncoder
     */
    @Bean
    AuthenticationManager authenticationManager(HttpSecurity httpSecurity, PasswordEncoder passwordEncoder) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder((passwordEncoder))
                .and()
                .build();
    }
    public static void main(String[]args){
        System.out.println(new BCryptPasswordEncoder().encode("J1o2E3l469304453"));
    }
}