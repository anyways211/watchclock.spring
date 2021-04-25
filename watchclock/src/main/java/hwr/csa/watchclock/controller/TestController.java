package hwr.csa.watchclock.controller;

import hwr.csa.watchclock.modell.User;
import hwr.csa.watchclock.modell.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


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
        //modelView.addObject("user", new User());
        modelView.setViewName("Login");
        //handle user inputs
        return modelView;
    }

    @PostMapping("/login")
    public ModelAndView Login(@ModelAttribute("user") User user){
        if(user.getEmail().equals("abc@gmx.de") && user.getPasswort().equals("1234")){
            ModelAndView modelView = new ModelAndView();
            modelView.addObject("user", new User());
            modelView.setViewName("Test2");
            return modelView;
        }else {
            ModelAndView modelView = new ModelAndView();
            modelView.addObject("user", new User());
            modelView.addObject("error", Boolean.TRUE);
            modelView.setViewName("Login");
            return modelView;
        }


    }
}
