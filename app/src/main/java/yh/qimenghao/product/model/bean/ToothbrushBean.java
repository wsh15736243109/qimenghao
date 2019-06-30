package yh.qimenghao.product.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Joy on 2019/1/4.10:47
 */
public class ToothbrushBean implements Serializable {


    /**
     * date : 1545880391
     * details : [{"timestamp":1545880391,"duration":1},{"timestamp":1545880391,"duration":2}]
     */

    private long date;
    private List<DetailsBean> details;

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public List<DetailsBean> getDetails() {
        return details;
    }

    public void setDetails(List<DetailsBean> details) {
        this.details = details;
    }

    public static class DetailsBean implements Serializable{
        /**
         * timestamp : 1545880391
         * duration : 1
         */

        private long timestamp;
        private int duration;

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        @Override
        public String toString() {
            return "DetailsBean{" +
                    "timestamp=" + timestamp +
                    ", duration=" + duration +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ToothbrushBean{" +
                "date=" + date +
                ", details=" + details +
                '}';
    }
}
