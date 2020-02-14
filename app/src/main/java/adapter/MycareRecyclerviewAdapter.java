package adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xiekun.myapplication.R;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MycareRecyclerviewAdapter extends RecyclerView.Adapter {



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class Favourite_cardview extends RecyclerView.ViewHolder{

        @BindView(R.id.iv_careweather)
        ImageView iv_city;

        @BindView(R.id.citycare_text)
        TextView tv_city;

        @BindView(R.id.centigradecare_text)
        TextView centigrade；

        @BindView(R.id.tipcare_weather)
        TextView tv_tip;


        public Favourite_cardview(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
