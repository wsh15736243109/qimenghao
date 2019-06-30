package yh.qimenghao.product.view.ui.fragment.toothbrush_fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inuker.bluetooth.library.search.SearchResult;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import yh.qimenghao.product.R;
import yh.qimenghao.product.SelfApp;
import yh.qimenghao.product.model.ProjectConstant;
import yh.qimenghao.product.model.bean.UserInfoBean;
import yh.qimenghao.product.util.YaoSharedPreferences;
import yh.qimenghao.product.view.adapter.DeviceManagerListAdapter;
import yh.qimenghao.product.view.ui.activity.LoginActivity;
import yh.qimenghao.product.view.ui.activity.SetUserInfoActivity;
import yh.qimenghao.product.view.ui.fragment.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends BaseFragment {
    @BindView(R.id.userName)
    TextView userName;
    @BindView(R.id.logout)
    TextView logout;
    @BindView(R.id.genderIcon)
    ImageView genderIcon;
    @BindView(R.id.currentDevice)
    TextView currentDevice;
    @BindView(R.id.myDevices)
    TextView myDevices;
    @BindView(R.id.aboutAs)
    TextView aboutAs;
    @BindView(R.id.deviceList)
    ListView deviceList;
    @BindView(R.id.noDeviceTv)
    TextView noDeviceTv;
    @BindView(R.id.myDeviceLL)
    LinearLayout myDeviceLL;
    @BindView(R.id.aboutAsLL)
    LinearLayout aboutAsLL;
    @BindView(R.id.avatar)
    ImageView avatar;
    @BindView(R.id.rlName)
    RelativeLayout rlName;
    Unbinder unbinder;
    private UserInfoBean userInfoBean;
    private List<UserInfoBean.DeviceListBean> deviceListBeanList;
    private DeviceManagerListAdapter deviceManagerListAdapter;

    public MyFragment() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        userInfoBean = YaoSharedPreferences.getObject(ProjectConstant.USERINFO);
        deviceListBeanList = YaoSharedPreferences.getList(userInfoBean.getUserToken());
        deviceManagerListAdapter = new DeviceManagerListAdapter(getContext(), deviceListBeanList);
        userName.setText(userInfoBean.getNickName());
        if (userInfoBean.getGender().equals("1")) {
            genderIcon.setImageResource(R.mipmap.boy);
        } else {
            genderIcon.setImageResource(R.mipmap.girl);
        }
        if (deviceListBeanList == null || deviceListBeanList.size() == 0) {
            noDeviceTv.setVisibility(View.VISIBLE);
            deviceList.setVisibility(View.GONE);
        } else {
            noDeviceTv.setVisibility(View.GONE);
            deviceList.setVisibility(View.VISIBLE);
        }
        if (SelfApp.getApp().getLinkedDevice() == null) {
            currentDevice.setText(getResources().getString(R.string.currentDevice) + getResources().getString(R.string.no_link));
        } else {
            currentDevice.setText(getResources().getString(R.string.currentDevice) + checkNickName(SelfApp.getApp().getLinkedDevice()));
        }
        deviceList.setAdapter(deviceManagerListAdapter);
    }

    @Override
    public void onResume() {
        userInfoBean = YaoSharedPreferences.getObject(ProjectConstant.USERINFO);
        userName.setText(userInfoBean.getNickName());
        if (userInfoBean.getGender().equals("1")) {
            genderIcon.setImageResource(R.mipmap.boy);
        } else {
            genderIcon.setImageResource(R.mipmap.girl);
        }
        if (deviceListBeanList == null || deviceListBeanList.size() == 0) {
            noDeviceTv.setVisibility(View.VISIBLE);
            deviceList.setVisibility(View.GONE);
        } else {
            noDeviceTv.setVisibility(View.GONE);
            deviceList.setVisibility(View.VISIBLE);
        }
        if (SelfApp.getApp().getLinkedDevice() == null) {
            currentDevice.setText(getResources().getString(R.string.currentDevice) + getResources().getString(R.string.no_link));
        } else {
            currentDevice.setText(getResources().getString(R.string.currentDevice) + checkNickName(SelfApp.getApp().getLinkedDevice()));
        }
        super.onResume();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            deviceListBeanList = YaoSharedPreferences.getList(userInfoBean.getUserToken());
            if (deviceListBeanList == null || deviceListBeanList.size() == 0) {
                noDeviceTv.setVisibility(View.VISIBLE);
                deviceList.setVisibility(View.GONE);
            } else {
                noDeviceTv.setVisibility(View.GONE);
                deviceList.setVisibility(View.VISIBLE);
            }
            if (SelfApp.getApp().getLinkedDevice() == null) {
                currentDevice.setText(getResources().getString(R.string.currentDevice) + getResources().getString(R.string.no_link));
            } else {
                currentDevice.setText(getResources().getString(R.string.currentDevice) + checkNickName(SelfApp.getApp().getLinkedDevice()));
            }
            deviceManagerListAdapter.setDeviceListBeanList(deviceListBeanList);
        }
        super.onHiddenChanged(hidden);
    }

    String checkNickName(SearchResult device) {
        for (UserInfoBean.DeviceListBean deviceListBean : deviceListBeanList) {
            if (deviceListBean.getDeviceId().equals(device.getAddress())) {
                return deviceListBean.getDeviceNickname();
            }
        }
        return device.getName();
    }

    @OnClick({R.id.myDevices, R.id.aboutAs, R.id.logout, R.id.rlName, R.id.avatar})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.myDevices:
                myDeviceLL.setVisibility(View.VISIBLE);
                aboutAsLL.setVisibility(View.GONE);
                myDevices.setTextColor(getResources().getColor(R.color.color_my_devices));
                aboutAs.setTextColor(getResources().getColor(R.color.color4));
                break;
            case R.id.aboutAs:
                myDeviceLL.setVisibility(View.GONE);
                aboutAsLL.setVisibility(View.VISIBLE);
                myDevices.setTextColor(getResources().getColor(R.color.color4));
                aboutAs.setTextColor(getResources().getColor(R.color.color_about_as));
                break;
            case R.id.logout:
                YaoSharedPreferences.putObject(ProjectConstant.USERINFO, new UserInfoBean("null"));
                getActivity().startActivity(new Intent(getContext(), LoginActivity.class));
                getActivity().finish();

                break;
            case R.id.rlName:
            case R.id.avatar:
                Intent intent = new Intent(SelfApp.getApp().getAppContext(), SetUserInfoActivity.class);
                intent.putExtra("SETINFO",1);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
