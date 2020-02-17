package Entity;

public class LoginData {

    private String user,password;

    public LoginData(String user, String password, boolean keepuser, boolean keeppassword) {
        this.user = user;
        this.password = password;
        this.keepuser = keepuser;
        this.keeppassword = keeppassword;
    }

    private boolean keepuser,keeppassword;

    public void setKeepuser(boolean keepuser) {
        this.keepuser = keepuser;
    }

    public void setKeeppassword(boolean keeppassword) {
        this.keeppassword = keeppassword;
    }

    public boolean isKeepuser() {
        return keepuser;
    }

    public boolean isKeeppassword() {
        return keeppassword;
    }


    public final static String LOGIN="LOGIN";
    public final static String LOGINDATA="LOGINDATA";

    public LoginData(String user, String password){
        this.user=user;
        this.password=password;
        keeppassword=false;
        keepuser=false;
    }

    @Override
    public String toString() {
        return "{" +
                "user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", keepuser=" + keepuser +
                ", keeppassword=" + keeppassword +
                '}';
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
