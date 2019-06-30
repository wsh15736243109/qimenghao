package yh.qimenghao.product.view.ui.activity.toothbrush_activitys;

import android.graphics.Color;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.inuker.bluetooth.library.search.SearchResult;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import yh.qimenghao.product.R;
import yh.qimenghao.product.SelfApp;
import yh.qimenghao.product.model.BleContent;
import yh.qimenghao.product.model.ProjectConstant;
import yh.qimenghao.product.model.bean.BaseBean;
import yh.qimenghao.product.model.bean.ToothbrushUpBean;
import yh.qimenghao.product.model.bean.ToothbrushUpReturnBean;
import yh.qimenghao.product.model.bean.UserInfoBean;
import yh.qimenghao.product.model.net.BaseObserver;
import yh.qimenghao.product.model.net.HttpMethods;
import yh.qimenghao.product.presenter.PersenterBleI;
import yh.qimenghao.product.presenter.PersenterBleImpl;
import yh.qimenghao.product.util.BufChangeHex;
import yh.qimenghao.product.util.MoreUtils;
import yh.qimenghao.product.util.ShowUtil;
import yh.qimenghao.product.util.YaoSharedPreferences;
import yh.qimenghao.product.view.adapter.DeviceListAdapter;
import yh.qimenghao.product.view.myview.YaoDialog;
import yh.qimenghao.product.view.ui.activity.BaseActivity;
import yh.qimenghao.product.view.ui.fragment.toothbrush_fragments.MyFragment;
import yh.qimenghao.product.view.ui.fragment.toothbrush_fragments.ToothbrushFragment;
import yh.qimenghao.product.view.view_interface.ToothbrushMainI;

import static yh.qimenghao.product.util.ShowUtil.d;

public class ToothbrushActivity extends BaseActivity implements ToothbrushMainI {
    PersenterBleI persenterBle;
    YaoDialog yaoDialog, nickNameDialog,upDateDialog;
    DeviceListAdapter deviceListAdapter;
    @BindView(R.id.frame_area)
    FrameLayout frameArea;
    @BindView(R.id.rbToothbrush)
    RadioButton rbToothbrush;
    @BindView(R.id.rbMyself)
    RadioButton rbMyself;
    private int currentNum=0;
    private int totalNum=0;
    private List<ToothbrushUpBean.ArgToothbrushData> argToothbrushDataList=new ArrayList<>();
    private TextView info;

    public void setOnLinkSuccess(OnLinkSuccess onLinkSuccess) {
        this.onLinkSuccess = onLinkSuccess;
    }

    private OnLinkSuccess onLinkSuccess;
    private SearchResult linkedDevice;

    private ToothbrushFragment toothbrushFragment;
    private MyFragment myFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_toothbrush;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        persenterBle = new PersenterBleImpl(this);
        toothbrushFragment = new ToothbrushFragment();
        selectFragment(toothbrushFragment);
        if(!YaoSharedPreferences.getString(BleContent.RELINKMAC, "null").equals("null")){
            persenterBle.searchDevice(true);
        }
    }

    @Override
    protected void onResume() {
        persenterBle = new PersenterBleImpl(this);
        //selectFragment(toothbrushFragment);
        super.onResume();
    }

    @Override
    public void linkSuccess(SearchResult device) {
        this.linkedDevice = device;
        if (onLinkSuccess != null) onLinkSuccess.linkSucced(device,this.nickName);
        YaoSharedPreferences.putString(BleContent.RELINKMAC, device.getAddress());
        d(device.getAddress());
        if(yaoDialog!=null)yaoDialog.cancel();
        ShowUtil.shortToast(getResources().getString(R.string.link_s));
        byte[] time = MoreUtils.long2byte(System.currentTimeMillis() / 1000);
       // byte[] time = MoreUtils.long2byte(1552556032L);
        persenterBle.sendData(new byte[]{(byte) 0xff, 0x05, 0x00, 0x01, 0x00, 0x01, time[4], time[5], time[6], time[7]});
        //ShowUtil.d(MoreUtils.timeStamp2Date(MoreUtils.bytes2long(time[4], time[5], time[6], time[7])+"",null));
        String userToken = ((UserInfoBean) YaoSharedPreferences.getObject(ProjectConstant.USERINFO)).getUserToken();
        List<UserInfoBean.DeviceListBean> deviceList = YaoSharedPreferences.getList(userToken);
        if (deviceList != null) {
            if (!checkExist(device, deviceList)) {
                showSetNickName(device);
            }
        } else {
            showSetNickName(device);
        }
    }

    @Override
    public void disConnected(SearchResult device) {
        if (onLinkSuccess != null) onLinkSuccess.disConnected();
        SelfApp.getApp().setLinkedDevice(null);
        ShowUtil.shortToast(getResources().getString(R.string.link_d));
        d("断开连接！");
    }

    @Override
    public void linkFailed() {
        if(yaoDialog!=null)yaoDialog.cancel();
        persenterBle.disConnect(linkedDevice);
        ShowUtil.shortToast(getResources().getString(R.string.link_f));
    }
    /*ToothbrushUpBean toothbrushUpBean=new ToothbrushUpBean();
            toothbrushUpBean.setUserToken(((UserInfoBean)YaoSharedPreferences.getObject(ProjectConstant.USERINFO)).getUserToken());
            List<ToothbrushUpBean.ArgToothbrushData> argToothbrushDataList=new ArrayList<>();
            for (int i = 0; i < 60; i++) {
                ToothbrushUpBean.ArgToothbrushData argToothbrushData=new ToothbrushUpBean.ArgToothbrushData("AA:BB:CC:DD:EE:FF",(System.currentTimeMillis()/1000)-i*86400);
                argToothbrushDataList.add(argToothbrushData);
            }
            toothbrushUpBean.setToothbrushDatas(argToothbrushDataList);
            ShowUtil.d("添加内容"+new Gson().toJson(toothbrushUpBean));
            RequestBody requestBody=RequestBody.create(MediaType.parse("application/json; charset=utf-8"),new Gson().toJson(toothbrushUpBean));
            HttpMethods.getInstance().upToothbrushDate(requestBody, this, new BaseObserver<BaseBean<ToothbrushUpReturnBean>>() {
                @Override
                public void onResult(int code, BaseBean<ToothbrushUpReturnBean> toothbrushUpReturnBeanBaseBean, Throwable e) {
                    if(code==0){
                        ShowUtil.d("收到内容："+toothbrushUpReturnBeanBaseBean.toString());
                        YaoSharedPreferences.putInt(ProjectConstant.TOTCOUNT,toothbrushUpReturnBeanBaseBean.getData().getTotalcount());
                        toothbrushNum.setText(toothbrushUpReturnBeanBaseBean.getData().getTotalcount()+"");
                    }else {
                        ShowUtil.d("错误："+e.toString());
                    }
                }
            });*/
    @Override
    public void receiveData(byte[] buf) {
        d(BufChangeHex.encodeHexStr(buf));
        if(buf[1]==0x05&&buf[6]==0x00){
            argToothbrushDataList.clear();
            totalNum=0;currentNum=0;
            persenterBle.sendData(new byte[]{(byte) 0xff, 0x01, 0x00, 0x00, 0x00, (byte)(currentNum+1)});
        }else if(buf[1]==0x01){
            if((buf[2]&0xff)==0xff&&(buf[3]&0xff)==0xff&&(buf[4]&0xff)==0xff&&(buf[5]&0xff)==0xff){
                d("没有新数据");
            }else {
                if(upDateDialog==null){showUpdateDialog();}else
                if(!upDateDialog.isShowing()){showUpdateDialog();}
                totalNum=buf[3];
                currentNum=buf[5];
                d(MoreUtils.byteArrayToLong(new byte[]{buf[6],buf[7],buf[8],buf[9]})+"");
                ToothbrushUpBean.ArgToothbrushData argToothbrushData=new ToothbrushUpBean.ArgToothbrushData(this.linkedDevice.getAddress(),MoreUtils.byteArrayToLong(new byte[]{buf[6],buf[7],buf[8],buf[9]}));
                argToothbrushDataList.add(argToothbrushData);
                if(currentNum<totalNum){
                    persenterBle.sendData(new byte[]{(byte) 0xff, 0x01, 0x00, 0x00, 0x00, (byte)(currentNum+1)});
                }else {
                    d("device->APP 更新完成！");
                    upToothbrushDate();
                    info.setText(getResources().getText(R.string.update_to_server));
                }
            }
        }else if(buf[1]==0x04){
            if(buf[6]==0x00){
                d("设备清除数据完成！");
                ShowUtil.d(getResources().getString(R.string.clear_complete));
            }else {
                d("设备清除数据失败！");
            }
        }

    }
    private void upToothbrushDate(){
        ToothbrushUpBean toothbrushUpBean=new ToothbrushUpBean();
        toothbrushUpBean.setUserToken(((UserInfoBean)YaoSharedPreferences.getObject(ProjectConstant.USERINFO)).getUserToken());
        toothbrushUpBean.setToothbrushDatas(argToothbrushDataList);
        ShowUtil.d("添加内容"+new Gson().toJson(toothbrushUpBean));
        RequestBody requestBody=RequestBody.create(MediaType.parse("application/json; charset=utf-8"),new Gson().toJson(toothbrushUpBean));
        HttpMethods.getInstance().upToothbrushDate(requestBody, this, new BaseObserver<BaseBean<ToothbrushUpReturnBean>>() {
            @Override
            public void onResult(int code, BaseBean<ToothbrushUpReturnBean> toothbrushUpReturnBeanBaseBean, Throwable e) {
                if(code==0){
                    ShowUtil.d("收到内容："+toothbrushUpReturnBeanBaseBean.toString());
                    YaoSharedPreferences.putInt(ProjectConstant.TOOTHBRUSHTOTCOUNT,toothbrushUpReturnBeanBaseBean.getData().getTotalcount());
                    onLinkSuccess.totalCount(toothbrushUpReturnBeanBaseBean.getData().getTotalcount());
                    persenterBle.sendData(new byte[]{(byte) 0xff, 0x04, 0x00, 0x01, 0x00, 0x01});//清空数据命令
                    info.setText(R.string.clear_complete);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(upDateDialog!=null)upDateDialog.cancel();
                        }
                    },1000);
                }else {
                    ShowUtil.d("错误："+e.toString());
                    if(upDateDialog!=null)upDateDialog.cancel();
                }
            }
        });
    }

    @Override
    public void autoLinkFail() {

    }

    @Override
    public void autoLinkSuccess() {

    }

    @Override
    public void searchDeviceResult(SearchResult device) {
        if (device != null) {
            d(device.getName()+device.device.getAddress());
           if(deviceListAdapter!=null) deviceListAdapter.addDevice(device);
        }
    }

    @OnCheckedChanged({R.id.rbToothbrush, R.id.rbMyself})
    public void onRadioCheck(CompoundButton view, boolean ischanged) {
        switch (view.getId()) {
            case R.id.rbToothbrush:
                if (ischanged) {
                    selectFragment(toothbrushFragment);
                }
                break;
            case R.id.rbMyself:
                if (ischanged) {
                    if (myFragment == null) {
                        myFragment = new MyFragment();
                    }
                    selectFragment(myFragment);
                }
                break;
            default:
                break;
        }
    }

    private boolean checkExist(SearchResult device, List<UserInfoBean.DeviceListBean> deviceList) {
        for (UserInfoBean.DeviceListBean deviceListBean : deviceList) {
            if (device.getAddress().equals(deviceListBean.getDeviceId())) {
                return true;
            }
        }
        return false;
    }
    private String nickName;
    public void showDeviceDialog() {
        yaoDialog = new YaoDialog(this, R.layout.device_layout, 0f,true, view -> {
            RecyclerView recyclerView = view.findViewById(R.id.deviceList);
            ProgressBar progressBar = view.findViewById(R.id.waitPb);
            TextView textView=view.findViewById(R.id.waitTv);
            recyclerView.setLayoutManager(new LinearLayoutManager(SelfApp.getApp().getAppContext()));
            deviceListAdapter = new DeviceListAdapter(SelfApp.getApp().getAppContext(), (device,nickName) -> {
                this.nickName=nickName;
                persenterBle.linkBle(device);
                recyclerView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                textView.setText(getResources().getString(R.string.wait));
            });
            recyclerView.setAdapter(deviceListAdapter);
        });
        yaoDialog.show();
        persenterBle.searchDevice(false);
    }
    private void showUpdateDialog(){
        upDateDialog=new YaoDialog(this,R.layout.holding_layout,0.3f,false,view -> {
            info=view.findViewById(R.id.updateInfoTv);
        });
        upDateDialog.setGravity(Gravity.BOTTOM);
        upDateDialog.show();
    }

    private void showSetNickName(SearchResult device) {
        nickNameDialog = new YaoDialog(this, R.layout.set_name_layout, false, false, view -> {
            EditText nickName = view.findViewById(R.id.inputNickName);
            nickName.setText(device.getName());
            Button ok = view.findViewById(R.id.ok);
            ok.setOnClickListener(v -> {
                ok.setEnabled(false);
                HttpMethods.getInstance().bindDevice(((UserInfoBean) YaoSharedPreferences.getObject(ProjectConstant.USERINFO)).getUserToken(),
                        device.getAddress(), nickName.getText().toString(), "2", this,
                        new BaseObserver<BaseBean<List<UserInfoBean.DeviceListBean>>>() {
                            @Override
                            public void onResult(int code, BaseBean<List<UserInfoBean.DeviceListBean>> listBaseBean, Throwable e) {
                                ok.setEnabled(true);
                                if (listBaseBean.getCode() == 0) {
                                    if (onLinkSuccess != null) onLinkSuccess.linkSucced(device,nickName.getText().toString());
                                    nickNameDialog.cancel();
                                    d(listBaseBean.toString());
                                    YaoSharedPreferences.putList(((UserInfoBean) YaoSharedPreferences.getObject(ProjectConstant.USERINFO)).getUserToken(), listBaseBean.getData());
                                    ShowUtil.shortToast(getResources().getString(R.string.set_success));
                                    onLinkSuccess.upNikeName(nickName.getText().toString());
                                }
                            }
                        });
            });
        });
        nickNameDialog.show();
    }


    public void disConnect() {
        d("断开");
        persenterBle.disConnect(linkedDevice);
    }

    public interface OnLinkSuccess {
        void linkSucced(SearchResult device,String nickName);
        void totalCount(int count);
        void upNikeName(String name);
        void disConnected();
    }

    @Override
    protected void onDestroy() {
        d("退出");
        if(linkedDevice!=null)disConnect();
        super.onDestroy();
    }
}
