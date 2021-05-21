package hwr.csa.watchclock.view;

//Felder die auf PasswortAendern.html angezeigt werden
public class PasswortAendernView {

    private String altesPasswort;
    private String neuesPasswort;
    private String neuesPasswortW;
    private boolean error;
    private String errormsg;

    public String getAltesPasswort() {
        return altesPasswort;
    }

    public void setAltesPasswort(String altesPasswort) {
        this.altesPasswort = altesPasswort;
    }

    public String getNeuesPasswort() {
        return neuesPasswort;
    }

    public void setNeuesPasswort(String neuesPasswort) {
        this.neuesPasswort = neuesPasswort;
    }

    public String getNeuesPasswortW() {
        return neuesPasswortW;
    }

    public void setNeuesPasswortW(String neuesPasswortW) {
        this.neuesPasswortW = neuesPasswortW;
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
