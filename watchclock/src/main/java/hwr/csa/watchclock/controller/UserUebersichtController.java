package hwr.csa.watchclock.controller;

import hwr.csa.watchclock.modell.User;
import hwr.csa.watchclock.modell.UserRepository;
import hwr.csa.watchclock.services.UserService;
import hwr.csa.watchclock.view.UserAendernView;
import hwr.csa.watchclock.view.UserUebersichtView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;

import static org.springframework.util.ObjectUtils.isEmpty;

//Controller der HTTP-Mapping für UserÜbersicht und UserAndern übernimmt!
@Controller
public class UserUebersichtController {
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    @GetMapping("/userUebersicht")
    public ModelAndView start(ModelAndView modelView){
        UserUebersichtView view= new UserUebersichtView();
        view.setUsers(userRepository.findAll());
        modelView.setViewName("userUebersicht");
        modelView.addObject("view", view);
        return modelView;
    }

    @GetMapping("userUebersicht/userAendern/{personalNr}")
    public ModelAndView getUserAendern(@PathVariable("personalNr") long personalNr){
        UserAendernView userAendernView = new UserAendernView();
        ModelAndView modelView = new ModelAndView();
        userAendernView.setUser(userRepository.findByPersonalNr(personalNr));
        modelView.setViewName("userAendern");
        modelView.addObject("view", userAendernView);
        return modelView;
    }

    @PostMapping("userUebersicht/userAendern/{personalNr}")
    public ModelAndView postUserAendern(@PathVariable("personalNr") long personalNr, UserAendernView userAendernView){
        ModelAndView modelView = new ModelAndView();
        User aktuell = userRepository.findByPersonalNr(personalNr);
        User geändert = userAendernView.getUser();
        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$!?%_.:,;'^&+*=])(?=\\S+$).{5,}";


        //wenn Feld gefüllt und anders als alter Wert, Feld in User ändern
        if (!aktuell.getVorname().equals(geändert.getVorname()) && !isEmpty(geändert.getVorname())){
            aktuell.setVorname(geändert.getVorname());
        }
        if (!aktuell.getNachname().equals(geändert.getNachname()) && !isEmpty(geändert.getNachname())){
            aktuell.setNachname(geändert.getNachname());
        }
        if (!aktuell.getUsername().equals(geändert.getUsername()) && !isEmpty(geändert.getUsername())){
            aktuell.setUsername(geändert.getUsername());
        }
        if (!aktuell.getEmail().equals(geändert.getEmail()) && !isEmpty(geändert.getEmail())){
            aktuell.setEmail(geändert.getEmail());
        }
        if (!aktuell.getGeburtsdatum().equals(geändert.getGeburtsdatum()) && !isEmpty(geändert.getGeburtsdatum())){
            aktuell.setGeburtsdatum(geändert.getGeburtsdatum());
        }
        if (aktuell.getSollArbeitszeit()!= geändert.getSollArbeitszeit() && !isEmpty(geändert.getSollArbeitszeit())){
            aktuell.setSollArbeitszeit(geändert.getSollArbeitszeit());
        }
        if (passwordEncoder.matches(geändert.getPassword(), aktuell.getPassword())
                && !isEmpty(geändert.getSollArbeitszeit()) && geändert.getPassword().matches(pattern)) {
            aktuell.setPassword(passwordEncoder.encode(geändert.getPassword()));
        }
        if(aktuell.isIstAdmin()!=geändert.isIstAdmin()){
            aktuell.setIstAdmin(geändert.isIstAdmin());
        }

        userRepository.saveAndFlush(aktuell);

        modelView.setViewName("userAendern");
        modelView.addObject("view", userAendernView);
        return modelView;
    }

    //in Userübersicht ausgewählter User wird gelöscht, wenn er nicht der einzige Admin ist (es muss immer mind. 1 Admin in der DB vorhanden
    //sein, sonst können keine neuen Nutzer eingefügt werden
    @GetMapping("userUebersich/userLoeschen/{personalNr}")
    public ModelAndView userLoeschen(@PathVariable("personalNr") long personalNr){
        ModelAndView modelView = new ModelAndView();
        UserUebersichtView userUebersichtView= new UserUebersichtView();
        //richtige ID angekommen?
        User zuLoeschen = userRepository.findByPersonalNr(personalNr);
        if(isEmpty(zuLoeschen)) {
            userUebersichtView.setError(true);
            userUebersichtView.setErrormsg("Etwas ist schief gelaufen, der zu löschende User existiert nicht");
        }
        else{
           // User user = zuLoeschen.get();
            //verhindern das letzter Admin gelöscht wird
            if (zuLoeschen.isIstAdmin() && !userService.mehrAlsEinAdmin()) {
                userUebersichtView.setError(true);
                userUebersichtView.setErrormsg("Es muss mindestens ein Admin vorhanden sein!");
            }
            else{
                userRepository.deleteByPersonalNr(personalNr);
                userUebersichtView.setError(true);
                userUebersichtView.setErrormsg(zuLoeschen.getUsername() + " wurde erfolgreich gelöscht!");
            }
        }
        userUebersichtView.setUsers(userRepository.findAll());
        modelView.setViewName("userUebersicht");
        modelView.addObject("view", userUebersichtView);
        return modelView;
    }
}
