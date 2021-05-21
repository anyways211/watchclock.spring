package hwr.csa.watchclock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AboutController {
    //Controller, der Anzeige der about Seite übernimmt
    @GetMapping("/about")
    public ModelAndView about(){
        ModelAndView modelView = new ModelAndView();
        modelView.setViewName("about");
        return modelView;
    }
}
