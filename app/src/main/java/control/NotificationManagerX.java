package control;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.xiekun.myapplication.R;

import util.Constant;

public class NotificationManagerX {

    public static final NotificationManagerX instance=new NotificationManagerX();

    public static NotificationManagerX getInstance() {
        return instance;
    }

    /**
     * Send chat msg.
     *
     * @param title the title  通知标题
     * @param text  the text   通知内容
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public  void sendChatMsg(Context context, String title , String text) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(Constant.CHANNEL_1, getString(R.string.app_name), NotificationManager.IMPORTANCE_LOW);
            mChannel.setDescription("notication channel");
            mChannel.setVibrationPattern(new long[]{100, 200, 100, 200});
            manager .createNotificationChannel(mChannel);
        }
        Notification notification = new NotificationCompat.Builder(this, Constant.CHANNEL_1)
                .setContentTitle(title)
                .setContentText(text)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                        R.drawable.ic_launcher_background))
                .setAutoCancel(true)
                .setNumber(2)
                .build();
        manager.notify(1, notification);
    }
}
