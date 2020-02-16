package Entity;

public class UserData {

    private static volatile UserData INSTANCE;
    private String name;
    private String password;

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public static UserData getUserData(){
        if (INSTANCE == null) {
            synchronized (UserData.class) {
                if (INSTANCE == null) {
                    INSTANCE =new UserData();
                }
            }
        }
        return INSTANCE;
    }
}
