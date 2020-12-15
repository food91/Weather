package util;

public class Constant {

    //消息
    public static String CHANNEL_1 = "channel1";

    //腾讯地图API
    public static String TencentApiUrl="https://apis.map.qq.com/";
    public static String TencentApiUrlpath="ws/geocoder/v1/";
    //腾讯key
    public static String TencentApiKey="ZLFBZ-3MK6S-I2ZOK-6IQKU-XL2B7-6VBOE";

    /**
     * The constant WEATHERURL.
     */
    public final static String WEATHERURL="http://tianqiapi.com/";
    /**
     * The constant WEATHERID.
     */
    public final static String WEATHERID="87699471";
    /**
     * The constant WEATHERPASSWORD.
     */
    public final static String WEATHERPASSWORD="oSys9sgp";
    /**
     * The constant VERSION.
     */
    public final static String VERSION="v1";

    public final static int NOTIFICATIONTIME=7;
    public final static int NOTIFICATIONTIME2=19;

    //广播接收标识，发送定时notification
    public final static String ACTION_NOTIFICATION="broadcast_notification";
    public final static String BROADCAST_NOTIFICATION_TITLE="notification_title";
    public final static String BROADCAST_NOTIFICATION_TEXT="notification_text";
    //通知 控制今天还是明天的天气
    public static final String BROADCAST_NOTIFICATION_DAY="notification_day";

    //配置文件存储名称
    public static final String SET_FILE_NAME="setfilename";

}
