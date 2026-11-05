package edu.marywood.eventapp.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfigIteration1 {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // Lecture Notes: Public pages
                        .requestMatchers("/", "/events").permitAll()

                        // Lecture Notes: Protect the event creation form
                        .requestMatchers(HttpMethod.GET, "/events/new").authenticated()

                        // Lecture Notes: Allow public event details
                        .requestMatchers(HttpMethod.GET, "/events/{id}").permitAll()

                        // Lecture Notes: Other requests require login
                        .anyRequest().authenticated()
                )
                // Lecture Notes: Use Spring Security's default login page
                .formLogin(Customizer.withDefaults());

        return http.build();
    }
}

