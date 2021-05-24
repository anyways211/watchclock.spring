package hwr.csa.watchclock.security;

import hwr.csa.watchclock.modell.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//aktueller Nutzer
public class MyUserPrincipal extends User implements UserDetails {

    protected MyUserPrincipal(User user){
        super(user.getPersonalNr(), user.getVorname(), user.getNachname(), user.getEmail(),
                user.getUsername(),user.getSollArbeitszeit(), user.getPassword(),
                user.getGeburtsdatum(), user.isIstAdmin());
    }

    //zuordnung der Rechte des jeweiligen Nutzers
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
        if (super.isIstAdmin()) {
            list.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        else {
            list.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        return list;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
