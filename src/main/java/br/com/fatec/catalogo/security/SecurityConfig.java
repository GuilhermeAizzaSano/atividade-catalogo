package br.com.fatec.catalogo.security;

import br.com.fatec.catalogo.repositories.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/usuarios/novo", "/usuarios/editar/**", "/usuarios/excluir/**").hasRole("ADMIN")
                        .requestMatchers("/produtos/novo", "/produtos/editar/**", "/produtos/excluir/**").hasRole("ADMIN")
                        .requestMatchers("/categorias/novo", "/categorias/editar/**", "/categorias/excluir/**").hasRole("ADMIN")
                        .requestMatchers("/usuarios", "/produtos", "/categorias", "/dashboard").authenticated()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/dashboard", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UsuarioRepository repository) {
        return username -> {
            var usuario = repository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario nao encontrado: " + username));

            return User.builder()
                    .username(usuario.getUsername())
                    .password(usuario.getPassword())
                    .roles(usuario.getRole())
                    .build();
        };
    }
}
