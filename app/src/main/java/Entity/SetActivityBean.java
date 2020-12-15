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

    public boolean isSetAc_WeatherReport() {
        return SetAc_WeatherReport;
    }

    public void setSetAc_WeatherReport(boolean setAc_WeatherReport) {
        SetAc_WeatherReport = setAc_WeatherReport;
    }

    public boolean isSetAc_WeatherDamage() {
        return SetAc_WeatherDamage;
    }

    public void setSetAc_WeatherDamage(boolean setAc_WeatherDamage) {
        SetAc_WeatherDamage = setAc_WeatherDamage;
    }

    public boolean isSetAc_WeatherAbnormal() {
        return SetAc_WeatherAbnormal;
    }

    public void setSetAc_WeatherAbnormal(boolean setAc_WeatherAbnormal) {
        SetAc_WeatherAbnormal = setAc_WeatherAbnormal;
    }

    public boolean isSetAc_NightStop() {
        return SetAc_NightStop;
    }

    public void setSetAc_NightStop(boolean setAc_NightStop) {
        SetAc_NightStop = setAc_NightStop;
    }

    public boolean isSetAc_NightUpdate() {
        return SetAc_NightUpdate;
    }

    public void setSetAc_NightUpdate(boolean setAc_NightUpdate) {
        SetAc_NightUpdate = setAc_NightUpdate;
    }

    public boolean isWeatherVoice() {
        return WeatherVoice;
    }

    public void setWeatherVoice(boolean weatherVoice) {
        WeatherVoice = weatherVoice;
    }

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
