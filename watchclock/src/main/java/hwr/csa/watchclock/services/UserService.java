package hwr.csa.watchclock.services;

import hwr.csa.watchclock.modell.User;
import hwr.csa.watchclock.modell.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.util.ObjectUtils.isEmpty;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

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

    public User checkAenderungen (User aktuell, User aenderung){
        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$!?%_.:,;'^&+*=])(?=\\S+$).{5,}";

        //wenn Feld gefüllt und anders als alter Wert, Feld in User ändern
        if (!aktuell.getVorname().equals(aenderung.getVorname()) && !isEmpty(aenderung.getVorname())){
            aktuell.setVorname(aenderung.getVorname());
        }
        if (!aktuell.getNachname().equals(aenderung.getNachname()) && !isEmpty(aenderung.getNachname())){
            aktuell.setNachname(aenderung.getNachname());
        }
        if (!aktuell.getUsername().equals(aenderung.getUsername()) && !isEmpty(aenderung.getUsername())){
           //wenn es den Usernamen nicht schon einmal gibt
            if (!usernameDoppelt(aenderung.getUsername())) {
               aktuell.setUsername(aenderung.getUsername());
           }
        }
        if (!aktuell.getEmail().equals(aenderung.getEmail()) && !isEmpty(aenderung.getEmail())){
            aktuell.setEmail(aenderung.getEmail());
        }
        if (!aktuell.getGeburtsdatum().equals(aenderung.getGeburtsdatum()) && !isEmpty(aenderung.getGeburtsdatum())){
            aktuell.setGeburtsdatum(aenderung.getGeburtsdatum());
        }
        if (aktuell.getSollArbeitszeit()!= aenderung.getSollArbeitszeit() && !isEmpty(aenderung.getSollArbeitszeit())){
            aktuell.setSollArbeitszeit(aenderung.getSollArbeitszeit());
        }
        if (!passwordEncoder.matches(aenderung.getPassword(), aktuell.getPassword())
                && !isEmpty(aenderung.getSollArbeitszeit()) && aenderung.getPassword().matches(pattern)) {
            aktuell.setPassword(passwordEncoder.encode(aenderung.getPassword()));
        }
        if(aktuell.isIstAdmin()!=aenderung.isIstAdmin()){
            if (!aenderung.isIstAdmin())
            {
                if(mehrAlsEinAdmin()){
                    aktuell.setIstAdmin(aenderung.isIstAdmin());
                }
            }
            else{
                aktuell.setIstAdmin(aenderung.isIstAdmin());
            }
        }

        return aktuell;
    }

    //schauen, ob eventuell schon ein User den neuen Usernamen enthält
    public boolean usernameDoppelt(String username){
        int anzUsername = 0;
        List<User> alleUser = userRepository.findAll();

        //alle User durchgehen und Username zählen
        for (int i = 0; i < alleUser.size(); i++) {
            User aktuell = alleUser.get(i);
            if (aktuell.getUsername().equals(username)) {
                anzUsername += 1;
            }
        }
        //Username Doppelt?
        if (anzUsername > 0) {
            return true;
        } else {
            return false;
        }

    }
}