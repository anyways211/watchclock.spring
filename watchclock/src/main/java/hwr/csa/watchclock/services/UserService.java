package hwr.csa.watchclock.services;

import hwr.csa.watchclock.modell.User;
import hwr.csa.watchclock.modell.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public boolean mehrAlsEinAdmin() {
        int anzAdmins = 0;
        List<User> alleUser = userRepository.findAll();

        //alle User durchgehen und Admins zählen
        for (int i = 0; i < alleUser.size(); i++) {
            User aktuell = alleUser.get(i);
            if (aktuell.isIstAdmin()) {
                anzAdmins += 1;
            }
        }
        //gibt es mehr als einen Admin?
        if (anzAdmins > 1) {
            return true;
        } else {
            return false;
        }
    }
}