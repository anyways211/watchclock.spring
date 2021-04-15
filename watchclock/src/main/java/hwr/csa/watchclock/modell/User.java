package hwr.csa.watchclock.modell;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer personalNr;

    private String vorname;

    private String nachname;

    private String email;


    public User(String vorname, String nachname, String email) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.email = email;
    }

    public User() {
    }

    public String getVorname() {
        return this.vorname;
    }

    public Integer getPersonalNr(){
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
}
