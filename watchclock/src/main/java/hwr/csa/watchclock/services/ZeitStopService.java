package hwr.csa.watchclock.services;

import hwr.csa.watchclock.modell.User;
import hwr.csa.watchclock.modell.Zeiteintrag;
import hwr.csa.watchclock.modell.ZeiteintragRepository;
import hwr.csa.watchclock.security.MyUserPrincipal;
import hwr.csa.watchclock.view.ZeitAendernView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.apache.logging.log4j.util.Strings.isBlank;
import static org.springframework.util.ObjectUtils.isEmpty;

//Service der validierungen und Berechnungen beim Zeit stoppen übernimmt
@Service
public class ZeitStopService {
    @Autowired
    ZeiteintragRepository zeiteintragRepository;

    public void manageSpeichernZeiteintraege(String kommentar, Timestamp von, Timestamp bis, User user, Zeiteintrag start) {
        LocalDateTime vonLDT = von.toLocalDateTime();
        LocalDateTime bisLDT = bis.toLocalDateTime();
        // nur wenn "von" vor "bis" liegt in Schleife gehen
        if (von.before(bis)) {
            // Schleife so lange durchlafen bis "von"-Datum gleich "bis"-Datum ist
            while (true) {
                if (vonLDT.getDayOfMonth() == bisLDT.getDayOfMonth() && vonLDT.getMonthValue() == bisLDT.getMonthValue() && vonLDT.getYear() == bisLDT.getYear()) {
                    // LocalDateTime Variablen zurück in Timestamps umwandeln
                    von = Timestamp.valueOf(vonLDT);
                    bis = Timestamp.valueOf(bisLDT);
                    // Überprüfen ob es den Zeiteintrag schon gibt - wenn ja, dann vorhandenen anpassen
                    if(vonLDT.equals(start.getVon().toLocalDateTime())){
                        // vorhandenen Zeiteintrag laden
                        Zeiteintrag startZeiteintrag = zeiteintragRepository.findByEintragNr(start.getEintragNr());
                        // Kommentar und Endzeit hinzufügen
                        startZeiteintrag.setBis(bis);
                        startZeiteintrag.setKommentar(kommentar);
                        // speichern in DB
                        zeiteintragRepository.saveAndFlush(startZeiteintrag);
                    }else{ // wenn Zeiteintrag noch nicht vorhanden, neuen erstellen und speichern
                        // Zeiteintrag erstellen
                        Zeiteintrag neuerZeiteintrag = new Zeiteintrag(von, bis, kommentar, user);
                        // System.out.println("Neuer Zeiteintrag: von: " + von.toString() + " | bis: " + bis.toString());
                        //System.out.println("Saldo: " + berechneSaldo(von, bis)[0] + " h "+ berechneSaldo(von, bis)[1] + " min");
                        // Zeiteintrag speichern
                        zeiteintragRepository.saveAndFlush(neuerZeiteintrag);
                    }
                    break;
                } else {
                    // sollten Tage von "von" und "bis" nicht übereinstimmen, werden mehrere Zeiteinträge erstellt; am Tagesende wird immer abgeschnitten
                    // Tagesende definieren
                    LocalDateTime Tagesende = vonLDT.withHour(23).withMinute(59).withSecond(59).withNano(0);
                    // LocalDateTime Variablen zurück in Timestamps umwandeln zum Speichern
                    von = Timestamp.valueOf(vonLDT);
                    bis = Timestamp.valueOf(Tagesende);
                    // Überprüfen ob es den Zeiteintrag schon gibt - wenn ja, dann vorhandenen anpassen
                    if(vonLDT.equals(start.getVon().toLocalDateTime())){
                        // vorhandenen Zeiteintrag laden
                        Zeiteintrag startZeiteintrag = zeiteintragRepository.findByEintragNr(start.getEintragNr());
                        // Kommentar und Endzeit hinzufügen
                        startZeiteintrag.setBis(bis);
                        startZeiteintrag.setKommentar(kommentar);
                        // speichern in DB
                        zeiteintragRepository.saveAndFlush(startZeiteintrag);
                    }else { // wenn Zeiteintrag noch nicht vorhanden, neuen erstellen und speichern
                        // Zeiteintrag erstellen
                        Zeiteintrag neuerZeiteintrag = new Zeiteintrag(von, bis, kommentar, user);
                        // System.out.println("Neuer Zeiteintrag: von: " + von.toString() + " | bis: " + bis.toString());
                        //System.out.println("Saldo: " + berechneSaldo(von, bis)[0] + " h "+ berechneSaldo(von, bis)[1] + " min");
                        // Zeiteintrag speichern
                        zeiteintragRepository.saveAndFlush(neuerZeiteintrag);
                    }
                    // neuen Tagesbeginn auf nächsten Tag 0 Uhr setzen (da abends um 23:59 abgeschnitten wurde)
                    vonLDT = vonLDT.plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
                }
            }
        }
    }

    public Zeiteintrag checkAenderung(Zeiteintrag aktuell, ZeitAendernView geaendert){
        //Tag hat sich geändert
        if (!isEmpty(geaendert.getTag()) && !geaendert.getTag().equals(new Date(aktuell.getVon().getTime()))){
            //von geändert --> timestamp mit neuem Datum und neuer Zeit
            if(!isEmpty(geaendert.getVon()) && !geaendert.getVon().equals(new Time(aktuell.getVon().getTime()))){
                aktuell.setVon(dateTimeToTimeStamp(geaendert.getTag(), geaendert.getVon()));
            }
            //von nicht geändert --> timestamp mit neuem Datum und alter Zeit
            else{
                aktuell.setVon(dateTimeToTimeStamp(geaendert.getTag(), new Time(aktuell.getVon().getTime())));
            }
            //bis geändert --> timestam mit neuem Datum und neuer Zeit
            if(!isEmpty(geaendert.getBis()) && !geaendert.getBis().equals(new Time(aktuell.getBis().getTime()))){
                aktuell.setBis(dateTimeToTimeStamp(geaendert.getTag(), geaendert.getBis()));
            }
            //bis nicht geändert --> timestamp mit neuem Datum und alter Zeit
            else{
                aktuell.setBis(dateTimeToTimeStamp(geaendert.getTag(), new Time(aktuell.getBis().getTime())));
            }
        }
        //Tag ist gleich
        else{
            //von geändert --> timestamp mit altem Datum und neuer Zeit
            if(!isEmpty(geaendert.getVon()) && !geaendert.getVon().equals(new Time(aktuell.getVon().getTime()))){
                aktuell.setVon(dateTimeToTimeStamp(new Date(aktuell.getVon().getTime()), geaendert.getVon()));
            }
            //bis geändert --> timestamp mit altem Datum und neuer Zeit
            if(!isEmpty(geaendert.getBis()) && !geaendert.getBis().equals(new Time(aktuell.getBis().getTime()))){
                aktuell.setBis(dateTimeToTimeStamp(new Date(aktuell.getBis().getTime()), geaendert.getBis()));
            }
            //in else zweigen kann das Datum gleich bleiben --> es hat sich nichts geänddert
        }
        //Aenderung an Kommentar übernehmen
        if(!isBlank(geaendert.getKommentar()) && geaendert.getKommentar().equals(aktuell.getKommentar())){
            aktuell.setKommentar(geaendert.getKommentar());
        }

        return aktuell;
    }
    //aus date und time einen Timestamp machen
    public Timestamp dateTimeToTimeStamp(Date date, Time time){
        String timestamp = date + " " + time;
        return Timestamp.valueOf(timestamp);
    }
}
