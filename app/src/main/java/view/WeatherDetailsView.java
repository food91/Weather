package view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;
import com.xiekun.myapplication.R;

import java.util.ArrayList;
import java.util.Random;

import Entity.WeatherData;
import util.StringUtils;

public class WeatherDetailsView extends RelativeLayout {

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
    private WeatherData weatherData;
    private MPoint mPointWeather;
    private Paint weatherPaint;
    private Bitmap weatherBitmap;

    private Paint centigradePaint;
    private final static  int centigradeSize=178;
    private MPoint  centigradePoint;

    private Paint weaPaint;
    private final static  int WEASize=48;
    private MPoint  weaPoint;
    private String[] num=new String[2];
    private Paint RectPaint;
    private RectF rectF;

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

    public void getData(WeatherData weatherData){
        this.weatherData=weatherData;
        if(weatherData!=null){
            String wea=weatherData.getData().get(0).getWea_img();
            for(int i=0;i< StringUtils.IMGSTRING.length;i++){
                if(wea.equals(StringUtils.IMGSTRING[i])){
                    if(i==0){
                        weatherBitmap=BitmapFactory.decodeResource(getContext().getResources(),
                                R.mipmap.xue);
                    }
                    if(i==1){
                        weatherBitmap=BitmapFactory.decodeResource(getContext().getResources(),
                                R.mipmap.lei);
                    }
                    if(i==2){
                        weatherBitmap=BitmapFactory.decodeResource(getContext().getResources(),
                                R.mipmap.shachen);
                    }
                    if(i==3){
                        weatherBitmap=BitmapFactory.decodeResource(getContext().getResources(),
                                R.mipmap.wu);
                    }
                    if(i==4){
                        weatherBitmap=BitmapFactory.decodeResource(getContext().getResources(),
                                R.mipmap.bingbao);
                    }
                    if(i==5){
                        weatherBitmap=BitmapFactory.decodeResource(getContext().getResources(),
                                R.mipmap.yun);
                    }
                    if(i==6){
                        weatherBitmap=BitmapFactory.decodeResource(getContext().getResources(),
                                R.mipmap.yu);
                    }
                    if(i==7){
                        weatherBitmap=BitmapFactory.decodeResource(getContext().getResources(),
                                R.mipmap.yin);
                    }
                    if(i==8){
                        weatherBitmap=BitmapFactory.decodeResource(getContext().getResources(),
                                R.mipmap.qing);
                    }
                    break;
                }
            }
        }
 /*       String str=weatherData.getData().get(0).getTem();
        for(int i=0;i<str.length();i++){
            if((str.charAt(i) >= '0') && (str.charAt(i) <= '9')){
                num[0]+=str.charAt(i);
            }else{
                num[1]+=str.charAt(i);
            }
        }*/
    }

    private void init(){
        weatherPaint=new Paint();
        weatherPaint.setColor(Color.WHITE);
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
        START=0;
        mPointWeather=new MPoint();

        centigradePaint=new Paint();
        centigradePaint.setColor(Color.YELLOW);
        centigradePaint.setAntiAlias(true);
        centigradePaint.setTextSize(centigradeSize);
        centigradePaint.setStrokeWidth(5);
        centigradePaint.setStyle(Paint.Style.FILL);
        centigradePoint=new MPoint();

        weaPaint=new Paint();
        weaPaint.setColor(Color.YELLOW);
        weaPaint.setAntiAlias(true);
        weaPaint.setTextSize(WEASize);
        weaPaint.setStrokeWidth(5);
        weaPaint.setStyle(Paint.Style.FILL);
        weaPoint=new MPoint();

        RectPaint=new Paint();
        weaPaint.setColor(Color.BLUE);
        weaPaint.setAntiAlias(true);
        weaPaint.setStrokeWidth(5);
        weaPaint.setStyle(Paint.Style.FILL);
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
      postInvalidate();
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
        //计算天气图标坐标
        mPointWeather.setX(w-200);
        mPointWeather.setY(h/5);
        //计算点的位置
        int len=1;
        if(weatherData!=null){

            len=weatherData.getData().get(0).getTem().length();

        }
        centigradePoint.setX(w/2-centigradeSize*len/2+80);
        centigradePoint.setY(h/2-centigradeSize/2);
        if(weatherData!=null){
            len=weatherData.getData().get(0).getWea().length();
        }
        weaPoint.setX(w/2-WEASize*len/2);
        weaPoint.setY(h/2+centigradeSize/2-WEASize-40);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
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

        //画天气图标
        setWeatherImage(canvas);

        //写摄氏度
        canvas.drawText(weatherData.getData().get(0).getTem(),
                centigradePoint.getX(),
                centigradePoint.getY(),
                centigradePaint);
        canvas.drawText(weatherData.getData().get(0).getWea(),
                weaPoint.getX(),
                weaPoint.getY(),
                weaPaint);
    }

    private void setWeatherImage(Canvas canvas){
       if(weatherData==null||weatherPaint==null||weatherBitmap==null)
            return;

        canvas.drawBitmap(weatherBitmap,mPointWeather.getX(),mPointWeather.getY(),weatherPaint);
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
