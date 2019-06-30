package yh.qimenghao.product.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import yh.qimenghao.product.R;
import yh.qimenghao.product.SelfApp;
import yh.qimenghao.product.model.bean.RopeDataBean;
import yh.qimenghao.product.util.MoreUtils;

/**
 * Created by Joy on 2018/12/12.17:53
 */
public class TodayDataAdapter extends BaseAdapter {
    List<RopeDataBean.TodaydataBean> todayData;

    public TodayDataAdapter(List<RopeDataBean.TodaydataBean> todayData) {
        this.todayData = todayData;
    }

    @Override
    public int getCount() {
        return todayData.size();
    }

    @Override
    public RopeDataBean.TodaydataBean getItem(int position) {
        return todayData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = LayoutInflater.from(SelfApp.getApp().getAppContext()).inflate(R.layout.today_data_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        RopeDataBean.TodaydataBean todaydataBean = todayData.get(position);
        if (todaydataBean.getRopeType().equals("1")) {
            viewHolder.sportTypeImg.setImageResource(R.mipmap.timing);
            viewHolder.sportTypeText.setText(R.string.timing);
        } else if (todaydataBean.getRopeType().equals("2")) {
            viewHolder.sportTypeImg.setImageResource(R.mipmap.quota);
            viewHolder.sportTypeText.setText(R.string.num_jump);
        } else {
            viewHolder.sportTypeImg.setImageResource(R.mipmap.freejump);
            viewHolder.sportTypeText.setText(R.string.free_jump);
        }
            viewHolder.sportTimestamp.setText(MoreUtils.timeStamp2Date(String.valueOf(todaydataBean.getTimestamp()),null));
            viewHolder.sportTime.setText(MoreUtils.s2ss(todaydataBean.getRopeDuration()));
            viewHolder.sportNum.setText(todaydataBean.getRopeCount()+SelfApp.getApp().getResources().getString(R.string.ge));
            viewHolder.sportCalorie.setText(todaydataBean.getCalorie()<=1000?todaydataBean.getCalorie()+SelfApp.getApp().getResources().getString(R.string.calorie):todaydataBean.getCalorie()/1000+"K"+SelfApp.getApp().getResources().getString(R.string.calorie));
            viewHolder.sportSpeed.setText(todaydataBean.getSpeed()+SelfApp.getApp().getResources().getString(R.string.ge)+"/"+SelfApp.getApp().getResources().getString(R.string.minute));
        return v;
    }

    static class ViewHolder {
        @BindView(R.id.sportTypeImg)
        ImageView sportTypeImg;
        @BindView(R.id.sportTypeText)
        TextView sportTypeText;
        @BindView(R.id.sportTimestamp)
        TextView sportTimestamp;
        @BindView(R.id.sportTime)
        TextView sportTime;
        @BindView(R.id.sportNum)
        TextView sportNum;
        @BindView(R.id.sportCalorie)
        TextView sportCalorie;
        @BindView(R.id.sportSpeed)
        TextView sportSpeed;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
