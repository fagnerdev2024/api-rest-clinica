package med.voll.api.infra.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return
                http.csrf(csrf -> csrf.disable())
                        .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                        .authorizeHttpRequests(req -> {
                            req.requestMatchers("/login").permitAll();
                            req.requestMatchers("/consultas").permitAll();
                            req.requestMatchers("/pacientes").permitAll();
                            req.requestMatchers("/medicos").permitAll();
                            req.requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll();
                                    /*.requestMatchers(HttpMethod.DELETE, "/medicos").hasRole("ADMIN")
                                    .requestMatchers(HttpMethod.DELETE, "/pacientes").hasRole("ADMIN")
                                    .requestMatchers(HttpMethod.POST, "/medicos").hasRole("ADMIN")
                                    .requestMatchers(HttpMethod.PUT, "/medicos").hasRole("ADMIN")
                                    .requestMatchers(HttpMethod.GET, "/pacientes").hasRole("ADMIN")
                                    .requestMatchers(HttpMethod.POST, "/consultas").hasRole("ADMIN")
                                    .requestMatchers(HttpMethod.GET, "/consultas").hasRole("ADMIN");*/
                            req.anyRequest().authenticated();
                        })
                        .build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
