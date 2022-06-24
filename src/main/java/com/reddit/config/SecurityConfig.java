package com.reddit.config;

import com.reddit.config.security.admin.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

       // String path = "/api/v1/";

        @Autowired
        private JwtFilter jwtFilter;
        
        @Autowired
        private PasswordEncoder passwordEncoder;

        @Autowired
        UserDetailsServiceImpl userDetailsService;

//

        @Override
        public void configure(WebSecurity web) {
                System.out.println("CALLING WEB SECURITY CONFIGURATION ");
        }

        public SecurityConfig() {
            super();
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
                System.out.println("IN SPRINF SECURITY CONFIGURATION NOW...");
            http.cors();
            http.csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/api/auth/**")
                    .permitAll()
                    .antMatchers(HttpMethod.GET, "/api/subreddit")
                    .permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .exceptionHandling()
                    .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
                System.out.println("DONE WITH SPRING CONFIGURATION.....");
        }

        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
                System.out.println("CALLING AUTHENTICATION MANAGER BEAN....");
            return super.authenticationManagerBean();
        }

        @Override
        @Autowired
        public void configure(AuthenticationManagerBuilder auth) throws Exception {
                System.out.println("CALLING AUTHENTICATION MANAGER BUILDER ...");
            auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        }

}