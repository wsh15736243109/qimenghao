package yh.qimenghao.product.model.ble;

import com.inuker.bluetooth.library.search.SearchResult;

/**
 * Created by Joy on 2018/12/22.09:43
 */
public interface BleI {
    void linkSuccess(SearchResult device);
    void disConnected(SearchResult device);
    void linkFailed();
    void receiveData(byte[] buf);
    void autoLinkFail();
    void autoLinkSuccess();
    void searchDeviceResult(SearchResult device);
}
