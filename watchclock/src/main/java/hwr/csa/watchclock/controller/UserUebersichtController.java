package hwr.csa.watchclock.controller;

import hwr.csa.watchclock.modell.User;
import hwr.csa.watchclock.modell.UserRepository;
import hwr.csa.watchclock.modell.ZeiteintragRepository;
import hwr.csa.watchclock.security.MyUserPrincipal;
import hwr.csa.watchclock.services.UserService;
import hwr.csa.watchclock.view.UserAendernView;
import hwr.csa.watchclock.view.UserUebersichtView;
import hwr.csa.watchclock.view.UserZufuegenView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;

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
    @Autowired
    ZeiteintragRepository zeiteintragRepository;

    //Controller der UserUebersicht anzeigt
    @GetMapping("/userUebersicht")
    public ModelAndView start(ModelAndView modelView){
        UserUebersichtView view= new UserUebersichtView();
        view.setUsers(userRepository.findAll());
        modelView.setViewName("userUebersicht");
        modelView.addObject("view", view);
        return modelView;
    }

    //Controller der UserAendern mit aktuellen Daten des ausgewählten Users anzeigt
    @GetMapping("userUebersicht/userAendern/{personalNr}")
    public ModelAndView getUserAendern(@PathVariable("personalNr") long personalNr){

        ModelAndView modelView = new ModelAndView();
        User user = (userRepository.findByPersonalNr(personalNr));
        UserAendernView userAendernView;
        userAendernView = new UserAendernView(user.getPersonalNr(),
                user.getVorname(), user.getNachname(), user.getUsername(), user.getEmail(),
                user.getGeburtsdatum().toString(), Integer.toString(user.getSollArbeitszeit()),
                user.getPassword(), user.isIstAdmin());
        modelView.setViewName("userAendern");
        modelView.addObject("view", userAendernView);
        return modelView;
    }

    //Controller der Änderungen von UserAendern annimmt, validiert und User ändert
    @PostMapping("userUebersicht/userAendern/{personalNr}")
    public ModelAndView postUserAendern(@PathVariable("personalNr") long personalNr, UserAendernView userAendernView){
        ModelAndView modelView = new ModelAndView();
        User aktuell = userRepository.findByPersonalNr(personalNr);
        userAendernView.setError(false);
        userAendernView.setErrormsg("");
        try{
            Date date = Date.valueOf(userAendernView.getGeburtsdatum());
            //user aus View-Attributen parsen
            User aenderung = new User((long) userAendernView.getPersonalNr(), userAendernView.getVorname(),
                userAendernView.getNachname(), userAendernView.getEmail(), userAendernView.getUsername(),
                Integer.parseInt(userAendernView.getSollarbeitszeit()), userAendernView.getPassword(),
                date, userAendernView.isIstAdmin());

            //gab es korrekte Aenderungen? dann diese in User aktuell schreiben
            aktuell = userService.checkAenderungen(aktuell, aenderung);
            //korrekte Änderungen in DB speichern
            userRepository.saveAndFlush(aktuell);

            if(userService.usernameDoppelt(aenderung.getUsername())){
                userAendernView.setError(true);
                userAendernView.setErrormsg("Es existiert bereits ein User mit dem eingegebenen Usernamen!");
            }

            //wurde bei Validierung festgestellt, das dem letzten Admin seine rechte entzogen werden sollen?
            if (aktuell.isIstAdmin() != aenderung.isIstAdmin()){
                userAendernView.setError(true);
                userAendernView.setErrormsg("Es muss mindestens ein Admin vorhanden sein!");
            }

            //wurde bei Validierung festgestellt, dass das Passwort nicht den Anforderungen entspricht
            if (!passwordEncoder.matches(aenderung.getPassword(), aktuell.getPassword()) &&
                    !isEmpty(aenderung.getPassword())){

                userAendernView.setError(true);
                userAendernView.setErrormsg("Fehler beim Ändern des Passwortes. Kontrollieren Sie die Passwortvorgaben");
            }

        }
        catch (Exception e){
            userAendernView.setError(true);
            userAendernView.setErrormsg("Bitte kontrollieren Sie ihre Eingaben und füllen Sie alle Felder aus!");
        }

        //Änderungen in View-Klasse schreiben
        userAendernView.setVorname(aktuell.getVorname());
        userAendernView.setNachname(aktuell.getNachname());
        userAendernView.setUsername(aktuell.getUsername());
        userAendernView.setEmail(aktuell.getEmail());
        userAendernView.setSollarbeitszeit(String.valueOf(aktuell.getSollArbeitszeit()));
        userAendernView.setPassword(aktuell.getPassword());
        userAendernView.setIstAdmin(aktuell.isIstAdmin());

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
            MyUserPrincipal principal = (MyUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            //Verhindern das User das eigene Konto löscht
            if (zuLoeschen.getPersonalNr() == principal.getPersonalNr()) {
                userUebersichtView.setError(true);
                userUebersichtView.setErrormsg("Sie können nicht Ihr eigenes Konto löschen!");
            }
            //verhindern das letzter Admin gelöscht wird
            else if (zuLoeschen.isIstAdmin() && !userService.mehrAlsEinAdmin()) {
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

    //Controller der userZufuegen anzeigt
    @GetMapping("userUebersicht/userZufuegen")
    public ModelAndView GetUserZufuegen(){
        UserZufuegenView userZufuegenView = new UserZufuegenView();
        ModelAndView modelView = new ModelAndView();
        modelView.setViewName("userZufuegen");
        modelView.addObject("view", userZufuegenView);
        return modelView;

    }

    //Controller der eingaben validiert und neuen User speichert
    @PostMapping("userUebersicht/userZufuegen")
    public ModelAndView PostUserZufuegen(UserZufuegenView userZufuegenView){
        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$!?%_.:,;'^&+*=])(?=\\S+$).{5,}";

        try{
            Date date = Date.valueOf(userZufuegenView.getGeburtsdatum());
            //user aus View-Attributen parsen
            User user = new User((long) userZufuegenView.getPersonalNr(), userZufuegenView.getVorname(),
                    userZufuegenView.getNachname(), userZufuegenView.getEmail(), userZufuegenView.getUsername(),
                    Integer.parseInt(userZufuegenView.getSollarbeitszeit()), userZufuegenView.getPassword(),
                    date, userZufuegenView.isIstAdmin());

            //sind alle Felder gefüllt?
            if(userService.leereFelder(user)){
                userZufuegenView.setError(true);
                userZufuegenView.setErrormsg("Bitte füllen Sie alle Felder aus");
            }
            else{
                //entspricht Passwort den Richtlinien
                if (user.getPassword().matches(pattern) && !userService.usernameDoppelt(user.getUsername())){
                    //passwort codieren
                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                    //neuen User speichern
                    userRepository.saveAndFlush(user);
                }
                else if (!user.getPassword().matches(pattern)){
                    userZufuegenView.setError(true);
                    userZufuegenView.setErrormsg("Prüfen Sie die Passwortrichtlinien");
                }
                else if (userService.usernameDoppelt(user.getUsername())){
                    userZufuegenView.setError(true);
                    userZufuegenView.setErrormsg("Es existiert bereits ein User mit diesem Usernamen!");
                }
            }

        } catch (Exception e){
            userZufuegenView.setError(true);
            userZufuegenView.setErrormsg("Bitte kontrollieren Sie ihre Eingabenund füllen Sie alle Felder aus!");
        }

        ModelAndView modelView = new ModelAndView();
        modelView.setViewName("userZufuegen");
        modelView.addObject("view", userZufuegenView);
        return modelView;

    }
}
