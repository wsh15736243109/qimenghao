package yh.qimenghao.product.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import yh.qimenghao.product.R;
import yh.qimenghao.product.model.bean.ToothbrushBean;
import yh.qimenghao.product.util.MoreUtils;

/**
 * Created by Joy on 2019/1/11.15:25
 */
public class DetailDayAdapter extends BaseAdapter {
    private Context context;
    private List<ToothbrushBean.DetailsBean> detailsBeanList =new ArrayList<>();

    public void setDetailsBeanList(List<ToothbrushBean.DetailsBean> detailsBeanList) {
        this.detailsBeanList = detailsBeanList;
        notifyDataSetChanged();
    }

    public DetailDayAdapter(Context context) {
        this.context = context;
    }

    public DetailDayAdapter(Context context, List<ToothbrushBean.DetailsBean> detailsBeanList) {
        this.context = context;
        this.detailsBeanList = detailsBeanList;
    }

    @Override
    public int getCount() {
        return detailsBeanList.size();
    }

    @Override
    public ToothbrushBean.DetailsBean getItem(int position) {
        return detailsBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ToothbrushBean.DetailsBean detailsBean=detailsBeanList.get(position);
        View view = LayoutInflater.from(context).inflate(R.layout.everyday_layout, parent, false);
        TextView mCountTv;
        TextView mTomeTv;
        mCountTv = view.findViewById(R.id.countTv);
        mTomeTv = view.findViewById(R.id.tomeTv);
        mCountTv.setText(context.getResources().getString(R.string.di)+(position+1)+context.getResources().getString(R.string.chisuaya));
        mTomeTv.setText(MoreUtils.timeStamp2Date(String.valueOf(detailsBean.getTimestamp()),null).substring(11,16));
        return view;
    }
}
