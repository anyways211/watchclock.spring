package hwr.csa.watchclock.controller;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import hwr.csa.watchclock.modell.User;
import hwr.csa.watchclock.modell.UserRepository;
import hwr.csa.watchclock.modell.Zeiteintrag;
import hwr.csa.watchclock.modell.ZeiteintragRepository;
import hwr.csa.watchclock.security.MyUserPrincipal;
import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        String[] csvheader = {"Datum", "Start", "Ende", "Kommentar", "Saldo"};
        liste.add(csvheader);
        for(Zeiteintrag zeiteintrag: zeitenDesMonats) {
            int[] saldoHMin = zeiteintrag.berechneSaldo();
            double saldo = (double) saldoHMin[0] + ((double) saldoHMin[1]) / 60;
            String[] eintrag = {String.valueOf(zeiteintrag.getDatum()), String.valueOf(zeiteintrag.getVon()), String.valueOf(zeiteintrag.getBis()), zeiteintrag.getKommentar(), String.valueOf(saldo)};
            liste.add(eintrag);
        }

        // Einträge aus Liste schreiben
        PrintWriter writer = response.getWriter();
        for(String[] eintrag: liste){
            writer.write(String.join(";", eintrag)+"\n");
        }
    }
}
