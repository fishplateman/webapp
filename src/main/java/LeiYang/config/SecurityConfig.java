package LeiYang.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//import org.springframework.security.web.header.Header;
//import org.springframework.security.web.header.writers.StaticHeadersWriter;
//
//import java.util.Arrays;
//
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(password());
    }

    @Bean
    PasswordEncoder password() {
        return new BCryptPasswordEncoder();
    }

    //permit All URL
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable();
//}
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/healthz").anonymous()
                .antMatchers("/health").anonymous()
                .antMatchers(
                        HttpMethod.POST,
                        "/v1/user"

                ).anonymous()
                .antMatchers(
                        HttpMethod.GET,
                        "/v2/product/{productId}"

                ).permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic();
    }

}
