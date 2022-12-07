package edu.mintic.empresamongo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import edu.mintic.empresamongo.config.auth.jwt.AuthGestionEx;
import edu.mintic.empresamongo.config.auth.jwt.AuthTokenFiltro;
import edu.mintic.empresamongo.config.auth.services.UserDetailsServiceImpl;

//import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsServiceImpl detailsServiceImpl;

    @Autowired
    AuthGestionEx authGestionEx;

    @Bean
    public AuthTokenFiltro authTokenFiltro() {
        return new AuthTokenFiltro();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(detailsServiceImpl).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(authGestionEx).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().antMatchers("/api/auth/**").permitAll()
                .antMatchers("/empleado/**").hasRole("ADMIN")
                .anyRequest().authenticated();

        http.addFilterBefore(authTokenFiltro(), UsernamePasswordAuthenticationFilter.class);
        /*
         * http.httpBasic(withDefaults())
         * .authorizeRequests()
         * .antMatchers("/empleados", "/empleado/**").hasRole("ADMIN")
         * .anyRequest().authenticated();
         */
    }

    /*
     * @Override
     * protected void configure(AuthenticationManagerBuilder auth) throws Exception
     * {
     * auth.inMemoryAuthentication()
     * .withUser("deivis").password("{noop}contrasena").roles("USER")
     * .and()
     * .withUser("maria").password("{noop}secreto").roles("ADMIN");
     * }
     */

}
