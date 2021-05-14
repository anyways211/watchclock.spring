package hwr.csa.watchclock.security;


import hwr.csa.watchclock.modell.User;
import hwr.csa.watchclock.modell.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String Email) throws UsernameNotFoundException {
       User user = new User();
        try {
           user = userRepository.findUserByEmail(Email);
       }catch(UsernameNotFoundException e){
        throw new UsernameNotFoundException("User not present");
       }
        return user;
    }
    public void createUser(UserDetails user) {
        userRepository.save((User) user);
    }
}
