package view;

import android.content.Context;
import android.view.View;
import android.view.ViewStub;

import androidx.annotation.LayoutRes;

public abstract class AbsViewStubLayout {

    /**
     * ViewStub用来加载网络异常，空数据等页面
     */
    private ViewStub mLayoutVs;
    /**
     * View用来加载正常视图页面
     */
    private View mContentView;

    protected void initLayout(Context context , @LayoutRes int layoutResId) {
        mLayoutVs = new ViewStub(context);
        mLayoutVs.setLayoutResource(layoutResId);
    }

    public ViewStub getLayoutVs() {
        return mLayoutVs;
    }

    public void setView(View contentView) {
        mContentView = contentView;
    }

    /**
     * 设置数据
     * @param objects           数据
     */
    public abstract void setData(Object... objects);

}
