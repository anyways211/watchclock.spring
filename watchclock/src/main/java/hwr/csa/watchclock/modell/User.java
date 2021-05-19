package hwr.csa.watchclock.modell;

import javax.persistence.*;
import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long personalNr;

    @Column(name = "vorname")
    private String vorname;

    @Column(name = "nachname")
    private String nachname;

    @NotEmpty(message = "Feld darf nicht leer sein!")
    @Size(max=255, message = "Die maximale Zeichenlänge von 255 wurde überschritten!")
    @Column(name = "email")
    private String email;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "geburtsdatum")
    private Date geburtsdatum;

    @Column(name = "sollArbeitszeit")
    private int sollArbeitszeit;

    @NotEmpty(message = "Feld darf nicht leer sein!")
    @Size(max=255, message = "Die maximale Zeichenlänge von 255 wurde überschritten!")
    @Column(name = "password")
    private String password;

    @Column (name = "istAdmin")
    private boolean istAdmin;

    public User(String vorname, String nachname, String email, String username, int sollArbeitszeit,
                String password, Date geburtsdatum, boolean istAdmin) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.email = email;
        this.username = username;
        this.password = password;
        this.geburtsdatum = geburtsdatum;
        this.sollArbeitszeit = sollArbeitszeit;
        this.istAdmin = istAdmin;
    }

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Date getGeburtsdatum() {
        return geburtsdatum;
    }

    public void setGeburtsdatum(Date geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }

    public String getVorname() {
        return this.vorname;
    }

    public long getPersonalNr(){
        return this.personalNr;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return this.nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername (){return this.username;}

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPersonalNr(Integer personalNr) {
        this.personalNr = personalNr;
    }

    public int getSollArbeitszeit() {
        return sollArbeitszeit;
    }

    public void setSollArbeitszeit(int sollArbeitszeit) {
        this.sollArbeitszeit = sollArbeitszeit;
    }

    public void setIstAdmin(boolean istAdmin) {
        this.istAdmin = istAdmin;
    }

    public boolean isIstAdmin() {
        return istAdmin;
    }

}
