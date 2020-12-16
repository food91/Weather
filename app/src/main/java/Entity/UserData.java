package Entity;

/**
 * The type User data.提供全局单例，用来保存登录的用户状态
 */
public class UserData {

    private static volatile UserData INSTANCE;
    private String name;
    private String password;
    private SetActivityBean setActivityBean=null;

    public SetActivityBean getSetActivityBean() {
        if(setActivityBean==null)
            setActivityBean=new SetActivityBean();
        return setActivityBean;
    }

    public void setSetActivityBean(SetActivityBean setActivityBean) {
        this.setActivityBean = setActivityBean;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Get user data user data.
     *
     * @return the user data
     */
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
