package hwr.csa.watchclock.controller;

import hwr.csa.watchclock.modell.User;
import hwr.csa.watchclock.modell.UserRepository;
import hwr.csa.watchclock.modell.Zeiteintrag;
import hwr.csa.watchclock.modell.ZeiteintragRepository;
import hwr.csa.watchclock.services.ZeitStopService;
import hwr.csa.watchclock.view.ZeitAendernView;
import hwr.csa.watchclock.security.MyUserPrincipal;
import hwr.csa.watchclock.view.StartView;
import hwr.csa.watchclock.view.ZeitUebersichtView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Time;
import java.sql.Date;
import java.util.List;

@Controller
public class ZeitUebersichtController {
    @Autowired
    ZeitStopService zeitStopService;

    @Autowired
    ZeiteintragRepository zeiteintragRepository;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/zeitUebersicht")
    public ModelAndView zeitUebersicht(){
        // aktuellen Nutzer bekommen
        MyUserPrincipal principal = (MyUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByPersonalNr(principal.getPersonalNr());
        // zeiten für diesen User bekommen
        List<Zeiteintrag> zeiten = zeiteintragRepository.findAllByUserOrderByBisDesc(user);
        int index =0;
        boolean nullvalue = false;
        // Index des Eintrags ohne Biszeit finden, falls vorhanden
        for(Zeiteintrag zeiteintrag:zeiten){
            try{
                if(zeiteintrag.getBis().equals(null)){
                    zeiten.remove(zeiteintrag);
                }
            }catch(Exception e){
                nullvalue =true;
                index = zeiten.indexOf(zeiteintrag);
            }
        }
        // Eintrag ohne Biszeit rausnehmen
        if(nullvalue) zeiten.remove(index);
        // View erstellen und Variablen hinzufügen
        ModelAndView modelView = new ModelAndView();
        ZeitUebersichtView zeitUebersichtView = new ZeitUebersichtView();
        zeitUebersichtView.setZeitEintraege(zeiten);
        modelView.setViewName("zeitUebersicht");
        modelView.addObject("view", zeitUebersichtView);
        return modelView;
    }

    @GetMapping("/zeitUebersicht/zeitAendern/{zeiteintragNr}")
    public ModelAndView GetZeitAendern(@PathVariable("zeiteintragNr") int zeiteintragNr){
        ModelAndView modelAndView = new ModelAndView();
        ZeitAendernView zeitAendernView = new ZeitAendernView();
        Zeiteintrag zeiteintrag = zeiteintragRepository.findByEintragNr(zeiteintragNr);

        zeitAendernView.setZeitEintragNr(zeiteintragNr);
        zeitAendernView.setTag(new Date(zeiteintrag.getVon().getTime()));
        zeitAendernView.setVon(new Time(zeiteintrag.getVon().getTime()));
        zeitAendernView.setBis(new Time(zeiteintrag.getBis().getTime()));
        zeitAendernView.setKommentar(zeiteintrag.getKommentar());

        modelAndView.addObject("view", zeitAendernView);
        modelAndView.setViewName("zeitAendern");
        return modelAndView;
    }

    @PostMapping("/zeitUebersicht/zeitAendern/{zeiteintragNr}")
    public ModelAndView GetZeitAendern(@PathVariable("zeiteintragNr") int zeiteintragNr, ZeitAendernView zeitAendernView){
        ModelAndView modelAndView = new ModelAndView();

        Zeiteintrag aktuell = zeiteintragRepository.findByEintragNr(zeiteintragNr);
        //aenderungen in aktuell schreiben
        aktuell = zeitStopService.checkAenderung(aktuell, zeitAendernView);
        //aktuell in DB schreiben
        zeiteintragRepository.saveAndFlush(aktuell);

        zeitAendernView.setZeitEintragNr(zeiteintragNr);
        zeitAendernView.setTag(new Date(aktuell.getVon().getTime()));
        zeitAendernView.setVon(new Time(aktuell.getVon().getTime()));
        zeitAendernView.setBis(new Time(aktuell.getBis().getTime()));

        modelAndView.addObject("view", zeitAendernView);
        modelAndView.setViewName("zeitAendern");
        return modelAndView;
    }

    @GetMapping("zeitUebersicht/zeitLoeschen/{zeiteintragNr}")
    public String zeitLoeschen(@PathVariable("zeiteintragNr") int zeiteintragNr){

        zeiteintragRepository.deleteByEintragNr(zeiteintragNr);


        return "redirect:/zeitUebersicht";
    }


}
