package view;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.xiekun.myapplication.R;

public class DetailsMarkerView extends MarkerView {


    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     * @param layoutResource the layout resource to use for the MarkerView
     */

    TextView tv_updatetime,tv_tem,tv_wea;


    public DetailsMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
        tv_tem=findViewById(R.id.tv_tem);
        tv_updatetime=findViewById(R.id.tv_update);
        tv_wea=findViewById(R.id.tv_wea);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        super.refreshContent(e, highlight);
        tv_updatetime.setText(e.getX()+"");
        tv_tem.setText(e.getY()+"");
        tv_wea.setText(e.getData()+"");
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}
