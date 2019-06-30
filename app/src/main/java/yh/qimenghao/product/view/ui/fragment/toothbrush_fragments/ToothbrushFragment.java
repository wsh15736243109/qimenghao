package yh.qimenghao.product.view.ui.fragment.toothbrush_fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.aigestudio.wheelpicker.WheelPicker;
import com.inuker.bluetooth.library.search.SearchResult;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import yh.qimenghao.product.R;
import yh.qimenghao.product.SelfApp;
import yh.qimenghao.product.model.ProjectConstant;
import yh.qimenghao.product.model.bean.BaseBean;
import yh.qimenghao.product.model.bean.ToothbrushUpReturnBean;
import yh.qimenghao.product.model.bean.UserInfoBean;
import yh.qimenghao.product.model.net.BaseObserver;
import yh.qimenghao.product.model.net.HttpMethods;
import yh.qimenghao.product.util.ShowUtil;
import yh.qimenghao.product.util.YaoSharedPreferences;
import yh.qimenghao.product.view.myview.YaoDialog;
import yh.qimenghao.product.view.ui.activity.toothbrush_activitys.AlarmActivity;
import yh.qimenghao.product.view.ui.activity.toothbrush_activitys.BrushHistoryActivity;
import yh.qimenghao.product.view.ui.activity.toothbrush_activitys.ToothbrushActivity;
import yh.qimenghao.product.view.ui.fragment.BaseFragment;

import static yh.qimenghao.product.util.ShowUtil.d;

/**
 * A simple {@link Fragment} subclass.
 */
public class ToothbrushFragment extends BaseFragment implements ToothbrushActivity.OnLinkSuccess {

    @BindView(R.id.toothbrushNum)
    TextView toothbrushNum;
    @BindView(R.id.sign_in)
    TextView signIn;
    @BindView(R.id.alarmSet)
    TextView alarmSet;
    @BindView(R.id.brushTime)
    TextView brushTime;
    @BindView(R.id.noLinkWarn)
    TextView noLinkWarn;
    private List secondData;
    ToothbrushActivity activity;

    public ToothbrushFragment() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_toothbrush;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        activity = (ToothbrushActivity) getActivity();
        activity.setOnLinkSuccess(this);
        toothbrushNum.setText(YaoSharedPreferences.getInt(ProjectConstant.TOOTHBRUSHTOTCOUNT, 0) + "");
        secondData = new ArrayList();
        for (int i = 0; i < 59; i++) {
            secondData.add(i);
        }
        UserInfoBean userInfo = YaoSharedPreferences.getObject(ProjectConstant.USERINFO);
        if (userInfo == null) return;

        HttpMethods.getInstance().getBrushTotalcount(userInfo.getUserToken(), this, new BaseObserver<BaseBean<ToothbrushUpReturnBean>>() {
            @Override
            public void onResult(int code, BaseBean<ToothbrushUpReturnBean> toothbrushUpReturnBeanBaseBean, Throwable e) {
                if (code == 1) {
                    d(e.toString());
                    return;
                }
                if (toothbrushUpReturnBeanBaseBean.getCode() == 0) {
                    toothbrushNum.setText(toothbrushUpReturnBeanBaseBean.getData().getTotalcount() + "");
                    YaoSharedPreferences.putInt(ProjectConstant.TOOTHBRUSHTOTCOUNT, toothbrushUpReturnBeanBaseBean.getData().getTotalcount());
                }
            }
        });
    }

    @Override
    public void onResume() {
        activity = (ToothbrushActivity) getActivity();
        activity.setOnLinkSuccess(this);
        if (SelfApp.getApp().getLinkedDevice() == null) {
            noLinkWarn.setText(getResources().getString(R.string.no_link));
        } else {
            noLinkWarn.setText(checkNickName(SelfApp.getApp().getLinkedDevice())+ getResources().getString(R.string.check_disconnect));
        }
        super.onResume();
    }

    YaoDialog yaoDialog;

    @OnClick({R.id.brushTime, R.id.noLinkWarn, R.id.sign_in, R.id.alarmSet})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.brushTime:
                yaoDialog = new YaoDialog(getActivity(), R.layout.time_picker_layout, true, view1 -> {
                    WheelPicker mMinPicker;
                    WheelPicker mSecondPicker;
                    Button commit;
                    mMinPicker = view1.findViewById(R.id.minPicker);
                    mSecondPicker = view1.findViewById(R.id.secondPicker);
                    commit = view1.findViewById(R.id.commit);
                    mSecondPicker.setData(secondData);
                    mMinPicker.setSelectedItemPosition(2);
                    commit.setOnClickListener(v -> {
                        int time = mMinPicker.getCurrentItemPosition() * 60 + mSecondPicker.getCurrentItemPosition();
                        setBrushTime(time);
                        yaoDialog.cancel();
                    });

                });
                yaoDialog.show();
                break;
            case R.id.noLinkWarn:
                ToothbrushActivity activity = (ToothbrushActivity) getActivity();
                if (noLinkWarn.getText().toString().equals(getResources().getString(R.string.no_link))) {
                    activity.showDeviceDialog();
                } else {
                    activity.disConnect();
                    SelfApp.getApp().setInitiativeDisconnect(true);
                }
                break;
            case R.id.sign_in:
                startActivity(new Intent(SelfApp.getApp().getAppContext(), BrushHistoryActivity.class));
                break;
            case R.id.alarmSet:
                startActivity(new Intent(SelfApp.getApp().getAppContext(), AlarmActivity.class));
                break;

        }
    }

    private void setBrushTime(int time) {
        ShowUtil.d(time + "");
    }

    @Override
    public void linkSucced(SearchResult device, String nickName) {
        if (isAdded())
            noLinkWarn.setText(checkNickName(device) + getResources().getString(R.string.check_disconnect));
        //noLinkWarn.setText(device.getName() + getResources().getString(R.string.check_disconnect));
    }

    @Override
    public void totalCount(int count) {
        if (isAdded()) toothbrushNum.setText(count + "");
    }

    @Override
    public void upNikeName(String name) {
        if (isAdded())
            noLinkWarn.setText(name + getResources().getString(R.string.check_disconnect));
    }

    @Override
    public void disConnected() {
        if (isAdded()) noLinkWarn.setText(getResources().getString(R.string.no_link));
    }

    private String checkNickName(SearchResult device) {
        String userToken = ((UserInfoBean) YaoSharedPreferences.getObject(ProjectConstant.USERINFO)).getUserToken();
        List<UserInfoBean.DeviceListBean> localDeviceList = YaoSharedPreferences.getList(userToken);
        for (UserInfoBean.DeviceListBean deviceListBean : localDeviceList) {
            if (deviceListBean.getDeviceId().equals(device.getAddress())) {
                return deviceListBean.getDeviceNickname();
            }
        }
        return "null";
    }
}
