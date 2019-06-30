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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import yh.qimenghao.product.R;
import yh.qimenghao.product.model.bean.ToothbrushBean;
import yh.qimenghao.product.util.MoreUtils;

/**
 * Created by Joy on 2019/1/4.15:30
 */
public class AwardAdapter extends RecyclerView.Adapter {
    private Context context;
    private OnSelectListener onSelectListener;
    private String[] awardList;
    private int[] bgList = {
            R.drawable.item_bg1, R.drawable.item_bg2, R.drawable.item_bg3, R.drawable.item_bg4,
            R.drawable.item_bg5, R.drawable.item_bg6,
    };
    private int[] numList;
    private int index = 0;

    public void setIndex(int index) {
        this.index = index;
        notifyDataSetChanged();
    }

    public AwardAdapter(Context context) {
        this.context = context;
        awardList = context.getResources().getStringArray(R.array.award_list);
        numList = new int[awardList.length];
        for (int i = 0; i < awardList.length; i++) {
            numList[i] = new Random().nextInt(bgList.length);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Vh(LayoutInflater.from(context).inflate(R.layout.award_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (index == position + 1) {
            ((Vh) holder).mark.setVisibility(View.VISIBLE);
        } else {
            ((Vh) holder).mark.setVisibility(View.GONE);
        }
        ((Vh) holder).info.setBackgroundResource(bgList[numList[position]]);
        ((Vh) holder).info.setText(awardList[position]);
        ((Vh) holder).info.setOnClickListener(v -> {
            if (onSelectListener != null) {
                if (((Vh) holder).mark.getVisibility() == View.GONE) {
                    onSelectListener.onSelect(position + 1);
                    index = position + 1;
                } else {
                    onSelectListener.onSelect(0);
                    index = 0;
                }
                notifyDataSetChanged();
                //notifyItemChanged(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return awardList.length;
    }

    public interface OnSelectListener {
        void onSelect(int index);
    }

    public class Vh extends RecyclerView.ViewHolder {
        TextView info;
        ImageView mark;

        public Vh(View itemView) {
            super(itemView);
            info = itemView.findViewById(R.id.awardTv);
            mark = itemView.findViewById(R.id.markIv);
        }
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }


}
