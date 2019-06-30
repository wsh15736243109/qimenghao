package yh.qimenghao.product.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Joy on 2019/1/4.10:56
 */
public class ToothbrushUpBean implements Serializable {
    private String userToken;
    private List<ArgToothbrushData> toothbrushDatas;

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public List<ArgToothbrushData> getToothbrushDatas() {
        return toothbrushDatas;
    }

    public void setToothbrushDatas(List<ArgToothbrushData> toothbrushDatas) {
        this.toothbrushDatas = toothbrushDatas;
    }

    public static class ArgToothbrushData {

        private String deviceId;

        private long timestamp;

        private int brushDuration=120;

        public ArgToothbrushData() {
        }

        public ArgToothbrushData(String deviceId, long timestamp) {
            this.deviceId = deviceId;
            this.timestamp = timestamp;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        public int getBrushDuration() {
            return brushDuration;
        }

        public void setBrushDuration(int brushDuration) {
            this.brushDuration = brushDuration;
        }

        @Override
        public String toString() {
            return "ArgToothbrushData{" +
                    "deviceId='" + deviceId + '\'' +
                    ", timestamp=" + timestamp +
                    ", brushDuration=" + brushDuration +
                    '}';
        }
    }
}
