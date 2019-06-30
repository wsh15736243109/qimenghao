package yh.qimenghao.product.view.ui.activity.rope_activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import yh.qimenghao.product.R;
import yh.qimenghao.product.model.ProjectConstant;
import yh.qimenghao.product.model.bean.BaseBean;
import yh.qimenghao.product.model.bean.RopeDataBean;
import yh.qimenghao.product.model.bean.UserInfoBean;
import yh.qimenghao.product.model.net.BaseObserver;
import yh.qimenghao.product.model.net.HttpMethods;
import yh.qimenghao.product.util.ShowUtil;
import yh.qimenghao.product.util.YaoSharedPreferences;
import yh.qimenghao.product.view.adapter.TodayDataAdapter;
import yh.qimenghao.product.view.myview.UnfoldListView;
import yh.qimenghao.product.view.ui.activity.BaseActivity;

public class TrainingRecordsActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;
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
    @BindView(R.id.pkField)
    TextView pkField;
    @BindView(R.id.pkWin)
    TextView pkWin;
    @BindView(R.id.pkLost)
    TextView pkLost;
    @BindView(R.id.pkDetail)
    ImageView pkDetail;
    @BindView(R.id.todayData)
    UnfoldListView todayDataLv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_training_records;
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
                if (ropeDataBeanBaseBean.getCode()==0) {
                    ShowUtil.d(ropeDataBeanBaseBean.toString());
                    totNum.setText(String.valueOf(ropeDataBeanBaseBean.getData().getTotalnum()));
                    totCount.setText(String.valueOf(ropeDataBeanBaseBean.getData().getTotalcount()));
                    totMin.setText(String.valueOf(ropeDataBeanBaseBean.getData().getTotaltimes()));
                    totCalorie.setText(
                            ropeDataBeanBaseBean.getData().getTotalcalorie()<1000?
                                    String.valueOf(ropeDataBeanBaseBean.getData().getTotalcalorie()):
                                    String.valueOf(ropeDataBeanBaseBean.getData().getTotalcalorie()/1000)+" K");
                    totDay.setText(String.valueOf(ropeDataBeanBaseBean.getData().getTotaldays()));
                    List<RopeDataBean.TodaydataBean> todayData = ropeDataBeanBaseBean.getData().getTodaydata();
                    if(todayData!=null){
                        todayDataLv.setAdapter(new TodayDataAdapter(todayData));
                    }
                }
            }
        });
    }

    @OnClick({R.id.back, R.id.pkDetail})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                break;
            case R.id.pkDetail:
                break;
        }
    }
}
