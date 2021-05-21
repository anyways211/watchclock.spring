package hwr.csa.watchclock.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackageClasses = SecurityUserDetailsService.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private UserDetailsService userDetailsService;

    //Festlegen, welche Klasse die Kontrolle von Passwort und Nutzername übernimmt
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService);
    }

    //Schutz für Seiten aufbauen, Rechte je nach Rolle (in MyUserPrincipal entschieden) vergeben
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers("/start/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .antMatchers("/profil/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                .antMatchers("/zeitUebersicht/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                .antMatchers("/userUebersicht/**").hasAnyAuthority("ROLE_ADMIN")
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/login").defaultSuccessUrl("/start", true).permitAll()
                .and().logout().logoutUrl("/logout").permitAll();
    }


    //Impressum-,Fehler-,Aboutseite und alle CSS-/Bilddateien von Schutz ausnehmen
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/impressum", "/error", "/about", "resources/**", "/templates/**", "/static/**", "/css/**",
                        "/img/**", "/js/**", "/bootstrap/css/**", "bootstrap/js/**");
    }


}