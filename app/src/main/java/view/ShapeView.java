package view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Random;

public class ShapeView extends View {

    private Paint shapePaint;
    public  final static int RECT=0;
    public final static  int TRIANGLE=1;
    public final static  int RROUND=2;
    private int ShapeState=RECT;
    private int speed=20,distance=80,time=4;
    //园和三角形都为正方形的内接图形
    private Point heightPoint,goPoint;
    private Path shapePath;
    private RectF rectF;
    private ValueAnimator valueAnimatorDown;


    public ShapeView(Context context) {

        super(context);
        init();
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs) {

        super(context, attrs);
        init();
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        shapePaint=new Paint();
        shapePath=new Path();
        shapePaint.setStyle(Paint.Style.FILL);
        rectF=new RectF();
        heightPoint=new Point();
        goPoint=new Point();
    }

    public int getShapeState() {
        return ShapeState;
    }

    public void setShapeState(int shapeState) {
        ShapeState = shapeState;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width= MeasureSpec.getSize(widthMeasureSpec);
        int height= MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(Math.min(width, height), Math.min(width, height));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //确定最高点
        heightPoint.x=w/2;
        heightPoint.y=h/2-distance;
        goPoint=heightPoint;


    }



    private void setPath(){
        //三角形
        if(ShapeState==TRIANGLE){
            shapePath.moveTo(getWidth() / 2, 0);
            shapePath.lineTo(0, (float) ((getWidth()/2)* Math.sqrt(3)));
            shapePath.lineTo(getWidth(), (float) ((getWidth()/2)* Math.sqrt(3)));
            shapePath.close();
            shapePaint.setColor(Color.MAGENTA);
        }
        //矩形
        else if(ShapeState==RECT){
            rectF.left=0;
            rectF.right=getWidth();
            rectF.bottom=getHeight();
            rectF.top=0;
            shapePath.addRect(rectF, Path.Direction.CW);
            Log.d("ss","-1111------"+rectF.toString());
            shapePaint.setColor(Color.BLUE);
        }
        //圆形
        else if(ShapeState==RROUND){
            shapePath.addCircle(getWidth()/2,
                    getWidth()/2,
                    getWidth()/2,
                    Path.Direction.CW);
            shapePaint.setColor(Color.YELLOW);
        }
    }

    public void exChangeShapeView(){
        int random=new Random().nextInt(3);
        Log.d("run","romdam==="+random);
        ShapeState=random;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(shapePaint!=null&&shapePath!=null){
            setPath();
            canvas.drawPath(shapePath,shapePaint);
            shapePath.reset();
        }

    }
}
