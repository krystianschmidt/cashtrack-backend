package de.dhbw.plugins.security;

import de.dhbw.cleanproject.domain.user.UserApplication;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private final UserApplication userApplication;



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .csrf()
                    .disable()
                .authorizeRequests()
                    .antMatchers("/auth/register", "/auth/available").permitAll()
                    .anyRequest().authenticated()
                .and()
                .logout()
                    .logoutUrl("/auth/logout")
                    .deleteCookies("JSESSIONID")
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .permitAll()
                .and()
                .rememberMe()
                    .alwaysRemember(true)
                    .userDetailsService(userApplication)
                    .tokenValiditySeconds(7 * 24 * 60 * 60) // expiration time: 7 days
                    .key("AbcdefghiJklmNoPqRstUvXyz") // cookies will survive if restarted
                ;

    }

}
