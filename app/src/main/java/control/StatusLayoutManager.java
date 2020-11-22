package control;


import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.view.ViewStructure;
import android.view.ViewStub;

import androidx.appcompat.app.AlertDialog;

import view.AbsViewStubLayout;
import view.StateFrameLayout;

public class StatusLayoutManager {

    private Context context;



    final StateFrameLayout rootFrameLayout;
    private int contentLayoutResId=0;
    private int loadingLayoutResId=0;
    private ViewStub vs_netError=null;
    private ViewStub vs_emptyData=null;
    private ViewStub vs_defaultError=null;
    private boolean isshowloading;
    private final AbsViewStubLayout emptyDataLayout;
    private final OnShowHideViewListener onShowHideViewListener;
    private final OnRetryListener onRetryListener;
    private final OnNetworkListener onNetworkListener;

    public AbsViewStubLayout getEmptyDataLayout() {
        return emptyDataLayout;
    }



    public OnShowHideViewListener getOnShowHideViewListener() {
        return onShowHideViewListener;
    }

    public OnRetryListener getOnRetryListener() {
        return onRetryListener;
    }

    public OnNetworkListener getOnNetworkListener() {
        return onNetworkListener;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getContentLayoutResId() {
        return contentLayoutResId;
    }

    public void setContentLayoutResId(int contentLayoutResId) {
        this.contentLayoutResId = contentLayoutResId;
    }

    public int getLoadingLayoutResId() {
        return loadingLayoutResId;
    }

    public void setLoadingLayoutResId(int loadingLayoutResId) {
        this.loadingLayoutResId = loadingLayoutResId;
    }

    public ViewStub getVs_netError() {
        return vs_netError;
    }

    public void setVs_netError(ViewStub vs_netError) {
        this.vs_netError = vs_netError;
    }

    public ViewStub getVs_emptyData() {
        return vs_emptyData;
    }

    public void setVs_emptyData(ViewStub vs_emptyData) {
        this.vs_emptyData = vs_emptyData;
    }

    public ViewStub getVs_defaultError() {
        return vs_defaultError;
    }

    public void setVs_defaultError(ViewStub vs_defaultError) {
        this.vs_defaultError = vs_defaultError;
    }


    public static Builder newBuilder(Context context){return new Builder(context)};

    public StatusLayoutManager(Builder builder,boolean wrapContent) {
        this.context=builder.context;
        rootFrameLayout=new StateFrameLayout(context);
        ViewGroup.LayoutParams layoutParams ;
        //是否包裹内容
        if (wrapContent){
            layoutParams = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }else {
            layoutParams = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        rootFrameLayout.setLayoutParams(layoutParams);
        rootFrameLayout.setBackgroundColor(Color.WHITE);
        rootFrameLayout.setStatusLayoutManager(this);

    }

    public void showEmptyData(){
        showEmptyData(0,"");
    }

    public void showLayoutEmptyData(Object...objects){
        rootFrameLayout.showLayoutEmptyData(objects);
    }

    /**
     * 判断是否在showLoading中
     * @return
     */
    public boolean isShowLoading(){
        return rootFrameLayout.isLoading();
    }


    public void showloading(){
        if(!rootFrameLayout.isLoading()){
            rootFrameLayout.showLoading();
        }
    }

    /**
     * 隐藏loading
     */
    public void goneLoading() {
        rootFrameLayout.goneLoading();
    }

    public static final class Builder{

        private Context context;

   }
}
