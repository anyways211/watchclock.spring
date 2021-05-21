package hwr.csa.watchclock.controller;

import hwr.csa.watchclock.modell.Zeiteintrag;
import hwr.csa.watchclock.modell.ZeiteintragRepository;
import hwr.csa.watchclock.view.ZeitAendernView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Time;
import java.sql.Date;

@Controller
public class ZeitUebersichtController {

    @Autowired
    ZeiteintragRepository zeiteintragRepository;

    @GetMapping("/zeitUebersicht")
    public ModelAndView startTest2(){
        ModelAndView modelView = new ModelAndView();
        modelView.setViewName("zeitUebersicht");
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
    public ModelAndView GetZeitAendern(@PathVariable("zeiteintragNr") int zeiteintragNr, ModelAndView modelAndView){

        ZeitAendernView zeitAendernView = new ZeitAendernView();
        Zeiteintrag zeiteintrag = zeiteintragRepository.findByEintragNr(zeiteintragNr);

        zeitAendernView.setZeitEintragNr(zeiteintragNr);
        zeitAendernView.setTag(new Date(zeiteintrag.getVon().getTime()));
        zeitAendernView.setVon(new Time(zeiteintrag.getVon().getTime()));
        zeitAendernView.setBis(new Time(zeiteintrag.getVon().getTime()));

        modelAndView.addObject("view", zeitAendernView);
        modelAndView.setViewName("zeitAendern");
        return modelAndView;
    }


}
