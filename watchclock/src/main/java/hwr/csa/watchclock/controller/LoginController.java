package hwr.csa.watchclock.controller;

import hwr.csa.watchclock.modell.User;
import hwr.csa.watchclock.modell.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("login")
    public ModelAndView startLogin(){
        ModelAndView modelView = new ModelAndView();
        modelView.addObject("error", false);
        modelView.addObject("user", new User());
        modelView.setViewName("Login");
        //handle user inputs
        return modelView;
    }

    @Validated
    @PostMapping("login")
    public ModelAndView Login(@Valid @ModelAttribute("user") User user, BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            ModelAndView modelView = new ModelAndView();
            modelView.setViewName("Login");
            return modelView;
        }else {
            if (user.getEmail().equals("abc@gmx.de") && user.getPasswort().equals("1234")) {
                ModelAndView modelView = new ModelAndView();
                modelView.addObject("user", new User());
                modelView.setViewName("zeitUebersicht");
                return modelView;

            } else {
                ModelAndView modelView = new ModelAndView();
                modelView.addObject("user", new User());
                modelView.addObject("error", Boolean.TRUE);
                modelView.setViewName("Login");
                return modelView;
            }
        }
    }

}
