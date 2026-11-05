package edu.marywood.eventapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


@Configuration
public class SecurityConfigIteration4 {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // Lecture Notes: Public pages
                        .requestMatchers("/", "/events").permitAll()

                        // Lecture Notes: Only ADMIN can open the event creation form
                        .requestMatchers(HttpMethod.GET, "/events/new").hasRole("ADMIN")

                        // Lecture Notes: Allow public event details
                        .requestMatchers(HttpMethod.GET, "/events/{id}").permitAll()

                        // Lecture Notes: Only ADMIN can submit a new event
                        .requestMatchers(HttpMethod.POST, "/events").hasRole("ADMIN")

                        // Lecture Notes: Other requests require login
                        // Add explicit ADMIN rules for any edit/delete endpoints you implement.
                        .anyRequest().authenticated()
                )
                // Lecture Notes: Our custom GET /login page; Spring handles POST /login
                .formLogin(form -> form.loginPage("/login").permitAll())

                // Lecture Notes: POST /logout invalidates the session by default
                .logout(logout -> logout.logoutSuccessUrl("/login?logout").permitAll());

        return http.build();
    }

    // Lecture Notes: BCrypt hashes passwords and verifies submitted passwords
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Lecture Notes: Predefined classroom accounts (no registration/database needed)
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user = User.withUsername("user")
                .password(passwordEncoder.encode("user123"))
                .roles("USER")
                .build();

        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder.encode("admin123"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }
}
