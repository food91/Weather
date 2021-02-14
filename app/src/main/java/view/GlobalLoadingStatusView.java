package view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.NetworkUtils;
import com.xiekun.myapplication.R;

import static adapter.Gloading.STATUS_EMPTY_DATA;
import static adapter.Gloading.STATUS_LOADING;
import static adapter.Gloading.STATUS_LOAD_FAILED;
import static adapter.Gloading.STATUS_LOAD_SUCCESS;


/**
 * simple loading status view for global usage
 * @author billy.qi
 * @since 19/3/19 23:12
 */
@SuppressLint("ViewConstructor")
public class GlobalLoadingStatusView extends LinearLayout implements View.OnClickListener {

    private final TextView mTextView;
    private final Runnable mRetryTask;
    private final ImageView mImageView;

    public GlobalLoadingStatusView(Context context, Runnable retryTask) {
        super(context);
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER_HORIZONTAL);
        LayoutInflater.from(context).inflate(R.layout.view_global_loading_status, this, true);
        mImageView = findViewById(R.id.image);
        mTextView = findViewById(R.id.text);
        this.mRetryTask = retryTask;
        setBackgroundColor(0xFFF0F0F0);
    }

    public void setMsgViewVisibility(boolean visible) {
        mTextView.setVisibility(visible ? VISIBLE : GONE);
    }

    public void setStatus(int status,String str,int image) {
        boolean show = true;
        OnClickListener onClickListener = null;
        switch (status) {
            case STATUS_LOAD_SUCCESS: show = false; break;
            case STATUS_LOADING: ; break;
            case STATUS_LOAD_FAILED:
                Boolean networkConn = NetworkUtils.isConnected();
                if (networkConn != null && !networkConn) {
                    str = "网络失联了…\\n请联网后点击重试";
                }
                onClickListener = new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setVisibility(View.GONE);
                    }
                };
                image = R.drawable.icon_failed;
                break;
            case STATUS_EMPTY_DATA:
                str = "没有找到数据";
                image = R.drawable.icon_empty;
                break;
            default: break;
        }
        mImageView.setImageResource(image);
        setOnClickListener(onClickListener);
        mTextView.setText(str);
        setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public void setStatus(int status) {
        boolean show = true;
        OnClickListener onClickListener = null;
        int image = R.drawable.loading;
        int str = R.string.str_none;
        switch (status) {
            case STATUS_LOAD_SUCCESS: show = false; break;
            case STATUS_LOADING: str = R.string.loading; break;
            case STATUS_LOAD_FAILED:
                str = R.string.load_failed;
                Boolean networkConn = NetworkUtils.isConnected();
                if (networkConn != null && !networkConn) {
                    str = R.string.load_failed_no_network;
                }
                onClickListener = this;
                image = R.drawable.icon_failed;
                break;
            case STATUS_EMPTY_DATA:
                str = R.string.empty;
                image = R.drawable.icon_empty;
                break;
            default: break;
        }
        mImageView.setImageResource(image);
        setOnClickListener(onClickListener);
        mTextView.setText(str);
        setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onClick(View v) {
        if (mRetryTask != null) {
            mRetryTask.run();
        }
    }
}
