package view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Random;

public class WeatherDetailsView extends View {

    private final String LOGSTRING="WeatherDetailsView";
    //球体画笔
    private Paint BallmPaint;
    private final static int Qualdx= 300;
    //曲线画笔
    private Paint QualPaint;
    private Path QualPath;
    private   int QualHeight=0;
    private ArrayList<MPoint> WeatherCNum;
    private Paint  coordinateSystemPaint;
    private final static int DropBallSpeed=6;
    private final static int BollNum=6;
    private final static int ballRadius=25;
    private static int START=0;
    private int BallBoundaryX=0;
    private int BallBoundaryY=0;
    private MPoint WaveStartPoint,WaveEndPoint;
    private ValueAnimator mAnimator;
    private int mOffsetX=0;

    class MPoint{
        float x;
        float y;

        public void setX(float x) {
            this.x = x;
        }

        public void setY(float y) {
            this.y = y;
        }

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }
    }



    public WeatherDetailsView(Context context) {
        super(context);
    }

    public WeatherDetailsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WeatherDetailsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public WeatherDetailsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        BallmPaint=new Paint();
        BallmPaint.setColor(Color.WHITE);
        WeatherCNum=new ArrayList<>();
        QualPaint=new Paint();
        QualPaint.setColor(Color.WHITE);
        QualPaint.setAntiAlias(true);
        QualPaint.setStrokeWidth(5);
        QualPaint.setStyle(Paint.Style.FILL);
        QualPath=new Path();
        WaveEndPoint=new MPoint();
        WaveStartPoint=new MPoint();
        mAnimator = ValueAnimator.ofInt(0, Qualdx*2);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mOffsetX = (int) animation.getAnimatedValue();
                invalidate();
            }
        });

        mAnimator.setInterpolator(new LinearInterpolator());

        mAnimator.setDuration(1000);
        mAnimator.setRepeatCount(-1);
        mAnimator.start();
    }



    //形成球体下落描绘
    private void ChangeData(){

        int dx=getWidth()/BollNum;

        for(int i=0;i<BollNum;i++){

            WeatherCNum.get(i).setY(WeatherCNum.get(i).y+DropBallSpeed);
            //边界检测，错误则重置
            if(CheckBoundBall(WeatherCNum.get(i))){
                setRandomPoint(WeatherCNum.get(i),dx*i,dx*(i+1));
            }

        }
      invalidate();
    }

    //边界检测,为0后重置小球，小球X位置随机，y=0
    //越界返回true
    private boolean CheckBoundBall(MPoint mPoint){

        if(mPoint==null){
            throw new NullPointerException();
        }
        if(mPoint.getY()<0||mPoint.getY()>BallBoundaryY
        ||mPoint.getX()<0||
        mPoint.getX()>BallBoundaryX){
            return true;
        }
        return false;
    }

    private void setRandomPoint(MPoint mPoint){

        Random r = new Random();
        int x = r.nextInt(BallBoundaryX) ;
        int y=r.nextInt(BallBoundaryY);
        mPoint.setX(x);
        mPoint.setY(y);

    }

    private void setRandomPoint(MPoint mPoint,int startx,int endx){
        Random r = new Random();
        int x = r.nextInt(endx-startx)+startx;
        int y=r.nextInt(BallBoundaryY);
        mPoint.setX(x);
        mPoint.setY(y);

    }


    private void getData(){

        int dx=getWidth()/BollNum;

        for(int i=0;i<BollNum;i++){

            MPoint mPoint=new MPoint();
            setRandomPoint(mPoint,dx*i,dx*(i+1));
            Log.d("sss","mpoint==="+mPoint.getX());
            WeatherCNum.add(mPoint);


        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int w_mod=MeasureSpec.getMode(widthMeasureSpec);
        int h_mod=MeasureSpec.getMode(heightMeasureSpec);
        int w=MeasureSpec.getSize(widthMeasureSpec),h=MeasureSpec.getSize(heightMeasureSpec);
        Log.d(LOGSTRING,"onMeasure,w="+w+"-h="+h);

        setMeasuredDimension(w,h);
        BallBoundaryX=w;
        BallBoundaryY=h/3;
        //波浪线位置
        QualHeight=h/3*2;
        WaveStartPoint.setY(QualHeight);
        WaveStartPoint.setX(0);
        WaveEndPoint.setX(w);
        WaveEndPoint.setY(QualHeight);
        Log.d("sss","onMeasure  BoundX=="+BallBoundaryX+"BoundY=="+BallBoundaryY);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d(LOGSTRING,"onlayout");
        getData();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //背景颜色

        if(WeatherCNum!=null){
            //画球
            for(int i=0;i<WeatherCNum.size();i++){
                drawCircle(canvas,WeatherCNum.get(i));
            }
        }

        //在2/3处画弧线
        drawWave(canvas,WaveStartPoint,WaveEndPoint,4);



        //开启自动下落计算线程
        if(START==0){
            START=1;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true){
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        ChangeData();
                    }
                }
            }).start();
        }
    }

    private void drawWave(Canvas canvas,MPoint startpoint,MPoint endpoint,int betweenNum){
    QualPath.reset();
    QualPath.moveTo(-Qualdx*2+mOffsetX,startpoint.getY());
    for(int i=-Qualdx;i<getWidth()+Qualdx*2;i+=Qualdx*2){
        QualPath.rQuadTo(Qualdx/2, -100, Qualdx, 0);
        QualPath.rQuadTo(Qualdx/2, 100, Qualdx, 0);
    }

        //闭合路径波浪以下区域
        QualPath.lineTo(getWidth(), getHeight());
        QualPath.lineTo(0, getHeight());
        QualPath.close();
        canvas.drawPath(QualPath,QualPaint);
    }

    private void drawCircle(Canvas canvas,MPoint mPoint){

        canvas.drawCircle(mPoint.getX(),
                mPoint.getY(),
                ballRadius,
                BallmPaint);

    }

}
