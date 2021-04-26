package hwr.csa.watchclock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ZeitUebersichtController {

    @GetMapping("/zeitUebersicht")
    public ModelAndView startTest2(){
        ModelAndView modelView = new ModelAndView();
        modelView.setViewName("zeitUebersicht");
        return modelView;
    }
}
