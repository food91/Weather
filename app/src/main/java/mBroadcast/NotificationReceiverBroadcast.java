package mBroadcast;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import control.NotificationManagerX;
import util.Constant;

public class NotificationReceiverBroadcast extends BroadcastReceiver {

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equals(Constant.ACTION_NOTIFICATION)){
            String title=intent.getStringExtra(Constant.BROADCAST_NOTIFICATION_TITLE);
            String text=intent.getStringExtra(Constant.BROADCAST_NOTIFICATION_TEXT);
            NotificationManagerX.getInstance().sendChatMsg(context,title,text);
        }

    }
}
