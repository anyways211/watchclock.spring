package hwr.csa.watchclock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StartController {

    @GetMapping(value= {"/startZeiteintrag"})
    public ModelAndView startZeiteintrag(){
        ModelAndView modelView = new ModelAndView();
        modelView.setViewName("startZeiteintrag");
        return modelView;
    }
}