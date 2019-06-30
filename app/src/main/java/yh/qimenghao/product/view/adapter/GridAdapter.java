package yh.qimenghao.product.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import yh.qimenghao.product.R;
import yh.qimenghao.product.model.bean.ToothbrushBean;
import yh.qimenghao.product.util.MoreUtils;

/**
 * Created by Joy on 2019/1/4.15:30
 */
public class GridAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<ToothbrushBean> lists = new ArrayList<>();
    private OnClickListener onClickListener;


    public GridAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Vh(LayoutInflater.from(context).inflate(R.layout.grid_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ToothbrushBean toothbrushBean = lists.get(position);
        ((Vh) holder).ll.setOnClickListener(v -> {
            if (onClickListener != null) {
                onClickListener.onClick(position);
            }
        });
        if (toothbrushBean.getDetails() == null) {
            ((Vh) holder).mAtar2.setImageResource(R.mipmap.star_off);
            ((Vh) holder).mAtar1.setImageResource(R.mipmap.star_off);
        } else if (toothbrushBean.getDetails().size() >= 2) {
            ((Vh) holder).mAtar2.setImageResource(R.mipmap.star);
            ((Vh) holder).mAtar1.setImageResource(R.mipmap.star);

        } else if (toothbrushBean.getDetails().size() == 1) {
            ((Vh) holder).mAtar2.setImageResource(R.mipmap.star_off);
            ((Vh) holder).mAtar1.setImageResource(R.mipmap.star);
        } else {
            ((Vh) holder).mAtar2.setImageResource(R.mipmap.star_off);
            ((Vh) holder).mAtar1.setImageResource(R.mipmap.star_off);
            //((Vh) holder).cardView.setCardBackgroundColor(context.getResources().getColor(R.color.main_red0));
        }
        String date = MoreUtils.timeStamp2Date(String.valueOf(toothbrushBean.getDate()), null);
        ((Vh) holder).mDate.setText(date.substring(5, 10));
        ((Vh) holder).week.setText(MoreUtils.dateToWeek(date.substring(0, 10)));
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public interface OnClickListener {
        void onClick(int p);
    }


    public List<ToothbrushBean> getLists() {
        return lists;
    }

    public void setLists(List<ToothbrushBean> lists) {
        this.lists = lists;
        notifyDataSetChanged();
    }

    public void addLists(List<ToothbrushBean> lists) {
        this.lists.addAll(lists);
        notifyDataSetChanged();
    }

    public class Vh extends RecyclerView.ViewHolder {
        public TextView mDate, week;
        public ImageView mAtar1;
        public ImageView mAtar2;
        public LinearLayout ll;
        private CardView cardView;

        public Vh(View itemView) {
            super(itemView);
            mDate = itemView.findViewById(R.id.date);
            week = itemView.findViewById(R.id.week);
            mAtar1 = itemView.findViewById(R.id.atar1);
            mAtar2 = itemView.findViewById(R.id.atar2);
            ll = itemView.findViewById(R.id.ll);
            cardView = itemView.findViewById(R.id.cvBg);
        }
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }


}
