package hwr.csa.watchclock.modell;

import javax.persistence.*;
import java.sql.*;

@Entity
public class Zeiteintrag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int eintragNr;

    @Column(name = "datum")
    private Date datum;

    @Column(name = "von")
    private Timestamp von;

    @Column(name = "bis")
    private Timestamp bis;

    @Column(name = "kommentar")
    private String kommentar;

    @ManyToOne
    @JoinColumn(name = "personalNr")
    private User user;

    public Zeiteintrag() {

    }

    public Zeiteintrag(Date datum, Timestamp von, Timestamp bis, String kommentar, User user) {
        this.datum = datum;
        this.von = von;
        this.bis = bis;
        this.kommentar = kommentar;
        this.user = user;
    }

    public int getEintragNr() {
        return eintragNr;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public Timestamp getVon() {
        return von;
    }

    public void setVon(Timestamp von) {
        this.von = von;
    }

    public Timestamp getBis() {
        return bis;
    }

    public void setBis(Timestamp bis) {
        this.bis = bis;
    }

    public String getKommentar() {
        return kommentar;
    }

    public void setKommentar(String kommentar) {
        this.kommentar = kommentar;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int[] berechneSaldo(){
        Timestamp von = this.von;
        Timestamp bis = this.bis;
        // wenn Start- und Endzeit gleich sind, ist das Saldo sowohl für Minuten als auch Stunden 0
        if(von.equals(bis)) return new int[]{0,0} ;
        // wenn Starttimestamp anch dem Endtimestamp ist, stimmt etwas nicht -> null zurückgeben
        if(von.after(bis)) return null;
        // Saldo per Differenz der Zeiten in Millisekunden berechnen
        long saldoInMillisekunden = bis.getTime() - von.getTime();
        // Differenz in Stunden umrechnen
        double saldoInStunden = (double)saldoInMillisekunden / 3600000;
        // Stunden gerundet in Zielarray eintragen
        int stunden = (int) saldoInStunden;
        int[] result= {stunden, 0};
        // die übrigen Millisekunden in Minuten umrechnen
        double difference = saldoInStunden - stunden;
        double minuten = difference * 60;
        // wenn übrige Minuten größer als halbe Miute sind weiter betrachgten, ansonsten einfach weglassen
        if(minuten>=0.5) {
            // wenn gerundete Minutenzahl 60 ist wird die Stundenzahl um eins erhöht, ansonsten wird gerundete Minuten zahl an zweiter Stelle des Zielarrays eingetragen
            int minutenGerundet = (int) Math.round(minuten);
            if(minutenGerundet==60) {
                result[0]+=1;
            }else {
                result[1]=minutenGerundet;
            }
        }
        return result;
    }
}
