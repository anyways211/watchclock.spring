package hwr.csa.watchclock.view;

import java.sql.Time;
import java.sql.Date;

public class ZeitAendernView {
    private long zeitEintragNr;
    private Date tag;
    private Time von;
    private Time bis;
    private String kommentar;
    private boolean error;
    private boolean errormsg;

    public long getZeitEintragNr() {
        return zeitEintragNr;
    }

    public void setZeitEintragNr(long zeitEintragNr) {
        this.zeitEintragNr = zeitEintragNr;
    }

    public Date getTag() {
        return tag;
    }

    public void setTag(Date tag) {
        this.tag = tag;
    }

    public Time getVon() {
        return von;
    }

    public void setVon(Time von) {
        this.von = von;
    }

    public Time getBis() {
        return bis;
    }

    public void setBis(Time bis) {
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

    public boolean isErrormsg() {
        return errormsg;
    }

    public void setErrormsg(boolean errormsg) {
        this.errormsg = errormsg;
    }
}
