package view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import control.StatusLayoutManager;

public class StateFrameLayout extends FrameLayout {

    /**
     *  loading 加载id
     */
    public final static int LAYOUT_LOADING_ID=1;

    /**
     *  内容id
     */
    public static final int LAYOUT_CONTENT_ID = 2;
    /**
     *  网络异常id
     */
    public static final int LAYOUT_NETWORK_ERROR_ID = 4;

    /**
     *  异常id
     */
    public static final int LAYOUT_DEFAULTERROR_ID = 3;

    /**
     *  空数据id
     */
    public static final int LAYOUT_EMPTY_DATA_ID = 5;

    StatusLayoutManager mstatusLayoutManager;
    /**
     *  存放布局集合
     */
    private SparseArray<View> layoutSparseArray = new SparseArray<>();


    public StateFrameLayout(@NonNull Context context) {
        super(context);
    }

    public StateFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StateFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public StateFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setStatusLayoutManager(StatusLayoutManager statusLayoutManager){

        mstatusLayoutManager=statusLayoutManager;
        addAllLayoutToRootLayout();
    }

    private void addAllLayoutToRootLayout(){

        if(mstatusLayoutManager.getContentLayoutResId()!=0){
            addLayoutResId(mstatusLayoutManager.getContentLayoutResId(),
                    StateFrameLayout.LAYOUT_LOADING_ID);
        }
        if(mstatusLayoutManager.getLoadingLayoutResId()!=0){
            addLayoutResId(mstatusLayoutManager.getLoadingLayoutResId(),
                    StateFrameLayout.LAYOUT_LOADING_ID);
        }
        if(mstatusLayoutManager.getVs_defaultError()！=null){
            addLayoutResId(mstatusLayoutManager.getVs_defaultError(),
                    StateFrameLayout.LAYOUT_DEFAULTERROR_ID);
        }
        if(mstatusLayoutManager.getVs_emptyData()!=null){
            addLayoutResId(mstatusLayoutManager.getVs_emptyData(),
                    StateFrameLayout.LAYOUT_EMPTY_DATA_ID);
        }
        if(mstatusLayoutManager.getVs_netError()!=null){
            addLayoutResId(mstatusLayoutManager.getVs_netError(),
                    StateFrameLayout.LAYOUT_NETWORK_ERROR_ID);
        }

    }

    private void addLayoutResId(@LayoutRes int layoutReId,int id){
        View resView= LayoutInflater.from(mstatusLayoutManager.getContext())
                .inflate(layoutReId,null);
        if(id==StateFrameLayout.LAYOUT_LOADING_ID){
            resView.setOnClickListener(null);
        }
        layoutSparseArray.put(id,resView);
        addView(resView);
    }

    public void showLayoutEmptyData(Object...objects){
            if(inflateLayout(LAYOUT_EMPTY_DATA_ID)){
                showHideViewById(LAYOUT_EMPTY_DATA_ID);
                AbsViewStubLayout emptyDataLayout=mstatusLayoutManager.getEmptyDataLayout();
                if (emptyDataLayout != null) {
                    emptyDataLayout.setData(objects);
                }
            }
    }

    private void  showHideViewById(int id){
        for(int i=0;i<layoutSparseArray.size();i++){
            int key = layoutSparseArray.keyAt(i);
            View valueView = layoutSparseArray.valueAt(i);
            if(key!=id){
                if(valueView.getVisibility()!=View.GONE){
                    valueView.setVisibility(View.GONE);
                }
                if(mstatusLayoutManager.getOnShowHideViewListener()!=null){
                    mstatusLayoutManager.getOnShowHideViewListener().
                            onHideView(valueView,key);
                }
            }else{
                if(valueView.getVisibility()==View.GONE){
                    valueView.setVisibility(View.VISIBLE);
                    if(mstatusLayoutManager.getOnShowHideViewListener() != null) {
                        mstatusLayoutManager.getOnShowHideViewListener().onShowView(valueView, key);
                    }
                }
            }
        }
    }

    private boolean inflateLayout(int id){
        boolean isShow=true;
        if(layoutSparseArray.get(i)!=null){
            return isShow;
        }
        //网络异常
        if(id==LAYOUT_NETWORK_ERROR_ID){
            if(mstatusLayoutManager.getVs_netError()!=null){
                View view=mstatusLayoutManager.getVs_netError().inflate();
                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    mstatusLayoutManager.getOnNetworkListener().onNetwork();
                    }
                });
                layoutSparseArray.put(id,view);
                isShow=true;
            }else{
                isShow=false;
            }
            //其他错误
        }else if(id==LAYOUT_DEFAULTERROR_ID){
            if(mstatusLayoutManager.getVs_defaultError()!=null){
                View view=mstatusLayoutManager.getVs_defaultError().inflate();
                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    mstatusLayoutManager.getOnRetryListener().onRetry();
                    }
                });
                layoutSparseArray.put(id,view);
                isShow=true;
            }else{
                isShow=false;
            }
            //数据为空
        }else if(id==LAYOUT_EMPTY_DATA_ID){
            if(mstatusLayoutManager.getVs_emptyData()!=null){
                View view=mstatusLayoutManager.getVs_emptyData().inflate();
                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mstatusLayoutManager.getOnRetryListener().onRetry();
                    }
                });
                layoutSparseArray.put(id,view);
                isShow=true;
            }else{
                isShow=false;
            }

        }
        return isShow;
    }

    /**
     * 判断是否正在loading中
     * @return                      true 表示loading正在加载中
     */
    public boolean isLoading(){
        View view = layoutSparseArray.get(LAYOUT_LOADING_ID);
        if (view != null) {
            if (view.getVisibility() == View.VISIBLE){
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     *  显示loading
     */
    public void showLoading() {
        if (layoutSparseArray.get(LAYOUT_LOADING_ID) != null) {
            showHideViewById(LAYOUT_LOADING_ID);
        }
    }

    /**
     * 关闭showLoading
     * @return
     */
    public boolean goneLoading(){
        if (layoutSparseArray.get(LAYOUT_LOADING_ID) != null) {
            View view = layoutSparseArray.get(LAYOUT_LOADING_ID);
            if (view.getVisibility() == View.VISIBLE){
                view.setVisibility(View.GONE);
                return true;
            }else {
                return false;
            }
        } else {
            return false;
        }
    }



}
