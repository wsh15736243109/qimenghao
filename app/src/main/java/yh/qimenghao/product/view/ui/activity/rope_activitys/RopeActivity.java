package yh.qimenghao.product.view.ui.activity.rope_activitys;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import yh.qimenghao.product.R;
import yh.qimenghao.product.model.bean.BaseBean;
import yh.qimenghao.product.model.bean.RopeDataBean;
import yh.qimenghao.product.model.bean.UpRopeDataBean;
import yh.qimenghao.product.model.net.BaseObserver;
import yh.qimenghao.product.model.net.HttpMethods;
import yh.qimenghao.product.util.ShowUtil;
import yh.qimenghao.product.view.ui.activity.BaseActivity;
import yh.qimenghao.product.view.ui.fragment.rope_fragments.CommunityFragment;
import yh.qimenghao.product.view.ui.fragment.rope_fragments.FindFragment;
import yh.qimenghao.product.view.ui.fragment.rope_fragments.MyselfFragment;
import yh.qimenghao.product.view.ui.fragment.rope_fragments.RopeFragment;

public class RopeActivity extends BaseActivity {
    @BindView(R.id.frame_area)
    FrameLayout frameArea;
    @BindView(R.id.rbRope)
    RadioButton rbRope;
    @BindView(R.id.rbFind)
    RadioButton rbFind;
    @BindView(R.id.rbCommunity)
    RadioButton rbCommunity;
    @BindView(R.id.rbMyself)
    RadioButton rbMyself;
    private RopeFragment ropeFragment;
    private FindFragment findFragment;
    private CommunityFragment communityFragment;
    private MyselfFragment myselfFragment;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_rope;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ropeFragment = new RopeFragment();
        selectFragment(ropeFragment);

        UpRopeDataBean upRopeDataBean = new UpRopeDataBean();
        upRopeDataBean.setUserToken("5298e1d6566471d13f83873ad9cf2c59");
        List<UpRopeDataBean.RopeDataBean> ropeDataBeanList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            UpRopeDataBean.RopeDataBean ropeDataBean = new UpRopeDataBean.RopeDataBean();
            ropeDataBean.setUserId(28);
            ropeDataBean.setDeviceId("11:22:33:44:55:66");
            ropeDataBean.setRopeCount(300);
            ropeDataBean.setRopeDuration(60);
            ropeDataBean.setRopeType(i>2?"0":String.valueOf(i));
            ropeDataBean.setTimestamp(System.currentTimeMillis() / 1000);
            ropeDataBeanList.add(ropeDataBean);
        }
        upRopeDataBean.setRopeData(ropeDataBeanList);
        Gson gson = new Gson();
        String obj = gson.toJson(upRopeDataBean);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), obj);
        HttpMethods.getInstance().update(body, RopeActivity.this, new BaseObserver<BaseBean<RopeDataBean>>() {
            @Override
            public void onResult(int code, BaseBean<RopeDataBean> ropeDataBeanBaseBean, Throwable e) {
                if (code == 0) {
                    if (ropeDataBeanBaseBean.getCode() == 1) {
                        ShowUtil.d(ropeDataBeanBaseBean.toString());
                    }
                } else {
                    ShowUtil.d(e.toString());
                }
            }
        });
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @OnCheckedChanged({R.id.rbRope, R.id.rbFind, R.id.rbCommunity, R.id.rbMyself})
    public void onRadioCheck(CompoundButton view, boolean ischanged) {
        switch (view.getId()) {
            case R.id.rbRope:
                if (ischanged) {
                    selectFragment(ropeFragment);
                }
                break;
            case R.id.rbFind:
                if (ischanged) {
                    if (findFragment == null) {
                        findFragment = new FindFragment();
                    }
                    selectFragment(findFragment);
                }
                break;
            case R.id.rbCommunity:
                if (ischanged) {
                    if (communityFragment == null) {
                        communityFragment = new CommunityFragment();
                    }
                    selectFragment(communityFragment);
                }
                break;
            case R.id.rbMyself:
                if (ischanged) {
                    if (myselfFragment == null) {
                        myselfFragment = new MyselfFragment();
                    }
                    selectFragment(myselfFragment);
                }
                break;
            default:
                break;
        }
    }

}
