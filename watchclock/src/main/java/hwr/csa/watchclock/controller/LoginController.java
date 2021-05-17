package hwr.csa.watchclock.controller;

import hwr.csa.watchclock.modell.User;
import hwr.csa.watchclock.modell.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

   @GetMapping("/login")
    public ModelAndView startLogin(){
        ModelAndView modelView = new ModelAndView();
        modelView.addObject("user", new User());
        modelView.setViewName("Login");
        return modelView;
    }
}

