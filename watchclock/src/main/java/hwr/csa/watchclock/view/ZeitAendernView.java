package hwr.csa.watchclock.view;

import java.sql.Time;
import java.sql.Date;

public class ZeitAendernView {
    private long zeitEintragNr;
    private String tag;
    private String von;
    private String bis;
    private String kommentar;
    private boolean error;
    private String errormsg;

    public long getZeitEintragNr() {
        return zeitEintragNr;
    }

    public void setZeitEintragNr(long zeitEintragNr) {
        this.zeitEintragNr = zeitEintragNr;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getVon() {
        return von;
    }

    public void setVon(String von) {
        this.von = von;
    }

    public String getBis() {
        return bis;
    }

    public void setBis(String bis) {
        this.bis = bis;
    }

    public String getKommentar() {
        return kommentar;
    }

    public void setKommentar(String kommentar) {
        this.kommentar = kommentar;
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
