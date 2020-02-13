package view;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.xiekun.myapplication.R;

import data.WeatherData;

public class DetailsMarkerView extends MarkerView {


    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     * @param layoutResource the layout resource to use for the MarkerView
     */

    TextView tv_updatetime,tv_tem,tv_wea;

    WeatherData weatherData;

    public DetailsMarkerView(Context context, int layoutResource,WeatherData weatherData) {
        super(context, layoutResource);
        tv_tem=findViewById(R.id.tv_tem);
        tv_updatetime=findViewById(R.id.tv_update);
        tv_wea=findViewById(R.id.tv_wea);
        this.weatherData=weatherData;
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        super.refreshContent(e, highlight);
        tv_updatetime.setText("时间:  "+weatherData.getData().get((int) e.getX()).getDate());
        tv_tem.setText("今日温度:   "+e.getY());
        tv_wea.setText("天气状况:   "+e.getData());
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}
