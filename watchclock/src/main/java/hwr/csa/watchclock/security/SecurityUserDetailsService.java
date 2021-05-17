package hwr.csa.watchclock.security;

import hwr.csa.watchclock.modell.User;
import hwr.csa.watchclock.modell.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SecurityUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException(username);
        }
        else{
            List<GrantedAuthority> authorities = getUserAuthority(user);
            return buildUserForAuthentication(user, authorities);
        }
    }

    private List<GrantedAuthority> getUserAuthority(User user) {
        Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
        if (user.isIstAdmin()) {
            roles.add(new SimpleGrantedAuthority("ADMIN"));
            roles.add(new SimpleGrantedAuthority("USER"));
        }
        else {
            roles.add(new SimpleGrantedAuthority("USER"));
        }
        return new ArrayList<>(roles);
    }

    private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                user.isEnabled(), true, true, true, authorities);
    }
}