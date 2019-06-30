package yh.qimenghao.product.view.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import ezy.ui.view.BannerView;
import yh.qimenghao.product.R;
import yh.qimenghao.product.view.ui.activity.rope_activitys.RopeActivity;

public class ScrollActivity extends BaseActivity {
    private BannerView bannerView;
    private static Context context;
    public static int[] urls = new int[]{
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scroll;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        bannerView = findViewById(R.id.bv);
        context = this;
        List<ScrollActivity.BannerItem> list = new ArrayList<>();
        for (int i = 0; i < urls.length; i++) {
            ScrollActivity.BannerItem item = new ScrollActivity.BannerItem();
            item.image = urls[i];
            list.add(item);
        }
        bannerView.setViewFactory(new ScrollActivity.BannerViewFactory());
        bannerView.setDataList(list);
        bannerView.start();
    }

    public static class BannerItem {
        public int image;

    }

    public class BannerViewFactory implements BannerView.ViewFactory<ScrollActivity.BannerItem> {
        @Override
        public View create(ScrollActivity.BannerItem item, int position, ViewGroup container) {
            View v = LayoutInflater.from(ScrollActivity.context).inflate(R.layout.scroll_item_layout, null, false);
            ImageView iv = v.findViewById(R.id.imageView);
            Button button = v.findViewById(R.id.button);
            button.setOnClickListener(view -> {
                ScrollActivity.context.startActivity(new Intent(ScrollActivity.context, LoginActivity.class));
                ScrollActivity.this.finish();
            });
            button.setVisibility(position == urls.length - 1 ? View.VISIBLE : View.GONE);
            iv.setImageResource(item.image);
            return v;
        }
    }
}
