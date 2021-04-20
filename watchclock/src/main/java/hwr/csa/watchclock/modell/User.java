package hwr.csa.watchclock.modell;

import org.apache.tomcat.jni.Local;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;


@Entity
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long personalNr;

    private String vorname;

    private String nachname;

    private String email;

    private String passwort;

    private LocalDate geburtsdatum;

    private boolean istAdmin;

    private int sollArbeitszeit;


    public User(String vorname, String nachname, String email, String passwort, LocalDate geburtsdatum, boolean istAdmin, int sollArbeitszeit) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.email = email;
        this.passwort = passwort;
        this.geburtsdatum = geburtsdatum;
        this.istAdmin = istAdmin;
        this.sollArbeitszeit = sollArbeitszeit;
    }

    public User() {
    }

    public String getVorname() {
        return this.vorname;
    }

    public long getPersonalNr(){
        return this.personalNr;
    }

    public void setVorname(String Vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return this.nachname;
    }

    public void setNachname(String nachname) {
        this.vorname = vorname;
    }

    public String getPasswort() {
        return this.passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getGeburtsdatum() {
        return this.geburtsdatum;
    }

    public void setGeburtsdatum(LocalDate geburtsdatum) { this.geburtsdatum = geburtsdatum; }

    public Boolean getIstAdmin() {
        return this.istAdmin;
    }

    public void setIstAdmin(Boolean istAdmin) {
        this.istAdmin = istAdmin;
    }

    public int getSollArbeitszeit() {
        return this.sollArbeitszeit;
    }

    public void setEmail(int sollArbeitszeit) {
        this.sollArbeitszeit = sollArbeitszeit;
    }
}
