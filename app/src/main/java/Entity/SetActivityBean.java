package Entity;

import java.io.Serializable;

/**
 * @des:Setactivity的Json
 * @author: xk
 * @date: 2020/12/14 0014 21:46
 * @see {@link }
 */
public class SetActivityBean implements Serializable {

    private boolean SetAc_WeatherReport;
    private boolean SetAc_WeatherDamage;
    private boolean SetAc_WeatherAbnormal;
    private boolean SetAc_NightStop;
    private boolean SetAc_NightUpdate;
    private boolean WeatherVoice;

    @Override
    public String toString() {
        return "SetActivityBean{" +
                "SetAc_WeatherReport=" + SetAc_WeatherReport +
                ", SetAc_WeatherDamage=" + SetAc_WeatherDamage +
                ", SetAc_WeatherAbnormal=" + SetAc_WeatherAbnormal +
                ", SetAc_NightStop=" + SetAc_NightStop +
                ", SetAc_NightUpdate=" + SetAc_NightUpdate +
                ", WeatherVoice=" + WeatherVoice +
                '}';
    }
}
