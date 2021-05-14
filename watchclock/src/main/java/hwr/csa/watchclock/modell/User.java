package hwr.csa.watchclock.modell;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Entity
@Table(name = "user")
public class User implements UserDetails {

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

    @Column(name = "geburtsdatum")
    private Date geburtsdatum;

    @Column(name = "sollArbeitszeit")
    private int sollArbeitszeit;

    @NotEmpty(message = "Feld darf nicht leer sein!")
    @Size(max=255, message = "Die maximale Zeichenlänge von 255 wurde überschritten!")
    @Column(name = "passwort")
    private String password;

    @Column (name = "istAdmin")
    private boolean istAdmin;

    @OneToMany
    private List<Zeiteintrag> zeiteintraege;

    public User(String vorname, String nachname, String email, int sollArbeitszeit,
                String passwort, boolean istAdmin) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.email = email;
        this.password = passwort;
        this.sollArbeitszeit = sollArbeitszeit;
        this.istAdmin = istAdmin;
    }

    public User() {
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

    @Override
    public String getUsername() {
        return this.getVorname() +' '+ this.getNachname();
    }

    public void setPassword(String passwort) {
        this.password = passwort;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public List<Zeiteintrag> getZeiteintraege() {
        return zeiteintraege;
    }

    public void setUsers(List<Zeiteintrag> zeiteintraege) {
        this.zeiteintraege = zeiteintraege;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "read");
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
