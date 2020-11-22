package view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

public class CircleView extends View {

    private Paint paint;
    //控制点，根据这个点的坐标控制椭圆的形状
    private int distance=80;
    private float height=0;
    private RectF ovailRectf;

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public CircleView(Context context) {
        super(context);
        init();
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){

        paint=new Paint();
        ovailRectf=new RectF();
        distance=dip2px(25);
        height=dip2px(3);
    }

    private int dip2px(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        super.onSizeChanged(w, h, oldw, oldh);

            ovailRectf.left=0;
            ovailRectf.right=distance;
            ovailRectf.top=0;
            ovailRectf.bottom=height;
        Log.d("run","onsiez"+ovailRectf.toString());

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(ovailRectf!=null&&paint!=null){
        canvas.drawOval(ovailRectf,paint);
        }
    }
}
