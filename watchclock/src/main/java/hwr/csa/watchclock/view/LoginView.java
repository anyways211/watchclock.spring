package hwr.csa.watchclock.view;

//Felder die auf Login.html angezeigt werden
public class LoginView {
    private String password;
    private String email;
    private boolean error;
    private String errormsg;

    public LoginView() {
        this.error = false;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getError() {
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
