package view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.xiekun.myapplication.R;

import org.w3c.dom.Attr;


public class TextViewRidus extends androidx.appcompat.widget.AppCompatTextView {


    private String mText;
    private int mTextColor;
    private int mTextSize;
    /**
     * 绘制时控制文本绘制的范围
     */
    private RectF mBound;
    private Paint mPaint;
    private Paint RectPaint;
    private float rectPaintStrokeWidth=2.5f;
    private float rx=30,ry=30;


    public TextViewRidus(Context context) {
        super(context);
        Logger.d("TextViewRidus 1 is ok");
    }

    public TextViewRidus(Context context, AttributeSet attrs) {
        super(context, attrs);
        Logger.d("TextViewRidus 2 is ok");
        init(context,attrs);
    }

    public TextViewRidus(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs){
        TypedArray typedArray=context.getTheme().obtainStyledAttributes(attrs, R.styleable.TextViewRidus
                ,0,0);
        mText=typedArray.getString(R.styleable.TextViewRidus_mText);
        mTextColor=typedArray.getColor(R.styleable.TextViewRidus_mTextColor, Color.BLUE);
        mTextSize= (int) typedArray.getDimension(R.styleable.TextViewRidus_mTextSize,100);
        rx=typedArray.getFloat(R.styleable.TextViewRidus_rx,50);
        ry=typedArray.getFloat(R.styleable.TextViewRidus_ry,50);

        typedArray.recycle();
        mPaint=new Paint();
        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mTextColor);
        mBound=new RectF();
        mBound.bottom=1000;
        mBound.left=0;
        mBound.right=1000;
        mBound.top=0;
        RectPaint=new Paint();
        RectPaint.setAntiAlias(true);
        RectPaint.setStyle(Paint.Style.FILL);
        RectPaint.setStrokeWidth(rectPaintStrokeWidth);
     //   RectPaint.setAlpha(50);
        RectPaint.setColor(getResources().getColor(R.color.skyblue));
        Logger.d("TextViewRidus 3 is ok");
    }

    public void setRectPaintStrokeWidth(float rectPaintStrokeWidth){
        this.rectPaintStrokeWidth=rectPaintStrokeWidth;
        RectPaint.setStrokeWidth(rectPaintStrokeWidth);
    }

    public void setRect(RectF rect,float rx,float ry){
        mBound=rect;
        this.rx=rx;
        this.ry=ry;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w_mod=MeasureSpec.getMode(widthMeasureSpec);
        int h_mod=MeasureSpec.getMode(heightMeasureSpec);
        int w=MeasureSpec.getSize(widthMeasureSpec),h=MeasureSpec.getSize(heightMeasureSpec);
        mBound.bottom=h;
        mBound.left=0;
        mBound.right=w;
        mBound.top=0;
        Logger.d("mBound is onMesure "+mBound.toString());
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        if(mBound!=null){
            canvas.drawRoundRect(mBound,
                    rx,
                    ry,
                    RectPaint);
            Logger.d("mBound  is   not null");
        }else{
            Logger.d("mBound  is   null");
        }
        super.onDraw(canvas);
    }
}
