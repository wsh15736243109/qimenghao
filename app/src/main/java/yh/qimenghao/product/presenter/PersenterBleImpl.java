package yh.qimenghao.product.presenter;

import com.inuker.bluetooth.library.search.SearchResult;

import yh.qimenghao.product.model.ble.BleI;
import yh.qimenghao.product.model.ble.BleOperation;
import yh.qimenghao.product.view.view_interface.ToothbrushMainI;

/**
 * Created by Joy on 2018/12/21.17:33
 */
public class PersenterBleImpl implements PersenterBleI, BleI {
    private BleOperation bleOperation;
    private  ToothbrushMainI toothbrushMainI;

    public PersenterBleImpl(ToothbrushMainI toothbrushMainI) {
       this.toothbrushMainI = toothbrushMainI;
        bleOperation = BleOperation.getInstance();
        bleOperation.setBleI(this);
    }

    @Override
    public void linkBle(SearchResult device) {
        bleOperation.linkDevice(device);
    }

    @Override
    public void disConnect(SearchResult device) {
        bleOperation.disConnect(device);
    }

    @Override
    public void sendData(byte[] buf) {
        bleOperation.writeBuf(buf);
    }

    @Override
    public void searchDevice(boolean reLink) {
        bleOperation.searchDevice(reLink);
    }

    @Override
    public void stopSearch() {
        bleOperation.stopSearch();
    }

    /**
     * 数据反馈 device to app
     */
    @Override
    public void linkSuccess(SearchResult device) {
        toothbrushMainI.linkSuccess(device);
    }

    @Override
    public void disConnected(SearchResult device) {
        toothbrushMainI.disConnected(device);
    }

    @Override
    public void linkFailed() {
        toothbrushMainI.linkFailed();
    }

    @Override
    public void receiveData(byte[] buf) {
        toothbrushMainI.receiveData(buf);
    }

    @Override
    public void autoLinkFail() {
        toothbrushMainI.autoLinkFail();
    }

    @Override
    public void autoLinkSuccess() {
        toothbrushMainI.autoLinkSuccess();
    }

    @Override
    public void searchDeviceResult(SearchResult device) {
        toothbrushMainI.searchDeviceResult(device);
    }
}
