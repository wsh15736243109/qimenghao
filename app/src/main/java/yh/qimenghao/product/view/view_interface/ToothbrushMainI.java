package yh.qimenghao.product.view.view_interface;

import com.inuker.bluetooth.library.search.SearchResult;

/**
 * Created by Joy on 2018/12/22.11:57
 */
public interface ToothbrushMainI {
    void linkSuccess(SearchResult device);
    void disConnected(SearchResult device);
    void linkFailed();
    void receiveData(byte[] buf);
    void autoLinkFail();
    void autoLinkSuccess();
    void searchDeviceResult(SearchResult device);
}
