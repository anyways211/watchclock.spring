package hwr.csa.watchclock.controller;

import hwr.csa.watchclock.modell.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController {

    @GetMapping(value= {"/bla","/test"})
    public ModelAndView start(){
        ModelAndView modelView = new ModelAndView();
        modelView.setViewName("Test");
        return modelView;
    }

    @GetMapping("/test2")
    public ModelAndView startTest2(){
        ModelAndView modelView = new ModelAndView();
        modelView.setViewName("Test2");
        User user = new User("Paß", "Annika", "annika.pass@gmx.de");
        modelView.addObject("user", user.getVorname());
        return modelView;
    }
}
