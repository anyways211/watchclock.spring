package hwr.csa.watchclock.view;

import hwr.csa.watchclock.modell.User;

//Felder die auf UserAendern.html angezeigt werden
public class UserAendernView {
    private User user;
    private boolean error;
    private String errormsg;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
