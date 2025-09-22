package rutagastronomicamadrid.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig{


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/usuarios/login").permitAll()
                .requestMatchers("/api/usuarios").permitAll() // si das acceso público a GET /api/usuarios
                .anyRequest().permitAll() // temporalmente permitir todo para que arranque
                .and()
                .httpBasic();  // puedes usar basic solo mientras pruebas

        return http.build();
    }





}
