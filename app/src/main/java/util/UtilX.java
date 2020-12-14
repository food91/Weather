package util;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.orhanobut.logger.Logger;
import com.xiekun.myapplication.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import Entity.WeatherData;

/**
 * The type Util x.
 */
public class UtilX {

    public static boolean isDebug=true;


    public static void allShare(Context context){
        Intent share_intent = new Intent();
        share_intent.setAction(Intent.ACTION_SEND);//设置分享行为
        share_intent.setType("text/plain");//设置分享内容的类型
        share_intent.putExtra(Intent.EXTRA_SUBJECT, "share");//添加分享内容标题
        share_intent.putExtra(Intent.EXTRA_TEXT, "分享:"+"android");//添加分享内容
        //创建分享的Dialog
        share_intent = Intent.createChooser(share_intent, "share");
        context.startActivity(share_intent);
    }

    public static int getLocalTime(){
        Calendar calendar=Calendar.getInstance();
        int hour=calendar.get(Calendar.HOUR_OF_DAY);
        return hour;
    }


    /**
     * 返回当前程序版本号
     */
    public static String getAppVersionCode(Context context) {
        int versioncode = 0;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            // versionName = pi.versionName;
            versioncode = pi.versionCode;
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versioncode + "";
    }

    public static void LogX(String string){

        if(isDebug){
            Logger.d(string);
        }

    }


    public static String DataFormat(String data){
        SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sf2 = new SimpleDateFormat("MM-dd");
        String formatStr = "";
        try {
            formatStr = sf2.format(sf1.parse(data));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Logger.d("for====="+formatStr);
        return formatStr;

    }



    /**
     * Centigrade string to int int.
     * 将摄氏度转化为int
     *
     * @param str the str
     * @return the int
     */
    public static int CentigradeStringToInt(String str){

        boolean nagative=false;
        int num=0;
        for(int i=0;i<str.length();i++){
            if(str.charAt(i)=='-'){
                nagative=true;
            }
           else if(str.charAt(i)>='0'&&str.charAt(i)<='9'){
                num*=10;
               num+=(str.charAt(i)-'0');
            }
        }
        if(nagative){
            num= 0 - num;
        }
        return num;
    }

    /**
     * Maxint int.
     *
     * @param a the a
     * @return the int
     */
    public static int maxint(int[] a) {

        int max = a[0];
        for (int i = 1; i < a.length;i++) {
            if (a[i] > max) {
                max = a[i];
            }
        }
        return max;
    }

    public static long getMillsTimeToday(){
        long time1=System.currentTimeMillis();
        long hour=UtilX.getLocalTimeHour()*3600000;
        long min=getLocalTimeMinute()*60000;
        return time1-hour-min;
    }

    /**
     * Minint int.
     *
     * @param a the a
     * @return the int
     */
    public static int minint ( int[] a){
            int min = a[0];
            for (int i = 1; i < a.length;i++) {
                if (a[i] < min) {
                    min = a[i];
                }
            }
            return min;
        }

    /**
     * Is string null boolean.
     *
     * @param str the str
     * @return the boolean
     */
    public final static boolean IsStringNull (String str){

            if (str == null || str.equals("")) {
                return true;
            }
            return false;
        }

    public static Bitmap getweatherBitmap(WeatherData weatherData,
                          Context mcontext) {
        Bitmap weatherBitmap = null;
        if (weatherData != null) {
            String wea = weatherData.getData().get(0).getWea_img();
            for (int i = 0; i < StringUtils.IMGSTRING.length; i++) {
                if (wea.equals(StringUtils.IMGSTRING[i])) {
                    if (i == 0) {
                        weatherBitmap = BitmapFactory.decodeResource(mcontext.getResources(),
                                R.mipmap.xue);
                    }
                    if (i == 1) {
                        weatherBitmap = BitmapFactory.decodeResource(mcontext.getResources(),
                                R.mipmap.lei);
                    }
                    if (i == 2) {
                        weatherBitmap = BitmapFactory.decodeResource(mcontext.getResources(),
                                R.mipmap.shachen);
                    }
                    if (i == 3) {
                        weatherBitmap = BitmapFactory.decodeResource(mcontext.getResources(),
                                R.mipmap.wu);
                    }
                    if (i == 4) {
                        weatherBitmap = BitmapFactory.decodeResource(mcontext.getResources(),
                                R.mipmap.bingbao);
                    }
                    if (i == 5) {
                        weatherBitmap = BitmapFactory.decodeResource(mcontext.getResources(),
                                R.mipmap.yun);
                    }
                    if (i == 6) {
                        weatherBitmap = BitmapFactory.decodeResource(mcontext.getResources(),
                                R.mipmap.yu);
                    }
                    if (i == 7) {
                        weatherBitmap = BitmapFactory.decodeResource(mcontext.getResources(),
                                R.mipmap.yin);
                    }
                    if (i == 8) {
                        weatherBitmap = BitmapFactory.decodeResource(mcontext.getResources(),
                                R.mipmap.qing);
                    }
                    break;
                }
            }
        }
        return weatherBitmap;
    }

    public static Bitmap getweatherBitmap(WeatherData weatherData,int m,
                                          Context mcontext) {
        Bitmap weatherBitmap = null;
        if (weatherData != null) {
            String wea = weatherData.getData().get(m).getWea_img();
            for (int i = 0; i < StringUtils.IMGSTRING.length; i++) {
                if (wea.equals(StringUtils.IMGSTRING[i])) {
                    if (i == 0) {
                        weatherBitmap = BitmapFactory.decodeResource(mcontext.getResources(),
                                R.mipmap.xue);
                    }
                    if (i == 1) {
                        weatherBitmap = BitmapFactory.decodeResource(mcontext.getResources(),
                                R.mipmap.lei);
                    }
                    if (i == 2) {
                        weatherBitmap = BitmapFactory.decodeResource(mcontext.getResources(),
                                R.mipmap.shachen);
                    }
                    if (i == 3) {
                        weatherBitmap = BitmapFactory.decodeResource(mcontext.getResources(),
                                R.mipmap.wu);
                    }
                    if (i == 4) {
                        weatherBitmap = BitmapFactory.decodeResource(mcontext.getResources(),
                                R.mipmap.bingbao);
                    }
                    if (i == 5) {
                        weatherBitmap = BitmapFactory.decodeResource(mcontext.getResources(),
                                R.mipmap.yun);
                    }
                    if (i == 6) {
                        weatherBitmap = BitmapFactory.decodeResource(mcontext.getResources(),
                                R.mipmap.yu);
                    }
                    if (i == 7) {
                        weatherBitmap = BitmapFactory.decodeResource(mcontext.getResources(),
                                R.mipmap.yin);
                    }
                    if (i == 8) {
                        weatherBitmap = BitmapFactory.decodeResource(mcontext.getResources(),
                                R.mipmap.qing);
                    }
                    break;
                }
            }
        }
        return weatherBitmap;
    }

    public static int  getLocalTimeMinute(){

        Calendar calendar=Calendar.getInstance();
        return calendar.get(Calendar.MINUTE);

    }

    public static int  getLocalTimeHour(){

        Calendar calendar=Calendar.getInstance();
        return calendar.get(Calendar.HOUR_OF_DAY);

    }
    public static  int getLocalTimeDay(){

        Calendar calendar=Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);
    }


    /**
     * Is string effect boolean.
     *
     * @param str the str
     * @return the boolean
     */
    public static final boolean IsStringEffect (String str){

            if (IsStringNull(str)) {

                return false;
            }
            for (int i = 0; i < str.length(); i++) {

                char a = str.charAt(i);
                if (a < 48) {
                    Logger.d("a==" + a);
                    return false;
                }
                if (a > 57 && a < 65) {
                    Logger.d("a==" + a);
                    return false;
                }
                if (a > 90 && a < 97) {
                    Logger.d("a==" + a);
                    return false;
                }
                if (a > 122) {
                    Logger.d("a==" + a);
                    return false;
                }
            }
            return true;
        }
    }