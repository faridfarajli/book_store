package az.bookstore.secuirty;

import az.bookstore.service.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static az.bookstore.model.Permissions.*;
import static az.bookstore.model.Role.ADMIN;
import static az.bookstore.model.Role.USER;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

    private final CustomUserService customUserService;

    public SpringSecurity(CustomUserService customUserService) {
        this.customUserService = customUserService;
    }


    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .requestMatchers("/login/**","/admin/register/**","/user/register/**").permitAll()
                .requestMatchers("/admin/**").hasRole(ADMIN.name())
                .requestMatchers(GET,"/admin/books/{bookId}/readers").hasAnyAuthority(ADMIN_READERS.name())
                .requestMatchers(POST,"/admin/create/book/**").hasAnyAuthority(ADMIN_CREATE.name())
                .requestMatchers(DELETE,"/admin/books/{bookId}").hasAnyAuthority(ADMIN_DELETE.name())
                .requestMatchers("/user/**").hasRole(USER.name())
                    .requestMatchers(GET,"/user/student/books/").hasAnyAuthority(USER_READ.name())
                .requestMatchers(GET,"/user/**").hasAnyAuthority(USER_CURRENTLY_READING.name())
                .requestMatchers(AUTH_WHITELIST).permitAll()
        .anyRequest().authenticated()
        .and()
        .logout()
        .and()
        .csrf().disable();



        return http.build();
    }
    private static final String[] AUTH_WHITELIST = {
            "/admin/**",
            "/user/**",
            "/swagger-ui/**",
            "/swagger-ui.html"
    };



        @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(customUserService)
                .passwordEncoder(passwordEncoder());
    }

}

