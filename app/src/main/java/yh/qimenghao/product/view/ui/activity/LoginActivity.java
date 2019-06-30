package yh.qimenghao.product.view.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import yh.qimenghao.product.R;
import yh.qimenghao.product.SelfApp;
import yh.qimenghao.product.model.ProjectConstant;
import yh.qimenghao.product.model.bean.BaseBean;
import yh.qimenghao.product.model.bean.UserInfoBean;
import yh.qimenghao.product.model.net.BaseObserver;
import yh.qimenghao.product.model.net.HttpMethods;
import yh.qimenghao.product.util.MoreUtils;
import yh.qimenghao.product.util.ShowUtil;
import yh.qimenghao.product.util.YaoSharedPreferences;
import yh.qimenghao.product.view.myview.YaoDialog;
import yh.qimenghao.product.view.ui.activity.toothbrush_activitys.ToothbrushActivity;

/**
 * A
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.phoneNumber)
    EditText phoneNumber;
    @BindView(R.id.clear)
    ImageView clear;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.showPassword)
    ImageView showPassword;
    @BindView(R.id.getPassword)
    TextView getPassword;
    @BindView(R.id.alarmInfo)
    TextView alarmInfo;
    @BindView(R.id.signIn)
    Button signIn;
    @BindView(R.id.register)
    Button register;

    private UserInfoBean userInfoBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        userInfoBean = YaoSharedPreferences.getObject(ProjectConstant.USERINFO);
        if (userInfoBean != null && !userInfoBean.getUserToken().equals("null")) {
            autoLogin();
        }
    }
    YaoDialog yaoDialog;
    private void autoLogin() {
        yaoDialog=new YaoDialog(this,R.layout.autologin_layout,0.5f,false,false);
        yaoDialog.setWidth(MoreUtils.dp2px(this,90));
        yaoDialog.show();
        login(userInfoBean.getUserToken());
    }

    @OnClick({R.id.clear, R.id.showPassword, R.id.getPassword, R.id.signIn, R.id.register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.clear:
                phoneNumber.setText("");
                break;
            case R.id.showPassword:
                if (showPassword.getTag().equals("0")) {
                    showPassword.setTag("1");
                    showPassword.setImageResource(R.mipmap.an);
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    showPassword.setTag("0");
                    showPassword.setImageResource(R.mipmap.ming);
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                break;
            case R.id.getPassword:
                Intent i1 = new Intent(this, RegisterActivity.class);
                i1.putExtra("type", 1);
                startActivity(i1);
                finish();
                break;
            case R.id.signIn:
                alarmInfo.setVisibility(View.INVISIBLE);
                if (phoneNumber.getText().toString().length() != 11) {
                    ShowUtil.shortToast(getResources().getString(R.string.phonenumber_error));
                    return;
                }
                if (phoneNumber.getText().toString().length() == 0) {
                    ShowUtil.shortToast(getResources().getString(R.string.phone_number_null));
                    return;
                } else if (password.getText().toString().length() == 0) {
                    ShowUtil.shortToast(getResources().getString(R.string.password_null));
                    return;
                }
                signIn.setEnabled(false);
                String userToken = MoreUtils.md5("password=" + password.getText().toString() + "&phoneNumber=" + phoneNumber.getText().toString() + "&" + ProjectConstant.yhKey);
                login(userToken);
                break;
            case R.id.register:
                Intent i2 = new Intent(this, RegisterActivity.class);
                i2.putExtra("type", 0);
                startActivity(i2);
                finish();
                break;
        }
    }

    private void login(String userToken) {
        HttpMethods.getInstance().login(userToken, this, new BaseObserver<BaseBean<UserInfoBean>>() {
            @Override
            public void onResult(int code, BaseBean<UserInfoBean> userInfoBeanBaseBean, Throwable e) {
                if(yaoDialog!=null)yaoDialog.cancel();signIn.setEnabled(true);
                if (code == 1) {
                    ShowUtil.shortToast(getResources().getString(R.string.other_error) + e.toString());
                    return;
                }
                if (userInfoBeanBaseBean.getCode() == 0) {
                    YaoSharedPreferences.putObject(ProjectConstant.USERINFO, userInfoBeanBaseBean.getData());
                    YaoSharedPreferences.putList(userToken, userInfoBeanBaseBean.getData().getDeviceList());
                    ShowUtil.d(userInfoBeanBaseBean.getData().toString());
                    if (userInfoBean == null || userInfoBean.getBirthday() == null) {
                        startActivity(new Intent(SelfApp.getApp().getAppContext(), SetUserInfoActivity.class));
                    } else {
                        // startActivity(new Intent(SelfApp.getApp().getAppContext(), RopeActivity.class));
                        startActivity(new Intent(SelfApp.getApp().getAppContext(), ToothbrushActivity.class));
                    }
                    finish();
                } else {
                    alarmInfo.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}

