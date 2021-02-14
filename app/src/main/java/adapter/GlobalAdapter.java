package adapter;

import android.view.View;
import android.widget.RelativeLayout;

import view.GlobalLoadingStatusView;


/**
 * @des:
 * @author: xk
 * @date: 2021/2/14 0014 11:33
 * @see {@link }
 */
public class GlobalAdapter implements Gloading.Adapter {

    @Override
    public View getView(Gloading.Holder holder, View convertView, int status) {
        GlobalLoadingStatusView loadingStatusView = null;
        //convertView为可重用的布局
        //Holder中缓存了各状态下对应的View
        //	如果status对应的View为null，则convertView为上一个状态的View
        //	如果上一个状态的View也为null，则convertView为null
        if (convertView != null && convertView instanceof GlobalLoadingStatusView) {
            loadingStatusView = (GlobalLoadingStatusView) convertView;
        }
        if (loadingStatusView == null) {
            loadingStatusView = new GlobalLoadingStatusView(holder.getContext(), holder.getRetryTask());
        }
        loadingStatusView.setStatus(status);
        return loadingStatusView;
    }

    @Override
    public View getView(Gloading.Holder holder, View convertView, int status, String text, int image) {
        GlobalLoadingStatusView loadingStatusView = null;
        //convertView为可重用的布局
        //Holder中缓存了各状态下对应的View
        //	如果status对应的View为null，则convertView为上一个状态的View
        //	如果上一个状态的View也为null，则convertView为null
        if (convertView != null && convertView instanceof GlobalLoadingStatusView) {
            loadingStatusView = (GlobalLoadingStatusView) convertView;
        }
        if (loadingStatusView == null) {
            loadingStatusView = new GlobalLoadingStatusView(holder.getContext(), holder.getRetryTask());
        }
        loadingStatusView.setStatus(status,text,image);
        return loadingStatusView;
    }


}
