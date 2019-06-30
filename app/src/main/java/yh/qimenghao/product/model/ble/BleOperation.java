package yh.qimenghao.product.model.ble;

import android.os.Handler;

import com.inuker.bluetooth.library.BluetoothClient;
import com.inuker.bluetooth.library.connect.listener.BleConnectStatusListener;
import com.inuker.bluetooth.library.connect.options.BleConnectOptions;
import com.inuker.bluetooth.library.connect.response.BleNotifyResponse;
import com.inuker.bluetooth.library.search.SearchRequest;
import com.inuker.bluetooth.library.search.SearchResult;
import com.inuker.bluetooth.library.search.response.SearchResponse;

import java.util.UUID;

import yh.qimenghao.product.SelfApp;
import yh.qimenghao.product.model.BleContent;
import yh.qimenghao.product.util.BufChangeHex;
import yh.qimenghao.product.util.ShowUtil;
import yh.qimenghao.product.util.YaoSharedPreferences;

import static com.inuker.bluetooth.library.Code.REQUEST_FAILED;
import static com.inuker.bluetooth.library.Code.REQUEST_SUCCESS;
import static com.inuker.bluetooth.library.Constants.STATUS_CONNECTED;
import static com.inuker.bluetooth.library.Constants.STATUS_DISCONNECTED;

/**
 * Created by Joy on 2018/12/21.18:24
 */
public class BleOperation {
    private SearchResult device;
    private BleI bleI;
    private BluetoothClient bluetoothClient;
    private static volatile BleOperation singleton = null;

    private BleOperation() {
        bluetoothClient = SelfApp.getApp().getBluetoothClient();
    }

    public static BleOperation getInstance() {
        if (singleton == null) {
            synchronized (BleOperation.class) {
                if (singleton == null) {
                    singleton = new BleOperation();
                }
            }
        }
        return singleton;
    }

    public void setBleI(BleI bleI) {
        this.bleI = bleI;
    }

    public void searchDevice(boolean reLink) {
        ShowUtil.d("开始扫描");
        bluetoothClient.stopSearch();
        SearchRequest request = new SearchRequest.Builder()
                .searchBluetoothLeDevice(120000, 2) // 先扫BLE设备1次，每次8s
                .build();
        bluetoothClient.search(request, new SearchResponse() {
            @Override
            public void onSearchStarted() {

            }

            @Override
            public void onDeviceFounded(SearchResult device) {
                if (reLink) {
                    if (YaoSharedPreferences.getString(BleContent.RELINKMAC, "null").equals(device.getAddress())) {
                        linkDevice(device);
                        return;
                    }
                }
                if (bleI != null) {
                    if (device.scanRecord[9] == 'Y' && device.scanRecord[10] == 'H') {
                        bleI.searchDeviceResult(device);
                    }
                    ShowUtil.d(BufChangeHex.encodeHexStr(device.scanRecord));
                }
            }

            @Override
            public void onSearchStopped() {
                if (YaoSharedPreferences.getBoolean(BleContent.AUTOLINK, false) && !SelfApp.getApp().isLinked()) {
                    if (bleI != null) {
                        bleI.autoLinkFail();
                    }
                }
            }

            @Override
            public void onSearchCanceled() {

            }
        });
    }

    public void stopSearch() {
        bluetoothClient.stopSearch();
    }

    public void linkDevice(final SearchResult device) {
        bluetoothClient.stopSearch();
        ShowUtil.d("测试", "正在连接: " + device.getName() + "  " + device.getAddress());
        BleConnectOptions options = new BleConnectOptions.Builder()
                .setConnectRetry(3)   // 连接如果失败重试3次
                .setConnectTimeout(5000)   // 连接超时4s
                .setServiceDiscoverRetry(3)  // 发现服务如果失败重试2次
                .setServiceDiscoverTimeout(5000)  // 发现服务超时5s
                .build();
        bluetoothClient.connect(device.getAddress(), options, (code, profile) -> {
            if (code == REQUEST_SUCCESS) {
                this.device = device;
                bluetoothClient.registerConnectStatusListener(device.getAddress(), mBleConnectStatusListener);
                ShowUtil.d("测试数据", "连接成功，正在开启通知！");
                new Handler().postDelayed(() -> notifyBuf(device), 150);
            } else if (code == REQUEST_FAILED) {
                if (bleI != null) bleI.linkFailed();
                ShowUtil.d("测试数据", "连接失败");

            }
        });
    }

    public void disConnect(SearchResult device) {
        bluetoothClient.disconnect(device.getAddress());
    }
    //收到通知

    private void notifyBuf(SearchResult device) {
        bluetoothClient.notify(device.getAddress(), UUID.fromString(BleContent.BleUUID.serviceUUID), UUID.fromString(BleContent.BleUUID.notifyUUID), new BleNotifyResponse() {
            @Override
            public void onNotify(UUID service, UUID character, byte[] receive1) {
                // if (bleDealResultI != null) bleDealResultI.receiveData(receive1);
                // ShowUtil.shortToast(BufChangeHex.encodeHexStr(receive1));
                if (receive1.length < 2) {
                    return;
                }
                bleI.receiveData(receive1);
            }

            @Override
            public void onResponse(int code) {
                if (code == REQUEST_SUCCESS) {
                    ShowUtil.d("测试数据", "开启通知");
                    SelfApp.getApp().setLinkedDevice(device);
                    SelfApp.getApp().setLinked(true);
                    SelfApp.getApp().setInitiativeDisconnect(false);
                    if (bleI != null) {
                        bleI.linkSuccess(device);
                        if (YaoSharedPreferences.getBoolean(BleContent.AUTOLINK, false))
                            bleI.autoLinkSuccess();
                    }
                } else if (code == REQUEST_FAILED) {
                    if (bleI != null) bleI.linkFailed();
                }
            }
        });
    } //写数据到固件

    public void writeBuf(final byte[] buf) {
        if (!SelfApp.getApp().isLinked()) return;
        bluetoothClient.write(device.getAddress(), UUID.fromString(BleContent.BleUUID.serviceUUID), UUID.fromString(BleContent.BleUUID.writeUUID), buf, code -> {
            if (code == REQUEST_SUCCESS) {
                ShowUtil.d("测试数据", "写入成功  " + BufChangeHex.encodeHexStr(buf));
            }
        });
    }

    private final BleConnectStatusListener mBleConnectStatusListener = new BleConnectStatusListener() {

        @Override
        public void onConnectStatusChanged(String mac, int status) {
            if (status == STATUS_CONNECTED) {
                ShowUtil.d("测试数据", "已经连接");
            } else if (status == STATUS_DISCONNECTED) {
                ShowUtil.d("测试数据", "断开连接");
                SelfApp.getApp().setLinkedDevice(null);
                if (mBleConnectStatusListener != null && device != null && bluetoothClient != null)
                    bluetoothClient.unregisterConnectStatusListener(device.getAddress(), mBleConnectStatusListener);
                if (bleI != null) bleI.disConnected(device);
                if(!SelfApp.getApp().isInitiativeDisconnect()){
                    searchDevice(true);
                }
            }
        }
    };

}
