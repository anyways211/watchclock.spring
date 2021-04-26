package hwr.csa.watchclock.controller;

import hwr.csa.watchclock.modell.User;
import hwr.csa.watchclock.modell.UserRepository;
import hwr.csa.watchclock.view.LoginView;
import hwr.csa.watchclock.view.StartView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("login")
    public ModelAndView startLogin(){
        ModelAndView modelView = new ModelAndView();
        modelView.addObject("loginView", new LoginView());
        modelView.setViewName("Login");
        //handle user inputs
        return modelView;
    }

    @Validated
    @PostMapping("login")
    public ModelAndView Login(@Valid @ModelAttribute("loginView") LoginView loginView, BindingResult bindingResult) {

        ModelAndView modelView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            modelView.setViewName("Login");
            loginView.setError(true);
            loginView.setErrormsg("Falsche Eingabe!");
            modelView.addObject("loginView", loginView);
            return modelView;
        } else {
            List<User> users = userRepository.findByEmail(loginView.getEmail());
            if (users.isEmpty()) {
                modelView.setViewName("Login");
                loginView.setError(true);
                loginView.setErrormsg("Kein Konto mit dieser Email!");
                modelView.addObject("loginView", loginView);
            } else {
                User user = users.get(0);
                if (loginView.getPasswort().equals(user.getPasswort())) {
                    StartView startView = new StartView();
                    startView.setUser(user);
                    modelView.setViewName("startZeiteintrag");
                    modelView.addObject("startView", startView);
                }
                else {
                    modelView.setViewName("Login");
                    loginView.setError(true);
                    loginView.setErrormsg("Falsches Passwort!");
                    modelView.addObject("loginView", loginView);
                }
            }
            return modelView;
        }
    }
}
