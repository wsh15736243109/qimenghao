package yh.qimenghao.product.model.bean;


import java.io.Serializable;
import java.util.List;

public class UserInfoBean implements Serializable {
    /**
     * userId : 6
     * nickname : a
     * password : 666666
     * gender : a
     * phoneNumber : 17602486866
     * stature : a
     * weight : a
     * grade : a
     * birthday : a
     * preference : a
     * userToken : 1f46c2ddc69c4b9ff277bc5846dc41e2
     * deviceList : [{"userId":123,"deviceId":"11:22:33","deviceNickname":"小牙刷","deviceType":2},{"userId":123,"deviceId":"11:22:33","deviceNickname":"小牙刷","deviceType":2}]
     */

    private Long userId;
    private String nickname;
    private String password;
    private String gender;
    private String phoneNumber;
    private String stature;
    private String weight;
    private String grade;
    private String birthday;
    private String preference;
    private String userToken;
    private List<DeviceListBean> deviceList;

    public UserInfoBean() {
    }

    public UserInfoBean(String userToken) {
        this.userToken = userToken;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickname;
    }

    public void setNickName(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStature() {
        return stature;
    }

    public void setStature(String stature) {
        this.stature = stature;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPreference() {
        return preference;
    }

    public void setPreference(String preference) {
        this.preference = preference;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public List<DeviceListBean> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<DeviceListBean> deviceList) {
        this.deviceList = deviceList;
    }

    public static class DeviceListBean implements Serializable{
        /**
         * userId : 123
         * deviceId : 11:22:33
         * deviceNickname : 小牙刷
         * deviceType : 2
         */

        private long userId;
        private String deviceId;
        private String deviceNickname;
        private int deviceType;
        private long did;

        public long getDid() {
            return did;
        }

        public void setDid(long did) {
            this.did = did;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getDeviceNickname() {
            return deviceNickname;
        }

        public void setDeviceNickname(String deviceNickname) {
            this.deviceNickname = deviceNickname;
        }

        public int getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(int deviceType) {
            this.deviceType = deviceType;
        }

        @Override
        public String toString() {
            return "DeviceListBean{" +
                    "userId=" + userId +
                    ", deviceId='" + deviceId + '\'' +
                    ", deviceNickname='" + deviceNickname + '\'' +
                    ", deviceType=" + deviceType +
                    ", did=" + did +
                    '}';
        }
    }
    @Override
    public String toString() {
        return "UserInfoBean{" +
                "userId=" + userId +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", stature='" + stature + '\'' +
                ", weight='" + weight + '\'' +
                ", grade='" + grade + '\'' +
                ", birthday='" + birthday + '\'' +
                ", preference='" + preference + '\'' +
                ", userToken='" + userToken + '\'' +
                ", deviceList=" + deviceList +
                '}';
    }
}
