package hwr.csa.watchclock.modell;

import javax.persistence.*;
import java.sql.*;

@Entity
public class Zeiteintrag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long eintragNr;

    @Column(name = "datum")
    private Date datum;

    @Column(name = "von")
    private Timestamp von;

    @Column(name = "bis")
    private Timestamp bis;

    @Column(name = "kommentar")
    private String kommentar;

    @ManyToOne(fetch = FetchType.LAZY)
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

    public long getEintragNr() {
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
}