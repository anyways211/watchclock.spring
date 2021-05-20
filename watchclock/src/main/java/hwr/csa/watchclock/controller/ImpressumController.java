package hwr.csa.watchclock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ImpressumController {
    @GetMapping("/impressum")
    public ModelAndView impressum(){
        ModelAndView modelView = new ModelAndView();
        modelView.setViewName("impressum");
        return modelView;
    }
}
