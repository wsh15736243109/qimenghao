package yh.qimenghao.product.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import yh.qimenghao.product.R;
import yh.qimenghao.product.model.ProjectConstant;
import yh.qimenghao.product.model.bean.BaseBean;
import yh.qimenghao.product.model.bean.UserInfoBean;
import yh.qimenghao.product.model.net.BaseObserver;
import yh.qimenghao.product.model.net.HttpMethods;
import yh.qimenghao.product.util.ShowUtil;
import yh.qimenghao.product.util.YaoSharedPreferences;
import yh.qimenghao.product.view.myview.YaoDialog;
import yh.qimenghao.product.view.ui.activity.toothbrush_activitys.ToothbrushActivity;

/**
 * Created by Joy on 2019/1/9.11:03
 */
public class DeviceManagerListAdapter extends BaseAdapter {
    private Context context;
    private List<UserInfoBean.DeviceListBean> deviceListBeanList = new ArrayList<>();

    public void setDeviceListBeanList(List<UserInfoBean.DeviceListBean> deviceListBeanList) {
        this.deviceListBeanList = deviceListBeanList;
        notifyDataSetChanged();
    }

    private YaoDialog nickNameDialog, delDeviceDialog;

    public DeviceManagerListAdapter(Context context) {
        this.context = context;
    }

    public DeviceManagerListAdapter(Context context, List<UserInfoBean.DeviceListBean> deviceListBeanList) {
        this.context = context;
        this.deviceListBeanList = deviceListBeanList;
    }

    @Override
    public int getCount() {
        return deviceListBeanList.size();
    }

    @Override
    public UserInfoBean.DeviceListBean getItem(int position) {
        return deviceListBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserInfoBean.DeviceListBean deviceListBean = deviceListBeanList.get(position);
        View view = LayoutInflater.from(context).inflate(R.layout.device_manager_item_layout, parent, false);
        TextView mDeviceName;
        ImageButton mAlterDeviceName;
        Button mDelDevice;
        mDeviceName = view.findViewById(R.id.deviceName);
        mAlterDeviceName = view.findViewById(R.id.alterDeviceName);
        mDelDevice = view.findViewById(R.id.delDevice);
        mDeviceName.setText(deviceListBean.getDeviceNickname());
        mAlterDeviceName.setOnClickListener(v -> showSetNickName(deviceListBean));
        mDelDevice.setOnClickListener(v -> showDel(deviceListBean));
        return view;
    }

    private void showSetNickName(UserInfoBean.DeviceListBean deviceListBean) {
        nickNameDialog = new YaoDialog(context, R.layout.set_name_layout, true, true, view -> {
            EditText nickName = view.findViewById(R.id.inputNickName);
            nickName.setText(deviceListBean.getDeviceNickname());
            Button ok = view.findViewById(R.id.ok);
            ok.setOnClickListener(v -> {
                ok.setEnabled(false);
                HttpMethods.getInstance().updateDeviceNickname(((UserInfoBean) YaoSharedPreferences.getObject(ProjectConstant.USERINFO)).getUserToken(),
                        deviceListBean.getDeviceId(), nickName.getText().toString(), (ToothbrushActivity) context,
                        new BaseObserver<BaseBean<List<UserInfoBean.DeviceListBean>>>() {
                            @Override
                            public void onResult(int code, BaseBean<List<UserInfoBean.DeviceListBean>> listBaseBean, Throwable e) {
                                ok.setEnabled(true);
                                if (code == 1) {
                                    ShowUtil.shortToast(e.toString());
                                    return;
                                }
                                if (listBaseBean.getCode() == 0) {
                                    nickNameDialog.cancel();
                                    YaoSharedPreferences.putList(((UserInfoBean) YaoSharedPreferences.getObject(ProjectConstant.USERINFO)).getUserToken(), listBaseBean.getData());
                                    setDeviceListBeanList(listBaseBean.getData());
                                    ShowUtil.shortToast(context.getResources().getString(R.string.set_success));
                                } else {
                                    ShowUtil.shortToast(listBaseBean.getMsg());
                                }
                            }
                        });
            });
        });
        nickNameDialog.show();
    }

    private void showDel(UserInfoBean.DeviceListBean deviceListBean) {
        delDeviceDialog = new YaoDialog(context, R.layout.warning_layout, true, true, view -> {
            TextView cancel, ok;
            cancel = view.findViewById(R.id.cancel);
            ok = view.findViewById(R.id.ok);
            cancel.setOnClickListener(v -> delDeviceDialog.cancel());
            ok.setOnClickListener(v -> {
                ok.setEnabled(false);
                HttpMethods.getInstance().unBindDevice(((UserInfoBean) YaoSharedPreferences.getObject(ProjectConstant.USERINFO)).getUserToken(), deviceListBean.getDeviceId(),
                        (ToothbrushActivity) context, new BaseObserver<BaseBean<List<UserInfoBean.DeviceListBean>>>() {
                            @Override
                            public void onResult(int code, BaseBean<List<UserInfoBean.DeviceListBean>> listBaseBean, Throwable e) {
                                ok.setEnabled(true);
                                if (code == 1) {
                                    ShowUtil.shortToast(e.toString());
                                    return;
                                }
                                if (listBaseBean.getCode() == 0) {
                                    delDeviceDialog.cancel();
                                    YaoSharedPreferences.putList(((UserInfoBean) YaoSharedPreferences.getObject(ProjectConstant.USERINFO)).getUserToken(), listBaseBean.getData());
                                    setDeviceListBeanList(listBaseBean.getData());
                                    ShowUtil.shortToast(context.getResources().getString(R.string.del_success));
                                } else {
                                    ShowUtil.shortToast(listBaseBean.getMsg());
                                }
                            }
                        });
            });
        });
        delDeviceDialog.show();
    }
}
