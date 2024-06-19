package com.endava.twitter.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Configuration
@EnableWebMvc
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceCustom userDetailsServiceCustom;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        CustomFilter customFilter = new CustomFilter();
        customFilter.setAuthenticationManager(authenticationManager());

        http
                .logout()
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .deleteCookies("userId")

                .and()

                .csrf().disable()

                .addFilterAt(
                        customFilter,
                        UsernamePasswordAuthenticationFilter.class
                )

                .authorizeRequests()

                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers(HttpMethod.POST, "/logout").authenticated()

                .antMatchers("/timeline").permitAll()
                .antMatchers("/swagger-ui").permitAll()

                .antMatchers("/tweet/{tweetId}").permitAll()
                .antMatchers("/tweets/All").permitAll()
                .antMatchers("tweets/page").permitAll()

                .antMatchers("/friends/create").hasRole("USER")
                .antMatchers("/friends/destroy").hasRole("USER")

                .antMatchers("/tweets").authenticated()
                .antMatchers("/tweets/{tweetId}").authenticated()
                .antMatchers("/favorites/create").authenticated()
                .antMatchers("/favorites/create").authenticated();

    }



    @Bean
    DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.userDetailsServiceCustom);
        return daoAuthenticationProvider;
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
