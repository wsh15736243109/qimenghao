package yh.qimenghao.product.view.ui.fragment.rope_fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import yh.qimenghao.product.R;
import yh.qimenghao.product.SelfApp;
import yh.qimenghao.product.model.ProjectConstant;
import yh.qimenghao.product.model.bean.BaseBean;
import yh.qimenghao.product.model.bean.RopeDataBean;
import yh.qimenghao.product.model.bean.UserInfoBean;
import yh.qimenghao.product.model.net.BaseObserver;
import yh.qimenghao.product.model.net.HttpMethods;
import yh.qimenghao.product.util.ShowUtil;
import yh.qimenghao.product.util.YaoSharedPreferences;
import yh.qimenghao.product.view.ui.activity.rope_activitys.TrainingRecordsActivity;
import yh.qimenghao.product.view.ui.fragment.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class RopeFragment extends BaseFragment {

    @BindView(R.id.myDrill)
    TextView myDrill;
    @BindView(R.id.tot_num)
    TextView totNum;
    @BindView(R.id.tot_count)
    TextView totCount;
    @BindView(R.id.tot_min)
    TextView totMin;
    @BindView(R.id.tot_calorie)
    TextView totCalorie;
    @BindView(R.id.tot_day)
    TextView totDay;
    @BindView(R.id.noLinkWarn)
    TextView noLinkWarn;
    @BindView(R.id.startSport)
    Button startSport;
    @BindView(R.id.learnClass)
    TextView learnClass;
    @BindView(R.id.myDrillIg)
    ImageView myDrillIg;
    Unbinder unbinder;

    public RopeFragment() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_rope;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        UserInfoBean userInfoBean = YaoSharedPreferences.getObject(ProjectConstant.USERINFO);
        HttpMethods.getInstance().getData(userInfoBean.getUserToken(), null, this, new BaseObserver<BaseBean<RopeDataBean>>() {
            @Override
            public void onResult(int code, BaseBean<RopeDataBean> ropeDataBeanBaseBean, Throwable e) {
                if (code == 1) {
                    ShowUtil.shortToast(e.toString());
                    return;
                }
                if (ropeDataBeanBaseBean.getCode() == 0) {
                    ShowUtil.d(ropeDataBeanBaseBean.toString());
                    totNum.setText(String.valueOf(ropeDataBeanBaseBean.getData().getTotalnum()));
                    totCount.setText(String.valueOf(ropeDataBeanBaseBean.getData().getTotalcount()));
                    totMin.setText(String.valueOf(ropeDataBeanBaseBean.getData().getTotaltimes()));
                    totCalorie.setText(
                            ropeDataBeanBaseBean.getData().getTotalcalorie() < 1000 ?
                                    String.valueOf(ropeDataBeanBaseBean.getData().getTotalcalorie()) :
                                    String.valueOf(ropeDataBeanBaseBean.getData().getTotalcalorie() / 1000) + " K");
                    totDay.setText(String.valueOf(ropeDataBeanBaseBean.getData().getTotaldays()));
                }
            }
        });

    }

    @OnClick({R.id.myDrill,R.id.myDrillIg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.myDrill:
            case R.id.myDrillIg:
                    startActivity(new Intent(SelfApp.getApp().getAppContext(), TrainingRecordsActivity.class));
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
