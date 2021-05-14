package hwr.csa.watchclock.controller;

import hwr.csa.watchclock.modell.User;
import hwr.csa.watchclock.modell.UserRepository;
import hwr.csa.watchclock.security.SecurityUserDetailsService;
import hwr.csa.watchclock.view.LoginView;
import hwr.csa.watchclock.view.StartView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
public class LoginController {
    @Autowired
    private SecurityUserDetailsService userDetailsManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public ModelAndView login(HttpServletRequest request, HttpSession session) {
        ModelAndView modelView = new ModelAndView();
        modelView.addObject("loginView", new LoginView());
        modelView.setViewName("Login");

        session.setAttribute(
                "error", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION")
        );

        return modelView;
    }

    private String getErrorMessage(HttpServletRequest request, String key) {
        Exception exception = (Exception) request.getSession().getAttribute(key);
        String error = "";
        if (exception instanceof BadCredentialsException) {
            error = "Invalid username and password!";
        } else if (exception instanceof LockedException) {
            error = exception.getMessage();
        } else {
            error = "Invalid username and password!";
        }
        return error;
    }
  /* @GetMapping("login")
    public ModelAndView startLogin(){
        ModelAndView modelView = new ModelAndView();
        modelView.addObject("loginView", new LoginView());
        modelView.setViewName("Login");
        return modelView;
    }*/

    @Validated
    @PostMapping("login")
    public ModelAndView Login(HttpServletRequest request, @Valid @ModelAttribute("loginView") LoginView loginView, BindingResult bindingResult) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        ModelAndView modelView = new ModelAndView();
        List<User> users = userRepository.findByEmail(loginView.getEmail());
            //gibt es die Email-Adresse in DB?
            if (users.isEmpty()) {
                modelView.setViewName("Login");
                loginView.setError(true);
                loginView.setErrormsg("Falsche Email oder falsches Passwort!");
                modelView.addObject("loginView", loginView);
            } else {
                //stimmt das Passwort?
                User user = users.get(0);
                if (encoder.matches(loginView.getPassword(), user.getPassword())) {

                    //redirect auf Startseite
                    StartView startView = new StartView();
                    startView.setUser(user);
                    modelView.setViewName("startZeiteintrag");
                    modelView.addObject("startView", startView);

                }
                else {
                    modelView.setViewName("Login");
                    loginView.setError(true);
                    loginView.setErrormsg("Falsche Email oder falsches Passwort!");
                    modelView.addObject("loginView", loginView);
                }
            }
            return modelView;
        }
    }

