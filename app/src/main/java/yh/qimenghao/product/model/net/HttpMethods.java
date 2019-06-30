package yh.qimenghao.product.model.net;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import yh.qimenghao.product.model.ProjectConstant;
import yh.qimenghao.product.model.bean.BaseBean;
import yh.qimenghao.product.model.bean.RopeDataBean;
import yh.qimenghao.product.model.bean.ToothbrushBean;
import yh.qimenghao.product.model.bean.ToothbrushUpReturnBean;
import yh.qimenghao.product.model.bean.UserInfoBean;
import yh.qimenghao.product.util.MoreUtils;

/**
 * Created Joy on 2018/11/27.
 */

public class HttpMethods {
   // private static final String BASE_URL = "http://192.168.1.7:8080/yhrope/";
    private static final String BASE_URL = "http://203.195.240.124:8080/yhrope/";
    private static final int TIME_OUT = 10;
    private Retrofit retrofit;
    private ApiService apiService;

    private HttpMethods() {
        /**
         * 构造函数私有化
         * 并在构造函数中进行retrofit的初始化
         */
        OkHttpClient client = new OkHttpClient();
        client.newBuilder().connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }


    private static class sinalInstance {
        public static final HttpMethods instance = new HttpMethods();
    }

    public static HttpMethods getInstance() {
        return sinalInstance.instance;
    }
    /**
     * 检查app更新
     */
    public void checkVersion(String version, LifecycleProvider<ActivityEvent> provider, BaseObserver<BaseBean> observer) {
        String checksum = MoreUtils.md5("version=" + version + "&" + ProjectConstant.yhKey);
        apiService.checkVersion(checksum, version)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(provider.bindToLifecycle())
                .subscribe(observer);
    }
    /**
     * 获取验证码
     */
    public void getCode(String phoneNumber, LifecycleProvider<ActivityEvent> provider, BaseObserver<BaseBean> observer) {
        String checksum = MoreUtils.md5("phoneNumber=" + phoneNumber + "&" + ProjectConstant.yhKey);
        apiService.getCode(checksum, phoneNumber)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(provider.bindToLifecycle())
                .subscribe(observer);
    }

    /**
     * 注册
     */
    public void register(String phoneNumber, String password, String verificationCode, LifecycleProvider<ActivityEvent> provider, BaseObserver<BaseBean<UserInfoBean>> observer) {
        String checksum = MoreUtils.md5("password=" + password + "&phoneNumber=" + phoneNumber + "&verificationCode=" + verificationCode + "&" + ProjectConstant.yhKey);
        apiService.register(checksum, phoneNumber, password, verificationCode)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(provider.bindToLifecycle())
                .subscribe(observer);
    }

    /**
     * 登陆
     */
    public void login(String userToken, LifecycleProvider<ActivityEvent> provider, BaseObserver<BaseBean<UserInfoBean>> observer) {
        String checksum = MoreUtils.md5("userToken=" + userToken + "&" + ProjectConstant.yhKey);
        apiService.login(checksum, userToken)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(provider.bindToLifecycle())
                .subscribe(observer);
    }

    /**
     * 重置密码
     */
    public void resetPassword(String phoneNumber, String newPassword, String verificationCode, LifecycleProvider<ActivityEvent> provider, BaseObserver<BaseBean<UserInfoBean>> observer) {
        String checksum = MoreUtils.md5("newPassword=" + newPassword + "&phoneNumber=" + phoneNumber + "&verificationCode=" + verificationCode + "&" + ProjectConstant.yhKey);
        apiService.resetPassword(checksum, phoneNumber, newPassword, verificationCode)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(provider.bindToLifecycle())
                .subscribe(observer);
    }

    /**
     * 设置用户信息
     */
    public void setUserInfo(String userToken, String nickname, String gender, String grade, String stature, String weight, String birthday, String preference, LifecycleProvider<ActivityEvent> provider, BaseObserver<BaseBean<UserInfoBean>> observer) {
        String checksum = MoreUtils.md5("birthday=" + birthday + "&gender=" + gender + "&grade=" + grade + "&nickname=" + nickname + "&preference=" + preference + "&stature=" + stature + "&userToken=" + userToken + "&weight=" + weight + "&" + ProjectConstant.yhKey);
        apiService.setUserInfo(checksum, userToken, nickname, gender, grade, stature, weight, birthday, preference)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(provider.bindToLifecycle())
                .subscribe(observer);
    }

    /**
     * 同步数据到服务器
     */
    public void update(RequestBody body, LifecycleProvider<?> provider, BaseObserver<BaseBean<RopeDataBean>> observer) {
        apiService.update(body)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(provider.bindToLifecycle())
                .subscribe(observer);
    }

    /**
     * 获取跳绳数据
     */
    public void getData(String userToken, String deviceId, LifecycleProvider<?> provider, BaseObserver<BaseBean<RopeDataBean>> observer) {
        String checksum;
        if (deviceId == null) {
            checksum = MoreUtils.md5("userToken=" + userToken + "&" + ProjectConstant.yhKey);
        } else {
            checksum = MoreUtils.md5("deviceid=" + deviceId + "&userToken=" + userToken + "&" + ProjectConstant.yhKey);
        }
        apiService.getdata(checksum, userToken, deviceId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(provider.bindToLifecycle())
                .subscribe(observer);
    }

    /**
     * 同步刷牙数据到服务器
     */
    public void upToothbrushDate(RequestBody body, LifecycleProvider<?> provider, BaseObserver<BaseBean<ToothbrushUpReturnBean>> observer) {
        apiService.updateToothbrush(body)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(provider.bindToLifecycle())
                .subscribe(observer);
    }

    /**
     * 获取牙刷数据
     */
    public void getToothbrushData(String userToken, String deviceId, int page, LifecycleProvider<?> provider, BaseObserver<BaseBean<List<ToothbrushBean>>> observer) {
        String checksum;
        checksum = MoreUtils.md5("deviceId=" + deviceId + "&page=" + page + "&userToken=" + userToken + "&" + ProjectConstant.yhKey);
        apiService.getToothbrushData(checksum, userToken, deviceId, page)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(provider.bindToLifecycle())
                .subscribe(observer);
    }

    /**
     * 绑定设备
     */
    public void bindDevice(String userToken, String deviceId, String deviceNickname, String deviceType, LifecycleProvider<?> provider, BaseObserver<BaseBean<List<UserInfoBean.DeviceListBean>>> observer) {
        String checksum;
        checksum = MoreUtils.md5("deviceId=" + deviceId + "&deviceNickname=" + deviceNickname + "&deviceType=" + deviceType + "&userToken=" + userToken + "&" + ProjectConstant.yhKey);
        apiService.bindDevice(checksum, userToken, deviceId, deviceNickname, deviceType)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(provider.bindToLifecycle())
                .subscribe(observer);
    }
    /**
     * 解绑设备
     */
    public void unBindDevice(String userToken, String deviceId, LifecycleProvider<?> provider, BaseObserver<BaseBean<List<UserInfoBean.DeviceListBean>>> observer) {
        String checksum;
        checksum = MoreUtils.md5("deviceId=" + deviceId + "&userToken=" + userToken + "&" + ProjectConstant.yhKey);
        apiService.unBindDevice(checksum, userToken, deviceId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(provider.bindToLifecycle())
                .subscribe(observer);
    }
    /**
     * 修改设备名称
     */
    public void updateDeviceNickname(String userToken, String deviceId, String deviceNickname, LifecycleProvider<?> provider, BaseObserver<BaseBean<List<UserInfoBean.DeviceListBean>>> observer) {
        String checksum;
        checksum = MoreUtils.md5("deviceId=" + deviceId + "&deviceNickname=" + deviceNickname +  "&userToken=" + userToken + "&" + ProjectConstant.yhKey);
        apiService.updateDeviceNickname(checksum, userToken, deviceId, deviceNickname)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(provider.bindToLifecycle())
                .subscribe(observer);
    }
    /**
     * 获取刷牙总数
     */
    public void getBrushTotalcount(String userToken,LifecycleProvider<?> provider, BaseObserver<BaseBean<ToothbrushUpReturnBean>> observer){
        String checksum;
        checksum = MoreUtils.md5("userToken=" + userToken + "&" + ProjectConstant.yhKey);
        apiService.getBrushTotalcount(checksum,userToken)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(provider.bindToLifecycle())
                .subscribe(observer);
        }
}
