package hwr.csa.watchclock.view;

import hwr.csa.watchclock.modell.User;
import hwr.csa.watchclock.modell.Zeiteintrag;

import java.util.List;
//Felder die auf ZeitUebersicht.html angezeigt werden
public class ZeitUebersichtView {
    private User user;
    private List<Zeiteintrag> zeitEintraege;

    public ZeitUebersichtView(User user, List<Zeiteintrag> zeitEintraege) {
        this.user = user;
        this.zeitEintraege = zeitEintraege;
    }

    public ZeitUebersichtView() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Zeiteintrag> getZeitEintraege() {
        return zeitEintraege;
    }

    public void setZeitEintraege(List<Zeiteintrag> zeitEintraege) {
        this.zeitEintraege = zeitEintraege;
    }
}
