package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopups;
import com.xiekun.myapplication.R;

import java.util.List;

import Entity.PopItemBean;

public class WeatherViewClcikLong extends BaseAdapter {


 List<PopItemBean> popItemBeanList;
    Context context;

    public WeatherViewClcikLong(List<PopItemBean> popItemBeanList, Context context) {
        this.popItemBeanList = popItemBeanList;
        this.context = context;
    }


    @Override
    public int getCount() {
        return popItemBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return popItemBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        //如果view未被实例化过，缓存池中没有对应的缓存
        if (convertView == null) {
            viewHolder = new ViewHolder();
            // 由于我们只需要将XML转化为View，并不涉及到具体的布局，所以第二个参数通常设置为null
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.view_onclicklong_weather,
                    null);

            //对viewHolder的属性进行赋值
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.iv_careweather_item);
            viewHolder.itemTitle = (TextView) convertView.findViewById(R.id.tv_careweather_item);
            //通过setTag将convertView与viewHolder关联
            convertView.setTag(viewHolder);
        }else {
            //如果缓存池中有对应的view缓存，则直接通过getTag取出viewHolder
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // 取出bean对象
        PopItemBean bean = popItemBeanList.get(position);
                // 设置控件的数据
        viewHolder.imageView.setImageResource(bean.itemImageResId);
        viewHolder.itemTitle.setText(bean.itemTitle);

        return convertView;
    }




    class ViewHolder {

         ImageView imageView;
         TextView itemTitle;//标题

        public ViewHolder() {
        }

        public ViewHolder(ImageView itemImageResId, TextView itemTitle) {
            this.imageView = itemImageResId;
            this.itemTitle = itemTitle;

        }
    }
}
