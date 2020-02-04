package data;

public class LoginData {

    private String user,password;

    public final static String SUSER="userstring";
    public final static String SPASSWORD="passwordstring";
    public final static String LOGINDATA="LOGINDATA";

    public LoginData(String user, String password){
        this.user=user;
        this.password=password;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
