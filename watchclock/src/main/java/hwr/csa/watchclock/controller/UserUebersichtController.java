package hwr.csa.watchclock.controller;

import hwr.csa.watchclock.modell.User;
import hwr.csa.watchclock.modell.UserRepository;
import hwr.csa.watchclock.view.UserUebersichtView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class UserUebersichtController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/userUebersicht")
    public ModelAndView start(){
        ModelAndView modelView = new ModelAndView();
        UserUebersichtView view= new UserUebersichtView();
        view.setUsers(userRepository.findAll());
        modelView.setViewName("userUebersicht");
        modelView.addObject("view", view);
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
