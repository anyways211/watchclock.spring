package hwr.csa.watchclock.services;

import hwr.csa.watchclock.modell.User;
import hwr.csa.watchclock.modell.Zeiteintrag;
import hwr.csa.watchclock.view.ZeitAendernView;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class ZeitStopServiceTest {
    @Autowired
    ZeitStopService zeitStopService = new ZeitStopService();

    @Test
    void checkAenderungAllesGleich() {
        Zeiteintrag zeiteintrag = new Zeiteintrag(1, Timestamp.valueOf("2012-02-21 10:00:00"),
                Timestamp.valueOf("2012-02-21 12:00:00"), "", new User(1,"Annika", "Pass", "annika.pass@gmx.de",
                "annika.pass", 30,"test", Date.valueOf("1998-01-21"),true));
        ZeitAendernView view = new ZeitAendernView();
        view.setZeitEintragNr(1);
        view.setTag("2012-02-21");
        view.setVon("10:00");
        view.setBis("12:00");
        view.setKommentar("");

        Zeiteintrag expected = new Zeiteintrag(1, Timestamp.valueOf("2012-02-21 10:00:00"),
                Timestamp.valueOf("2012-02-21 12:00:00"), "", new User(1,"Annika", "Pass", "annika.pass@gmx.de",
                "annika.pass", 30,"test", Date.valueOf("1998-01-21"),true));


        Zeiteintrag actual = zeitStopService.checkAenderung(zeiteintrag, view);
        assertEquals(expected, actual);

    }

    @Test
    void checkAenderungAenderungTag() {
        Zeiteintrag zeiteintrag = new Zeiteintrag(1, Timestamp.valueOf("2012-02-21 10:00:00"),
                Timestamp.valueOf("2012-02-21 12:00:00"), "", new User(1,"Annika", "Pass", "annika.pass@gmx.de",
                "annika.pass", 30,"test", Date.valueOf("1998-01-21"),true));
        ZeitAendernView view = new ZeitAendernView();
        view.setZeitEintragNr(1);
        view.setTag("2012-03-21");
        view.setVon("10:00");
        view.setBis("12:00");
        view.setKommentar("");

        Zeiteintrag expected = new Zeiteintrag(1, Timestamp.valueOf("2012-03-21 10:00:00"),
                Timestamp.valueOf("2012-03-21 12:00:00"), "", new User(1,"Annika", "Pass", "annika.pass@gmx.de",
                "annika.pass", 30,"test", Date.valueOf("1998-01-21"),true));

        Zeiteintrag actual = zeitStopService.checkAenderung(zeiteintrag, view);

        assertEquals(expected, actual);

    }
    @Test
    void checkAenderungAenderungVon() {
        Zeiteintrag zeiteintrag = new Zeiteintrag(1, Timestamp.valueOf("2012-02-21 10:00:00"),
                Timestamp.valueOf("2012-02-21 12:00:00"), "", new User(1,"Annika", "Pass", "annika.pass@gmx.de",
                "annika.pass", 30,"test", Date.valueOf("1998-01-21"),true));
        ZeitAendernView view = new ZeitAendernView();
        view.setZeitEintragNr(1);
        view.setTag("2012-02-21");
        view.setVon("09:00");
        view.setBis("12:00");
        view.setKommentar("");

        Zeiteintrag expected = new Zeiteintrag(1, Timestamp.valueOf("2012-02-21 09:00:00"),
                Timestamp.valueOf("2012-02-21 12:00:00"), "", new User(1,"Annika", "Pass", "annika.pass@gmx.de",
                "annika.pass", 30,"test", Date.valueOf("1998-01-21"),true));

        Zeiteintrag actual = zeitStopService.checkAenderung(zeiteintrag, view);

        assertEquals(expected, actual);

    }
    @Test
    void checkAenderungAenderungBis() {
        Zeiteintrag zeiteintrag = new Zeiteintrag(1, Timestamp.valueOf("2012-02-21 10:00:00"),
                Timestamp.valueOf("2012-02-21 12:00:00"), "", new User(1,"Annika", "Pass", "annika.pass@gmx.de",
                "annika.pass", 30,"test", Date.valueOf("1998-01-21"),true));
        ZeitAendernView view = new ZeitAendernView();
        view.setZeitEintragNr(1);
        view.setTag("2012-02-21");
        view.setVon("10:00");
        view.setBis("13:00");
        view.setKommentar("");

        Zeiteintrag expected = new Zeiteintrag(1, Timestamp.valueOf("2012-02-21 10:00:00"),
                Timestamp.valueOf("2012-02-21 13:00:00"), "", new User(1,"Annika", "Pass", "annika.pass@gmx.de",
                "annika.pass", 30,"test", Date.valueOf("1998-01-21"),true));

        Zeiteintrag actual = zeitStopService.checkAenderung(zeiteintrag, view);

        assertEquals(expected, actual);

    }
    @Test
    void checkAenderungAenderungKommentarVonLeer() {
        Zeiteintrag zeiteintrag = new Zeiteintrag(1, Timestamp.valueOf("2012-02-21 10:00:00"),
                Timestamp.valueOf("2012-02-21 13:00:00"), "", new User(1,"Annika", "Pass", "annika.pass@gmx.de",
                "annika.pass", 30,"test", Date.valueOf("1998-01-21"),true));
        ZeitAendernView view = new ZeitAendernView();
        view.setZeitEintragNr(1);
        view.setTag("2012-02-21");
        view.setVon("10:00");
        view.setBis("13:00");
        view.setKommentar("Test");

        Zeiteintrag expected = new Zeiteintrag(1, Timestamp.valueOf("2012-02-21 10:00:00"),
                Timestamp.valueOf("2012-02-21 13:00:00"), "Test", new User(1,"Annika", "Pass", "annika.pass@gmx.de",
                "annika.pass", 30,"test", Date.valueOf("1998-01-21"),true));

        Zeiteintrag actual = zeitStopService.checkAenderung(zeiteintrag, view);

        assertEquals(expected, actual);

    }
    @Test
    void checkAenderungAenderungKommentarVonGefuellt() {
        Zeiteintrag zeiteintrag = new Zeiteintrag(1, Timestamp.valueOf("2012-02-21 10:00:00"),
                Timestamp.valueOf("2012-02-21 13:00:00"), "abc", new User(1,"Annika", "Pass", "annika.pass@gmx.de",
                "annika.pass", 30,"test", Date.valueOf("1998-01-21"),true));
        ZeitAendernView view = new ZeitAendernView();
        view.setZeitEintragNr(1);
        view.setTag("2012-02-21");
        view.setVon("10:00");
        view.setBis("13:00");
        view.setKommentar("Test");

        Zeiteintrag expected = new Zeiteintrag(1, Timestamp.valueOf("2012-02-21 10:00:00"),
                Timestamp.valueOf("2012-02-21 13:00:00"), "Test", new User(1,"Annika", "Pass", "annika.pass@gmx.de",
                "annika.pass", 30,"test", Date.valueOf("1998-01-21"),true));

        Zeiteintrag actual = zeitStopService.checkAenderung(zeiteintrag, view);

        assertEquals(expected, actual);

    }
    @Test
    void checkAenderungAenderungALLE() {
        Zeiteintrag zeiteintrag = new Zeiteintrag(1, Timestamp.valueOf("2012-02-21 10:00:00"),
                Timestamp.valueOf("2012-02-21 13:00:00"), "abc", new User(1,"Annika", "Pass", "annika.pass@gmx.de",
                "annika.pass", 30,"test", Date.valueOf("1998-01-21"),true));
        ZeitAendernView view = new ZeitAendernView();
        view.setZeitEintragNr(1);
        view.setTag("2014-02-21");
        view.setVon("09:00");
        view.setBis("14:00");
        view.setKommentar("Test");

        Zeiteintrag expected = new Zeiteintrag(1, Timestamp.valueOf("2014-02-21 09:00:00"),
                Timestamp.valueOf("2014-02-21 14:00:00"), "Test", new User(1,"Annika", "Pass", "annika.pass@gmx.de",
                "annika.pass", 30,"test", Date.valueOf("1998-01-21"),true));

        Zeiteintrag actual = zeitStopService.checkAenderung(zeiteintrag, view);

        assertEquals(expected, actual);

    }
    @Test
    void fieldsEmptyAlleGefuellt() {
        ZeitAendernView view = new ZeitAendernView();
        view.setZeitEintragNr(1);
        view.setTag("2010-01-02");
        view.setBis("15:00");
        view.setVon("08:00");
        Boolean expected = false;
        Boolean actual = zeitStopService.fieldsEmpty(view);
        assertEquals(expected, actual);
    }
    @Test
    void fieldsEmptyEinsLeerzeichen() {
        ZeitAendernView view = new ZeitAendernView();
        view.setZeitEintragNr(1);
        view.setTag("2010-01-02");
        view.setBis(" ");
        view.setVon("08:00");
        Boolean expected = true;
        Boolean actual = zeitStopService.fieldsEmpty(view);
        assertEquals(expected, actual);
    }
    @Test
    void fieldsEmptyEinsLeer() {
        ZeitAendernView view = new ZeitAendernView();
        view.setZeitEintragNr(1);
        view.setTag("2010-01-02");
        view.setBis("");
        view.setVon("08:00");
        Boolean expected = true;
        Boolean actual = zeitStopService.fieldsEmpty(view);
        assertEquals(expected, actual);
    }
    @Test
    void fieldsEmptyAlleLeer() {
        ZeitAendernView view = new ZeitAendernView();
        view.setZeitEintragNr(1);
        view.setTag("");
        view.setBis("");
        view.setVon("");
        Boolean expected = true;
        Boolean actual = zeitStopService.fieldsEmpty(view);
        assertEquals(expected, actual);
    }
    @Test
    void fieldsEmptyAlleLeerzeichen() {
        ZeitAendernView view = new ZeitAendernView();
        view.setZeitEintragNr(1);
        view.setTag(" ");
        view.setBis(" ");
        view.setVon(" ");
        Boolean expected = true;
        Boolean actual = zeitStopService.fieldsEmpty(view);
        assertEquals(expected, actual);
    }
}