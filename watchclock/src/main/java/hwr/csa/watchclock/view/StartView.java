package hwr.csa.watchclock.view;

import hwr.csa.watchclock.modell.User;

public class StartView {
    private User user;
    private String kommentar;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getKommentar() {
        return kommentar;
    }

    public void setKommentar(String kommentar) {
        this.kommentar = kommentar;
    }
}
