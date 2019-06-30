package yh.qimenghao.product.model;

/**
 * Created by Joy on 2018/10/18.15:31
 */
public class BleContent {
    /**
     * Created by Joy on 2018/10/18.14:30
     */
    //蓝牙UUID
    public static class BleUUID {
        public static final String serviceUUID = "0000AE00-0000-1000-8000-00805F9B34FB";//服务
       // public static final String serviceUUID = "0000ffe0-0000-1000-8000-00805F9B34FB";//服务
        public static final String readUUID = "";//读
        public static final String writeUUID = "0000AE01-0000-1000-8000-00805F9B34FB";//写
      //  public static final String writeUUID = "0000ffe1-0000-1000-8000-00805F9B34FB";//写
        public static final String notifyUUID = "0000AE02-0000-1000-8000-00805F9B34FB";//通知
       // public static final String notifyUUID = "0000ffe1-0000-1000-8000-00805F9B34FB";//通知
    }
    //权限申请码
    public static final int RERUESTCODE=0;
    //记录上次连接的蓝牙MAC: RELINKMAC //String类型
    public static final String  RELINKMAC="RELINKMAC";
    //是否需要回连：AUTOLINK  //true需要回连
    public static final String AUTOLINK="AUTOLINK";
}
