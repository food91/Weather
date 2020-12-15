package mBroadcast;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import control.NotificationManagerX;
import control.TaskNotificationManager;
import util.Constant;

public class NotificationReceiverBroadcast extends BroadcastReceiver {

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equals(Constant.ACTION_NOTIFICATION)){
            TaskNotificationManager.getInstance().recevierWeatherInfo(context,intent.getIntExtra(Constant.BROADCAST_NOTIFICATION_DAY,0));
        }

    }
}
