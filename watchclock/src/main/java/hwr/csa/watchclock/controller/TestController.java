package hwr.csa.watchclock.controller;

import hwr.csa.watchclock.modell.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController {

    @GetMapping("/test")
    public ModelAndView start(){
        ModelAndView modelView = new ModelAndView();
        modelView.setViewName("Test");
        return modelView;
    }

    @GetMapping("/test2")
    public ModelAndView startTest2(){
        ModelAndView modelView = new ModelAndView();
        modelView.setViewName("Test2");
        User user = new User("Paß", "Annika", "annika.pass@gmx.de", "1234");
        modelView.addObject("user", user.getVorname());
        return modelView;
    }

    @GetMapping("/login")
    public ModelAndView startLogin(){
        ModelAndView modelView = new ModelAndView();
        modelView.addObject("user", new User());
        modelView.setViewName("Login");
        //handle user inputs


        return modelView;
    }

    @PostMapping("/logincheck")
    public ModelAndView Login(@ModelAttribute("user") User user){
        if(user.getEmail()=="abc@gmx.de" && user.getPasswort()=="1234"){
            ModelAndView modelView = new ModelAndView();
            modelView.addObject("user", new User());
            modelView.setViewName("Test");
            return modelView;
        }else {
            ModelAndView modelView = new ModelAndView();
            modelView.addObject("user", new User());
            modelView.setViewName("Login");
            return modelView;
        }


    }
}
