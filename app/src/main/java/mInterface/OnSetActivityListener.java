package mInterface;

public interface OnSetActivityListener {

    public void OpenNotiTime(boolean open, int ...h);
    public void weatherTip(boolean open);
    public void abnormalWeatherTip(boolean open);
    public void nightStop(boolean open);
    public void nightUpdate(boolean open);
    public void WeatherVoice(boolean open);
}
