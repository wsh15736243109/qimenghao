package yh.qimenghao.product.presenter;

import com.inuker.bluetooth.library.search.SearchResult;

/**
 * Created by Joy on 2018/12/21.16:25
 */
public interface PersenterBleI {
    void linkBle(SearchResult device);
    void disConnect(SearchResult device);
    void sendData(byte[] buf);
    //byte[] readData();
    void searchDevice(boolean reLink);
    void stopSearch();
}
