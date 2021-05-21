package hwr.csa.watchclock.controller;

import hwr.csa.watchclock.modell.User;
import hwr.csa.watchclock.modell.UserRepository;
import hwr.csa.watchclock.security.MyUserPrincipal;
import hwr.csa.watchclock.view.PasswortAendernView;
import hwr.csa.watchclock.view.ProfilView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProfilController {

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/profil")
    public ModelAndView zeigeProfil(){
        MyUserPrincipal principal = (MyUserPrincipal) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        ProfilView profilView = new ProfilView();
        profilView.setUser(principal);
        ModelAndView modelView = new ModelAndView();
        modelView.addObject("view", profilView);
        modelView.setViewName("profil");
        return modelView;
    }
    @GetMapping("/profil/passwortAendern")
    public ModelAndView passwortAendern(){
        ModelAndView modelView = new ModelAndView();
        PasswortAendernView passwortAendernView = new PasswortAendernView();
        modelView.setViewName("passwortAendern");
        modelView.addObject("view", passwortAendernView);
        return modelView;
    }

    @PostMapping("/profil/passwortAendern")
    public ModelAndView postPasswortAendern(PasswortAendernView passwortAendernView){
        MyUserPrincipal principal = (MyUserPrincipal) SecurityContextHolder.getContext()
                                        .getAuthentication().getPrincipal();

        //pattern --> passwort muss eine Zahl, einen kleinen & einen großen Buchstaben,
        //ein Sonderzeichen, kein Leerzeichen und mindestens 5 Zeichen enthalten
        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$!?%_.:,;'^&+*=])(?=\\S+$).{5,}";

        //altes Passwort = Passwort vom aktuellen Nutzer
        //neuesPasswort = neuesPasswortW
        //genügt neuesPasswort den Ansprüchen?
        if(passwordEncoder.matches(passwortAendernView.getAltesPasswort(), principal.getPassword())
            && passwortAendernView.getNeuesPasswort().equals(passwortAendernView.getNeuesPasswortW())
                && passwortAendernView.getNeuesPasswort().matches(pattern)){

            User user = userRepository.findByPersonalNr(principal.getPersonalNr());
            user.setPassword(passwordEncoder.encode(passwortAendernView.getNeuesPasswort()));
            userRepository.saveAndFlush(user);

            passwortAendernView.setError(true);
            passwortAendernView.setErrormsg("Geändert!");
            passwortAendernView.setAltesPasswort("");
            passwortAendernView.setNeuesPasswort("");
            passwortAendernView.setNeuesPasswortW("");

        }
        else{
            passwortAendernView.setAltesPasswort("");
            passwortAendernView.setNeuesPasswort("");
            passwortAendernView.setNeuesPasswortW("");
            passwortAendernView.setError(true);
        }

        ModelAndView modelView = new ModelAndView();
        modelView.addObject("view", passwortAendernView);
        modelView.setViewName("passwortAendern");
        return modelView;

    }
}
