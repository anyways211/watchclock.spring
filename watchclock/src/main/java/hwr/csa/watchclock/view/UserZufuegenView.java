package hwr.csa.watchclock.view;

import hwr.csa.watchclock.modell.User;

//Felder die auf UserZufuegen.html angezeigt werden
public class UserZufuegenView {
    private long personalNr;
    private String vorname;
    private String nachname;
    private String username;
    private String email;
    private String geburtsdatum;
    private String sollarbeitszeit;
    private String password;
    private boolean istAdmin;
    private boolean error;
    private String errormsg;

    public UserZufuegenView(){

    }

    public UserZufuegenView(long personalNr, String vorname,
                           String nachname, String username, String email,
                           String geburtsdatum, String sollArbeitszeit, String password,
                           boolean istAdmin) {
        this.personalNr = personalNr;
        this.vorname = vorname;
        this.nachname = nachname;
        this.username = username;
        this.email = email;
        this.geburtsdatum = geburtsdatum;
        this.sollarbeitszeit = sollArbeitszeit;
        this.password = password;
        this.istAdmin = istAdmin;
    }

    public long getPersonalNr() {
        return personalNr;
    }

    public void setPersonalNr(long personalNr) {
        this.personalNr = personalNr;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGeburtsdatum() {
        return geburtsdatum;
    }

    public void setGeburtsdatum(String geburtsdatum) {
        this.geburtsdatum = geburtsdatum;
    }

    public String getSollarbeitszeit() {
        return sollarbeitszeit;
    }

    public void setSollarbeitszeit(String sollarbeitszeit) {
        this.sollarbeitszeit = sollarbeitszeit;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isIstAdmin() {
        return istAdmin;
    }

    public void setIstAdmin(boolean istAdmin) {
        this.istAdmin = istAdmin;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }
}

