package hwr.csa.watchclock.controller;

import hwr.csa.watchclock.modell.User;
import hwr.csa.watchclock.modell.UserRepository;
import hwr.csa.watchclock.services.UserService;
import hwr.csa.watchclock.view.UserAendernView;
import hwr.csa.watchclock.view.UserUebersichtView;
import hwr.csa.watchclock.view.UserZufuegenView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.springframework.util.ObjectUtils.isEmpty;

//Controller der HTTP-Mapping für UserÜbersicht, UserZufuegen und UserAndern übernimmt!
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
        User aenderung = userAendernView.getUser();
        userAendernView.setError(false);
        userAendernView.setErrormsg("");
        userAendernView.setUser(null);

        aktuell = userService.checkAenderungen(aktuell, aenderung);
         userRepository.saveAndFlush(aktuell);

         if (aktuell.isIstAdmin() != aenderung.isIstAdmin()){
             userAendernView.setError(true);
             userAendernView.setErrormsg("Es muss mindestens ein Admin vorhanden sein");
         }

        if (!passwordEncoder.matches(aenderung.getPassword(), aktuell.getPassword()) &&
                !isEmpty(aenderung.getPassword())){

            userAendernView.setError(true);
            userAendernView.setErrormsg("Fehler beim Ändern des Passwortes. Kontrollieren Sie die Passwortvorgaben");
        }

        userAendernView.setUser(aktuell);
        modelView.setViewName("userAendern");
        modelView.addObject("view", userAendernView);
        return modelView;

    }

    //in Userübersicht ausgewählter User wird gelöscht, wenn er nicht der einzige Admin ist (es muss immer mind. 1 Admin in der DB vorhanden
    //sein, sonst können keine neuen Nutzer eingefügt werden
    @GetMapping("userUebersicht/userLoeschen/{personalNr}")
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
            }
        }
        userUebersichtView.setUsers(userRepository.findAll());
        modelView.setViewName("userUebersicht");
        modelView.addObject("view", userUebersichtView);
        return modelView;
    }

    @GetMapping("userUebersicht/userZufuegen")
    public ModelAndView GetUserZufuegen(){
        UserZufuegenView userZufuegenView = new UserZufuegenView();
        ModelAndView modelView = new ModelAndView();
        modelView.setViewName("userZufuegen");
        modelView.addObject("view", userZufuegenView);
        return modelView;

    }
    @PostMapping("userUebersicht/userZufuegen")
    public ModelAndView PostUserZufuegen(UserZufuegenView userZufuegenView){
        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$!?%_.:,;'^&+*=])(?=\\S+$).{5,}";

        try{
            User user = userZufuegenView.getUser();
            if(userService.leereFelder(user)){
                userZufuegenView.setError(true);
                userZufuegenView.setErrormsg("Bitte füllen Sie alle Felder aus");
            }
            else{
                if (user.getPassword().matches(pattern)){
                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                    userRepository.saveAndFlush(user);
                }
                else {
                    userZufuegenView.setError(true);
                    userZufuegenView.setErrormsg("Prüfen Sie die Passwortrichtlinien");
                }
            }


        } catch (Exception e){
            userZufuegenView.setError(true);
            userZufuegenView.setErrormsg("Bitte kontrollieren Sie ihre Eingaben! (Geburtsdatum bitte in folgendem Format angeben: yyyy-MM-dd)");
        }

        ModelAndView modelView = new ModelAndView();
        modelView.setViewName("userZufuegen");
        modelView.addObject("view", userZufuegenView);
        return modelView;

    }
}
