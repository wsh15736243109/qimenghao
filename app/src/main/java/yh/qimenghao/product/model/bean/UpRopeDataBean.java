package yh.qimenghao.product.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Joy on 2018/12/1.14:13
 */
public class UpRopeDataBean implements Serializable {

    /**
     * userToken :
     * ropeData : {"userId":999999999999999999,"ropeDuration":0,"ropeCount":0,"timestamp":99999999999999999,"ropeType":"","deviceId":""}
     */

    private String userToken;
    private List<RopeDataBean> ropeDatas;

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public List<RopeDataBean> getRopeData() {
        return ropeDatas;
    }

    public void setRopeData(List<RopeDataBean> ropeData) {
        this.ropeDatas = ropeData;
    }

    public static class RopeDataBean {
        /**
         * userId : 999999999999999999
         * ropeDuration : 0
         * ropeCount : 0
         * timestamp : 99999999999999999
         * ropeType :
         * deviceId :
         */

        private long userId;
        private int ropeDuration;
        private int ropeCount;
        private long timestamp;
        private String ropeType;
        private String deviceId;

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public int getRopeDuration() {
            return ropeDuration;
        }

        public void setRopeDuration(int ropeDuration) {
            this.ropeDuration = ropeDuration;
        }

        public int getRopeCount() {
            return ropeCount;
        }

        public void setRopeCount(int ropeCount) {
            this.ropeCount = ropeCount;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        public String getRopeType() {
            return ropeType;
        }

        public void setRopeType(String ropeType) {
            this.ropeType = ropeType;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        @Override
        public String toString() {
            return "RopeDataBean{" +
                    "userId=" + userId +
                    ", ropeDuration=" + ropeDuration +
                    ", ropeCount=" + ropeCount +
                    ", timestamp=" + timestamp +
                    ", ropeType='" + ropeType + '\'' +
                    ", deviceId='" + deviceId + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "UpRopeDataBean{" +
                "userToken='" + userToken + '\'' +
                ", ropeData=" + ropeDatas +
                '}';
    }
}
