package hwr.csa.watchclock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserUebersichtController {

    @GetMapping("/userUebersicht")
    public ModelAndView start(){
        ModelAndView modelView = new ModelAndView();
        //List<User> users = userRepository.findAll();
        modelView.setViewName("userUebersicht");
        //modelView.addObject("users", users);
        return modelView;
    }
    @GetMapping("/userAendern")
    public ModelAndView aendern(){
        ModelAndView modelView = new ModelAndView();
        //List<User> users = userRepository.findAll();
        modelView.setViewName("userAendern");
        //modelView.addObject("users", users);
        return modelView;
    }
}
