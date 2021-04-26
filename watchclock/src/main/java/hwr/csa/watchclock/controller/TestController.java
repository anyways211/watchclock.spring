package hwr.csa.watchclock.controller;

import com.sun.istack.NotNull;
import hwr.csa.watchclock.modell.User;
import hwr.csa.watchclock.modell.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import javax.validation.Valid;
import java.util.List;
import java.util.Map;


@Controller
public class TestController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/userUebersicht")
    public ModelAndView start(){
        ModelAndView modelView = new ModelAndView();
        //List<User> users = userRepository.findAll();
        modelView.setViewName("userUebersicht");
        //modelView.addObject("users", users);
        return modelView;
    }

    @GetMapping("/zeitUebersicht")
    public ModelAndView startTest2(){
        ModelAndView modelView = new ModelAndView();
        modelView.setViewName("zeitUebersicht");
        return modelView;
    }

    @GetMapping("/login")
    public ModelAndView startLogin(){
        ModelAndView modelView = new ModelAndView();
        modelView.addObject("error", false);
        modelView.addObject("user", new User());
        modelView.setViewName("Login");
        //handle user inputs
        return modelView;
    }

    @Validated
    @PostMapping("/login")
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

    @GetMapping(value= {"/startZeiteintrag"})
    public ModelAndView startZeiteintrag(){
        ModelAndView modelView = new ModelAndView();
        modelView.setViewName("startZeiteintrag");
        return modelView;
    }
}
