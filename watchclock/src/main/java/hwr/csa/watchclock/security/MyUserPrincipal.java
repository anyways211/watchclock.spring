package hwr.csa.watchclock.security;

import hwr.csa.watchclock.modell.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class MyUserPrincipal extends User implements UserDetails {

    protected MyUserPrincipal(User user){
        super(user.getUsername(), user.getPassword());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        if (super.isIstAdmin()) {
            return AuthorityUtils.createAuthorityList("ADMIN");
        }
        else {
            return AuthorityUtils.createAuthorityList("USER");
        }

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
