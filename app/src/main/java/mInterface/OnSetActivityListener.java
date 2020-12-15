package mInterface;

public interface OnSetActivityListener {

    public void OpenWeatherTip(boolean open, int ...h);
    public void OpenWeatherDamage(boolean open);
    public void abnormalWeatherTip(boolean open);
    public void nightStop(boolean open);
    public void nightUpdate(boolean open);
    public void WeatherVoice(boolean open);
}
