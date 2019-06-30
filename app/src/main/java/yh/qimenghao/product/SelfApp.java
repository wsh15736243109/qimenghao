package yh.qimenghao.product;

import android.app.Application;
import android.content.Context;

import com.inuker.bluetooth.library.BluetoothClient;
import com.inuker.bluetooth.library.search.SearchResult;

/**
 * Created by Joy_Zhang on 2018/11/27.15:34

 佛祖保佑         永无BUG
 */
public class SelfApp extends Application {
    private static SelfApp selfApp;
    private BluetoothClient bluetoothClient;
    private boolean linked=false;
    private SearchResult linkedDevice=null;
    public SearchResult getLinkedDevice() {
        return linkedDevice;
    }

    public boolean initiativeDisconnect=false;

    public boolean isInitiativeDisconnect() {
        return initiativeDisconnect;
    }

    public void setInitiativeDisconnect(boolean initiativeDisconnect) {
        this.initiativeDisconnect = initiativeDisconnect;
    }

    public void setLinkedDevice(SearchResult linkedDevice) {
        this.linkedDevice = linkedDevice;
    }

    public boolean isLinked() {
        return linked;
    }

    public void setLinked(boolean linked) {
        this.linked = linked;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        this.selfApp =this;
    }
    public static SelfApp getApp(){
        return selfApp;
    }
    public Context getAppContext() {
        return getApplicationContext();
    }
    public BluetoothClient getBluetoothClient(){
        if(bluetoothClient==null){
            bluetoothClient=new BluetoothClient(this);
        }
        return bluetoothClient;
    }
}
