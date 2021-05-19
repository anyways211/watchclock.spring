package hwr.csa.watchclock.controller;

import hwr.csa.watchclock.modell.User;
import hwr.csa.watchclock.modell.UserRepository;
import hwr.csa.watchclock.services.UserService;
import hwr.csa.watchclock.view.UserUebersichtView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

import static org.springframework.util.ObjectUtils.isEmpty;

@Controller
public class UserUebersichtController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    @GetMapping("/userUebersicht")
    public ModelAndView start(ModelAndView modelView){
        UserUebersichtView view= new UserUebersichtView();
        view.setUsers(userRepository.findAll());
        modelView.setViewName("userUebersicht");
        modelView.addObject("view", view);
        return modelView;
    }

    @GetMapping("userUebersicht/userAendern/{id}")
    public ModelAndView userAendern(){
        ModelAndView modelView = new ModelAndView();
        modelView.setViewName("userAendern");
        return modelView;
    }

    @GetMapping("userUebersich/userLoeschen/{personalNr}")
    public ModelAndView userLoeschen(@PathVariable("personalNr") long personalNr){
        ModelAndView modelView = new ModelAndView();
        UserUebersichtView view= new UserUebersichtView();

        //richtige ID angekommen?
        User zuLoeschen = userRepository.findByPersonalNr(personalNr);
        if(isEmpty(zuLoeschen)) {
            view.setError(true);
            view.setErrormsg("Etwas ist schief gelaufen, der zu löschende User existiert nicht");
        }
        else{
           // User user = zuLoeschen.get();
            //verhindern das letzter Admin gelöscht wird
            if (zuLoeschen.isIstAdmin() && !userService.mehrAlsEinAdmin()) {
                view.setError(true);
                view.setErrormsg("Es muss mindestens ein Admin vorhanden sein!");
            }
            else{
                userRepository.deleteByPersonalNr(personalNr);
                view.setError(true);
                view.setErrormsg(zuLoeschen.getUsername() + " wurde erfolgreich gelöscht!");
            }
        }
        view.setUsers(userRepository.findAll());
        modelView.setViewName("userUebersicht");
        modelView.addObject("view", view);
        return modelView;
    }
}
