package hwr.csa.watchclock.controller;

import hwr.csa.watchclock.modell.User;
import hwr.csa.watchclock.modell.UserRepository;
import hwr.csa.watchclock.modell.Zeiteintrag;
import hwr.csa.watchclock.modell.ZeiteintragRepository;
import hwr.csa.watchclock.security.MyUserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class DownloadController {

    @Autowired
    ZeiteintragRepository zeiteintragRepository;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/download")
    public void exportCSV(HttpServletResponse response) throws Exception {
        // User bekommen
        MyUserPrincipal principal = (MyUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByPersonalNr(principal.getPersonalNr());

        // Filename und Contenttype definieren
        String filename = "zeiten.csv";
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"");

        // Monatszeiten des Users ermitteln
        LocalDateTime jetzt = LocalDateTime.now();
        int aktuellerMonat = jetzt.getMonthValue();
        List<Zeiteintrag> zeitenDesUsers = zeiteintragRepository.findAllByUserOrderByBisDesc(user);
        List<Zeiteintrag> zeitenDesMonats = new ArrayList<>();
        for(Zeiteintrag zeiteintrag : zeitenDesUsers){
            if(zeiteintrag.getVon().toLocalDateTime().getMonthValue()==aktuellerMonat){
                zeitenDesMonats.add(zeiteintrag);
            }
        }

        // Stringarray-Liste mit den nötigen einträgen erstellen
        List<String[]> liste = new ArrayList<>();
        String[] csvheader = {"Datum","Start", "Ende", "Kommentar", "Saldo"};
        liste.add(csvheader);
        for(Zeiteintrag zeiteintrag: zeitenDesMonats) {
            int[] saldoHMin = zeiteintrag.berechneSaldo();
            String saldo = String.valueOf(saldoHMin[0]) + " h " + String.valueOf(saldoHMin[1]) + " min";
            String[] eintrag = {String.valueOf(zeiteintrag.getDatum()), String.valueOf(zeiteintrag.getVon()),  String.valueOf(zeiteintrag.getBis()), zeiteintrag.getKommentar(), saldo};
            liste.add(eintrag);
        }

        // Einträge aus Liste in CSV schreiben
        PrintWriter writer = response.getWriter();
        for(String[] eintrag: liste){
            writer.write(String.join(";", eintrag)+"\n");
        }
    }
}
