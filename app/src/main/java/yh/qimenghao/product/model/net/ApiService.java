package yh.qimenghao.product.model.net;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import yh.qimenghao.product.model.bean.BaseBean;
import yh.qimenghao.product.model.bean.RopeDataBean;
import yh.qimenghao.product.model.bean.ToothbrushBean;
import yh.qimenghao.product.model.bean.ToothbrushUpReturnBean;
import yh.qimenghao.product.model.bean.UpAppBean;
import yh.qimenghao.product.model.bean.UserInfoBean;

/**
 * Created by Joy on 2018/11/27.
 */

public interface ApiService {
    /**
     * ************************APP操作****************************
     */
    @FormUrlEncoded
    @POST("toothbrush/checkversion")
    Observable<BaseBean<UpAppBean>> checkVersion(@Field("checksum") String checksum, @Field("version") String version);

    /**
     * ************************用户操作****************************
     */
    @FormUrlEncoded
    @POST("user/getCode")
    Observable<BaseBean> getCode(@Field("checksum") String checksum, @Field("phoneNumber") String phoneNumber);

    @FormUrlEncoded
    @POST("user/register")
    Observable<BaseBean<UserInfoBean>> register(@Field("checksum") String checksum, @Field("phoneNumber") String phoneNumber, @Field("password") String password, @Field("verificationCode") String verificationCode);

    @FormUrlEncoded
    @POST("user/login")
    Observable<BaseBean<UserInfoBean>> login(@Field("checksum") String checksum, @Field("userToken") String userToken);

    @FormUrlEncoded
    @POST("user/setUserInfo")
    Observable<BaseBean<UserInfoBean>> setUserInfo(@Field("checksum") String checksum, @Field("userToken") String userToken, @Field("nickname") String nickname, @Field("gender") String gender, @Field("grade") String grade, @Field("stature") String stature, @Field("weight") String weight, @Field("birthday") String birthday, @Field("preference") String preference);

    @FormUrlEncoded
    @POST("user/resetPassword")
    Observable<BaseBean<UserInfoBean>> resetPassword(@Field("checksum") String checksum, @Field("phoneNumber") String phoneNumber, @Field("newPassword") String newPassword, @Field("verificationCode") String verificationCode);

    @FormUrlEncoded
    @POST("user/bindingdevice")
    Observable<BaseBean<List<UserInfoBean.DeviceListBean>>> bindDevice(@Field("checksum") String checksum, @Field("userToken") String usertoken, @Field("deviceId") String deviceid, @Field("deviceNickname") String deviceNickname, @Field("deviceType") String deviceType);

    @FormUrlEncoded
    @POST("user/unbinddevice")
    Observable<BaseBean<List<UserInfoBean.DeviceListBean>>> unBindDevice(@Field("checksum") String checksum, @Field("userToken") String usertoken, @Field("deviceId") String deviceid);

    @FormUrlEncoded
    @POST("user/updatedevicenickname")
    Observable<BaseBean<List<UserInfoBean.DeviceListBean>>> updateDeviceNickname(@Field("checksum") String checksum, @Field("userToken") String usertoken, @Field("deviceId") String deviceid, @Field("deviceNickname") String deviceNickname);

    /**
     * ************************跳绳****************************
     */
    @Headers({"Content-Type: application/json", "Accept: application/json", "checksum: 8fb0eccbcf2ddfd89879c277ae62087b"})
    @POST("ropedata/update")
    Observable<BaseBean<RopeDataBean>> update(@Body RequestBody body);

    @FormUrlEncoded
    @POST("ropedata/getdata")
    Observable<BaseBean<RopeDataBean>> getdata(@Field("checksum") String checksum, @Field("userToken") String usertoken, @Field("deviceId") String deviceid);

    /**
     * ************************牙刷****************************
     */
    @Headers({"Content-Type: application/json", "Accept: application/json", "checksum: 8fb0eccbcf2ddfd89879c277ae62087b"})
    @POST("toothbrush/update")
    Observable<BaseBean<ToothbrushUpReturnBean>> updateToothbrush(@Body RequestBody body);

    @FormUrlEncoded
    @POST("toothbrush/geteachpreviewdata")
    Observable<BaseBean<List<ToothbrushBean>>> getToothbrushData(@Field("checksum") String checksum, @Field("userToken") String usertoken, @Field("deviceId") String deviceid, @Field("page") int page);

    @FormUrlEncoded
    @POST("toothbrush/totalcount")
    Observable<BaseBean<ToothbrushUpReturnBean>> getBrushTotalcount(@Field("checksum") String checksum, @Field("userToken") String usertoken);
}
