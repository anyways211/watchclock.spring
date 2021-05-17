package hwr.csa.watchclock.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private SecurityUserDetailsService userDetailsService;



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers("/start").hasAnyAuthority("USER", "ADMIN")
                .antMatchers("/profil").hasAnyAuthority("ADMIN", "USER")
                .antMatchers("/zeitUebersicht").hasAnyAuthority("ADMIN", "USER")
                .antMatchers("/userUebersicht").hasAnyAuthority("ADMIN")
                .antMatchers("/userAendern").hasAuthority("ADMIN")
                .and().formLogin()
                .loginPage("/login")
                .permitAll();

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("resources/**", "/templates/**", "/static/**", "/css/**",
                        "/img/**", "/js/**", "/bootstrap/css/**", "bootstrap/js/**");
    }

}