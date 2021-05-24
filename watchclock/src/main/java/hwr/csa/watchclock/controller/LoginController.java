package hwr.csa.watchclock.controller;

import hwr.csa.watchclock.modell.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    //Controller der Loginseite Zeigt (eigentliches Login passiert über Package:Security)
    @GetMapping("/login")
    public String startLogin(Model model){
       return ("login");
    }

    //Controller der aktuellen User entfernt und berechtigungen wieder zurück setzt
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }
}

