package hwr.csa.watchclock.controller;

import hwr.csa.watchclock.modell.User;
import hwr.csa.watchclock.modell.UserRepository;
import hwr.csa.watchclock.modell.Zeiteintrag;
import hwr.csa.watchclock.modell.ZeiteintragRepository;
import hwr.csa.watchclock.security.MyUserPrincipal;
import hwr.csa.watchclock.services.ZeitStopService;
import hwr.csa.watchclock.services.UserService;
import hwr.csa.watchclock.view.PasswortAendernView;
import hwr.csa.watchclock.view.StartView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class StartController {

    @Autowired
    ZeiteintragRepository zeiteintragRepository;
    @Autowired
    ZeitStopService zeitStopService;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/start")
    public ModelAndView startseite(){
        // aktuellen Nutzer bekommen
        MyUserPrincipal principal = (MyUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByPersonalNr(principal.getPersonalNr());
        // schauen ob User Zeiteinträge mit Start aber ohne Ende haben
        List<Zeiteintrag> start = zeiteintragRepository.findByBisAndUser(null, user);
        boolean istStart = false;
        // wenn es Zeiteinträge mit Start aber ohne Ende gibt, istStart auf true setzen
        if(start.size()!=0){
            istStart = true;
        }
        // View erstellen und Objekte mitgeben
        ModelAndView modelView = new ModelAndView();
        StartView startView = new StartView();
        startView.setIstStart(istStart);
        modelView.setViewName("start");
        modelView.addObject("view", startView);
        return modelView;
    }

    @PostMapping("/startZeiteintrag")
    public String startZeiteintrag(StartView startView){
        // Startzeit festhalten
        LocalDateTime startZeit = LocalDateTime.now();
        // aktuellen Nutzer bekommen
        MyUserPrincipal principal = (MyUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByPersonalNr(principal.getPersonalNr());
        // neuen Zeiteintrag mit Startzeit erstellen und speichern in DB
        Zeiteintrag neuerZeiteintrag = new Zeiteintrag(Timestamp.valueOf(startZeit), null, "", user);
        zeiteintragRepository.saveAndFlush(neuerZeiteintrag);
        return "redirect:/start";
    }

    @PostMapping("/stopZeiteintrag")
    public String stopZeiteintrag(StartView startView){
        // Stopzeit festhalten
        LocalDateTime stopZeit = LocalDateTime.now();
        // aktuellen Nutzer bekommen
        MyUserPrincipal principal = (MyUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByPersonalNr(principal.getPersonalNr());
        // Zeiteintrag des aktuellen Users mit Start- aber ohne Endzeit  bekommen
        Zeiteintrag start = zeiteintragRepository.findByBisAndUser(null, user).get(0);
        // Funktion aufrufen, die Zeiteinträge erstellt und speichert (falls sich Zeit über mehrere Tage erstreckt)
        zeitStopService.manageSpeichernZeiteintraege(startView.getKommentar(), start.getVon(), Timestamp.valueOf(stopZeit), user, start);
        return "redirect:/start";
    }

}
