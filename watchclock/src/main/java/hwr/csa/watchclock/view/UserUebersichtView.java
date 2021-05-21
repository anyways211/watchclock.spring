package hwr.csa.watchclock.view;

import hwr.csa.watchclock.modell.User;

import java.util.List;
//Felder die auf UserUebersicht.html angezeigt werden
public class UserUebersichtView {
    private List<User> users;
    private boolean error;
    private String errormsg;

    public UserUebersichtView(List<User> users) {
        this.users = users;
    }
    public UserUebersichtView() {
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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
