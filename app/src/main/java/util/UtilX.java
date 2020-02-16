package util;


import com.orhanobut.logger.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * The type Util x.
 */
public class UtilX {

    public static boolean isDebug=true;

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
        Logger.d("Centigrade=="+str+"  int=="+num);
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