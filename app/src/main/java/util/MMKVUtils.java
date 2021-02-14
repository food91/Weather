package util;

import android.content.Context;
import android.content.SharedPreferences;

import com.tencent.mmkv.MMKV;

import Entity.LoginData;

/**
 * @des:
 * @author: xk
 * @date: 2021/2/12 0012 01:04
 * @see {@link }
 */
public class MMKVUtils {

    static MMKV mmkv = MMKV.defaultMMKV();

    public static String getLoginData(){
        String user = mmkv.getString(LoginData.LOGINDATA, "");
        return user;
    }

    public static void setLoginData(String data){
        mmkv.putString(LoginData.LOGIN,data);
    }

}
