package yh.qimenghao.product.view.ui.activity;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Button;
import android.widget.TextView;

import yh.qimenghao.product.R;
import yh.qimenghao.product.SelfApp;
import yh.qimenghao.product.model.BleContent;
import yh.qimenghao.product.model.bean.BaseBean;
import yh.qimenghao.product.model.bean.UpAppBean;
import yh.qimenghao.product.model.net.BaseObserver;
import yh.qimenghao.product.model.net.HttpMethods;
import yh.qimenghao.product.util.ShowUtil;
import yh.qimenghao.product.view.myview.YaoDialog;

public class VirginActivity extends BaseActivity {
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothStateBroadcastReceive mReceive;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_virgin;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        registerBluetoothReceiver();
        checkBlePermission();
    }

    public class BluetoothStateBroadcastReceive extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case BluetoothAdapter.ACTION_STATE_CHANGED:
                    int blueState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0);
                    switch (blueState) {
                        case BluetoothAdapter.STATE_OFF:
                            ShowUtil.d("蓝牙关闭");
                            break;
                        case BluetoothAdapter.STATE_ON:
                            ShowUtil.d("蓝牙打开");
                            new Handler().postDelayed(() -> {
                                goTo();
                            }, 800);
                            break;
                    }
                    break;
            }
        }
    }

    /**
     * 检查蓝牙权限
     */
    public void checkBlePermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    BleContent.RERUESTCODE);
        } else {
            if (!bluetoothAdapter.isEnabled()) {
                bluetoothAdapter.enable();
            } else {
                new Handler().postDelayed(() -> {
                    goTo();
                }, 800);
            }
        }
    }

    private void registerBluetoothReceiver() {
        if (mReceive == null) {
            mReceive = new BluetoothStateBroadcastReceive();
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        intentFilter.addAction("android.bluetooth.BluetoothAdapter.STATE_OFF");
        intentFilter.addAction("android.bluetooth.BluetoothAdapter.STATE_ON");
        registerReceiver(mReceive, intentFilter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case BleContent.RERUESTCODE: {
                // 如果请求被取消，则结果数组为空。
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (!bluetoothAdapter.isEnabled()) {
                        bluetoothAdapter.enable();
                    } else {
                        new Handler().postDelayed(() -> {
                            goTo();
                        }, 500);
                    }
                } else {
                    checkBlePermission();
                }
                return;
            }
        }
    }

    private void goTo() {
        HttpMethods.getInstance().checkVersion(getLocalVersion(this), this, new BaseObserver<BaseBean>() {
            @Override
            public void onResult(int code, BaseBean baseBean, Throwable e) {
                if (code == 0) {
                    if (baseBean.getCode() == 0) {
                        if (((UpAppBean) baseBean.getData()).getResult() == 0) {
                            startActivity(new Intent(SelfApp.getApp().getAppContext(), LoginActivity.class));
                            finish();
                        } else {
                            //更新
                            showUpdateDialog(((UpAppBean) baseBean.getData()).getNewversion(),
                                    ((UpAppBean) baseBean.getData()).getUpdateinfo(),
                                    ((UpAppBean) baseBean.getData()).getUrl());
                        }
                    } else {
                        ShowUtil.shortToast(getResources().getString(R.string.server_error));
                    }
                } else {
                    ShowUtil.shortToast(getResources().getString(R.string.network_error));
                }
            }
        });

    }

    private void showUpdateDialog(String version,String info,String url) {
        YaoDialog yaoDialog = new YaoDialog(this, R.layout.update_app_layout, false, v -> {
            TextView mVersion;
            TextView mUpdateInfo;
            Button mOk;
            Button mCancel;
            mVersion = v.findViewById(R.id.version);
            mVersion.setText(getResources().getString(R.string.version)+version);
            mUpdateInfo = v.findViewById(R.id.updateInfo);
            mUpdateInfo.setText(getResources().getString(R.string.update_info)+info);
            mOk = v.findViewById(R.id.ok);
            mCancel = v.findViewById(R.id.cancel);
            mOk.setOnClickListener(view -> {
                Intent intent = new Intent();
                intent.setData(Uri.parse(url));
                intent.setAction(Intent.ACTION_VIEW);
                this.startActivity(intent); //启动浏览器
            });
            mCancel.setOnClickListener(view -> {
                startActivity(new Intent(SelfApp.getApp().getAppContext(), LoginActivity.class));
                finish();
            });
        });
        yaoDialog.show();
    }

    public static String getLocalVersion(Context ctx) {
        String localVersion = "";
        try {
            PackageInfo packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
            localVersion = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }

    @Override
    protected void onDestroy() {
        if (mReceive != null) {
            unregisterReceiver(mReceive);
            mReceive = null;
        }
        super.onDestroy();
    }
}
