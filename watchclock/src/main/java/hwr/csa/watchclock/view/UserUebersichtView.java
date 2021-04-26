package hwr.csa.watchclock.view;

import hwr.csa.watchclock.modell.User;

import java.util.List;

public class UserUebersichtView {
    private List<User> users;

    public UserUebersichtView(List<User> users) {
        this.users = users;
    }

    public UserUebersichtView() {
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
