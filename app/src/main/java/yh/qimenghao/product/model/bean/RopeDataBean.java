package yh.qimenghao.product.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Joy on 2018/12/1.14:05
 */
public class RopeDataBean implements Serializable {

    /**
     * totalnum : 99999999999999999
     * totalcount : 99999999999999999
     * totaltimes : 0
     * totaldays : 0
     * todaydata : [{"type":"","duration":0,"timestamp":99999999999999999,"count":0,"calorie":0,"speed":0},{"type":"","duration":0,"timestamp":0,"count":0,"calorie":0,"speed":0}]
     */

    private long totalnum;
    private long totalcount;
    private int totaltimes;
    private int totaldays;
    private long totalcalorie;
    private List<TodaydataBean> todaydata;

    public long getTotalcalorie() {
        return totalcalorie;
    }

    public void setTotalcalorie(long totalcalorie) {
        this.totalcalorie = totalcalorie;
    }

    public long getTotalnum() {
        return totalnum;
    }

    public void setTotalnum(long totalnum) {
        this.totalnum = totalnum;
    }

    public long getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(long totalcount) {
        this.totalcount = totalcount;
    }

    public int getTotaltimes() {
        return totaltimes;
    }

    public void setTotaltimes(int totaltimes) {
        this.totaltimes = totaltimes;
    }

    public int getTotaldays() {
        return totaldays;
    }

    public void setTotaldays(int totaldays) {
        this.totaldays = totaldays;
    }

    public List<TodaydataBean> getTodaydata() {
        return todaydata;
    }

    public void setTodaydata(List<TodaydataBean> todaydata) {
        this.todaydata = todaydata;
    }

    public static class TodaydataBean {
        /**
         * type :
         * duration : 0
         * timestamp : 99999999999999999
         * count : 0
         * calorie : 0
         * speed : 0
         */

        private String ropeType;
        private int ropeDuration;
        private long timestamp;
        private int ropeCount;
        private int calorie;
        private int speed;

        public String getRopeType() {
            return ropeType;
        }

        public void setRopeType(String ropeType) {
            this.ropeType = ropeType;
        }

        public int getRopeDuration() {
            return ropeDuration;
        }

        public void setRopeDuration(int ropeDuration) {
            this.ropeDuration = ropeDuration;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        public int getRopeCount() {
            return ropeCount;
        }

        public void setRopeCount(int ropeCount) {
            this.ropeCount = ropeCount;
        }

        public int getCalorie() {
            return calorie;
        }

        public void setCalorie(int calorie) {
            this.calorie = calorie;
        }

        public int getSpeed() {
            return speed;
        }

        public void setSpeed(int speed) {
            this.speed = speed;
        }

        @Override
        public String toString() {
            return "TodaydataBean{" +
                    "ropeType='" + ropeType + '\'' +
                    ", ropeDuration=" + ropeDuration +
                    ", timestamp=" + timestamp +
                    ", ropeCount=" + ropeCount +
                    ", calorie=" + calorie +
                    ", speed=" + speed +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "RopeDataBean{" +
                "totalnum=" + totalnum +
                ", totalcount=" + totalcount +
                ", totaltimes=" + totaltimes +
                ", totaldays=" + totaldays +
                ", totalcalorie=" + totalcalorie +
                ", todaydata=" + todaydata +
                '}';
    }
}
