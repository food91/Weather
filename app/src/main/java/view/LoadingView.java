package view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.xiekun.myapplication.R;

import util.UtilX;

public class LoadingView extends LinearLayout {

    private ShapeView shapeView;
    private CircleView circleView;
    private ValueAnimator valueAnimator;
    //加速度
    private float a=0;
    private float mTranslationYDistance=0;
    private final int AIMATOR_TIME=300;
    private boolean isRunAnimator;


    public LoadingView(Context context) {

        super(context);
        initLayout();
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {

        super(context, attrs);
        initLayout();
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout();
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initLayout();
    }

    private int dip2px(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
    }


    private void initLayout(){
        UtilX.LogX("loading  init");
        mTranslationYDistance=dip2px(80);
        inflate(getContext(), R.layout.loadingview_layout,this);
        shapeView=findViewById(R.id.shapeview_loading);
        circleView=findViewById(R.id.circleview_loading);
    }

    private void startFailAnimator(){
        UtilX.LogX("loadingview startFailAnimator-----");
        if(!isRunAnimator)
            return;
        ValueAnimator valueAnimatorCircle= ValueAnimator.ofFloat((float) 1f,0.3f);
        valueAnimatorCircle.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float scale= (float) animation.getAnimatedValue();
                circleView.setScaleX(scale);
            }
        });
        ValueAnimator valueAnimatorShape= ValueAnimator.ofFloat(0,mTranslationYDistance);
        valueAnimatorShape.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value= (float) animation.getAnimatedValue();
                shapeView.setTranslationY(value);
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(valueAnimatorShape, valueAnimatorCircle);
        animatorSet.setDuration(AIMATOR_TIME);
        animatorSet.setInterpolator(new AccelerateInterpolator());
        animatorSet.start();
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {


            }

            @Override
            public void onAnimationEnd(Animator animation) {
                shapeView.exChangeShapeView();
                    startUpAnimation();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {


            }
        });
        animatorSet.start();
    }

    private void startUpAnimation(){
        if(!isRunAnimator)
            return;
        ValueAnimator valueAnimatorCircle= ValueAnimator.ofFloat((float) 0,3,1.0f);
        valueAnimatorCircle.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float scale= (float) animation.getAnimatedValue();
                circleView.setScaleX(scale);
            }
        });
        ValueAnimator valueAnimatorShape= ValueAnimator.ofFloat(mTranslationYDistance,0);
        valueAnimatorShape.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value= (float) animation.getAnimatedValue();
                shapeView.setTranslationY(value);
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(valueAnimatorShape, valueAnimatorCircle);
        animatorSet.setDuration(AIMATOR_TIME);
        animatorSet.setInterpolator(new AccelerateInterpolator());
        animatorSet.start();
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                startRotationAnimator();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
               startFailAnimator();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                shapeView.exChangeShapeView();
                startUpAnimation();

            }
        });
        animatorSet.start();
    }

    /**
     * 旋转
     */
    private void startRotationAnimator() {
        if(!isRunAnimator)
            return;
        ObjectAnimator rotationAnimator = null;
        switch (shapeView.getShapeState()) {
            case ShapeView.RECT:  //旋转180
                rotationAnimator = ObjectAnimator.ofFloat(shapeView, "rotation", 0, 180);
                break;
            case ShapeView.TRIANGLE:  //旋转 120
                rotationAnimator = ObjectAnimator.ofFloat(shapeView, "rotation", 0, -120);
                break;
            default:
                rotationAnimator = ObjectAnimator.ofFloat(shapeView, "rotation", 0, 0);
                break;
        }
        rotationAnimator.setDuration(AIMATOR_TIME);
        rotationAnimator.setInterpolator(new DecelerateInterpolator());
        rotationAnimator.start();
    }

    @Override
    public void setVisibility(int visibility) {
        UtilX.LogX("loading  setvisiblity");
        super.setVisibility(visibility);
        if(visibility== View.GONE||visibility==View.INVISIBLE){
            isRunAnimator=false;
            shapeView.clearAnimation();
            circleView.clearAnimation();
        }
        if(visibility==View.VISIBLE){
            post(new Runnable() {
                @Override
                public void run() {
                    isRunAnimator=true;
                    startFailAnimator();
                }
            });
        }
    }
}
