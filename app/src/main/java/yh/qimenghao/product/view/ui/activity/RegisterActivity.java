package yh.qimenghao.product.view.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.OnClick;
import yh.qimenghao.product.R;
import yh.qimenghao.product.SelfApp;
import yh.qimenghao.product.model.ProjectConstant;
import yh.qimenghao.product.model.bean.BaseBean;
import yh.qimenghao.product.model.bean.UserInfoBean;
import yh.qimenghao.product.model.net.BaseObserver;
import yh.qimenghao.product.model.net.HttpMethods;
import yh.qimenghao.product.util.ShowUtil;
import yh.qimenghao.product.util.YaoSharedPreferences;
import yh.qimenghao.product.view.myview.CountdownButton;
import yh.qimenghao.product.view.ui.activity.toothbrush_activitys.ToothbrushActivity;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.phoneNumber)
    EditText phoneNumber;
    @BindView(R.id.clear)
    ImageView clear;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.showPassword)
    ImageView showPassword;
    @BindView(R.id.code)
    EditText code;
    @BindView(R.id.getCode)
    CountdownButton getCode;
    @BindView(R.id.register)
    Button register;
    private int typeCode;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        Intent i = getIntent();
        typeCode = i.getIntExtra("type", 0);
        register.setText(typeCode == 0 ? getResources().getString(R.string.register) : getResources().getString(R.string.reset));
    }

    @OnClick({R.id.clear, R.id.showPassword, R.id.getCode, R.id.register})
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
            case R.id.getCode:
                if (phoneNumber.getText().toString().length() != 11) {
                    ShowUtil.shortToast(getResources().getString(R.string.phonenumber_error));
                    return;
                }
                if (phoneNumber.getText().toString().length() == 0 || password.getText().toString().length() == 0) {
                    ShowUtil.shortToast(getResources().getString(R.string.info_deficiency));
                    return;
                }
                HttpMethods.getInstance().getCode(phoneNumber.getText().toString(), this, new BaseObserver<BaseBean>() {
                    @Override
                    public void onResult(int code, BaseBean baseBean, Throwable e) {
                        if (code == 1) {
                            ShowUtil.shortToast(getResources().getString(R.string.other_error) + e.toString());
                            return;
                        }
                        if (baseBean.getCode() == 0) {
                            ShowUtil.shortToast(getResources().getString(R.string.getcode_success));
                        } else {
                            ShowUtil.shortToast(getResources().getString(R.string.other_error));
                        }
                    }
                });

                break;
            case R.id.register:
                if (phoneNumber.getText().toString().length() != 11) {
                    ShowUtil.shortToast(getResources().getString(R.string.phonenumber_error));
                    return;
                }
                if (phoneNumber.getText().toString().length() == 0 || password.getText().toString().length() == 0 || code.getText().toString().length() != 4) {
                    ShowUtil.shortToast(getResources().getString(R.string.info_deficiency));
                    return;
                }
                if (typeCode == 0) {
                    HttpMethods.getInstance().register(phoneNumber.getText().toString(), password.getText().toString(), code.getText().toString(), this, new BaseObserver<BaseBean<UserInfoBean>>() {
                        @Override
                        public void onResult(int code, BaseBean<UserInfoBean> userInfoBeanBaseBean, Throwable e) {
                            if (code == 1) {
                                ShowUtil.longToast(getResources().getString(R.string.other_error) + e.toString());
                                return;
                            }
                            if (code == 0) {
                                if (userInfoBeanBaseBean.getCode() == 1) {
                                    ShowUtil.longToast(getResources().getString(R.string.user_exist));
                                } else if (userInfoBeanBaseBean.getCode() == 0) {
                                    YaoSharedPreferences.putObject(ProjectConstant.USERINFO, userInfoBeanBaseBean.getData());
                                    ShowUtil.d(userInfoBeanBaseBean.toString());
                                    startActivity(new Intent(SelfApp.getApp().getAppContext(), SetUserInfoActivity.class));
                                    finish();
                                }
                            } else {
                                ShowUtil.longToast(getResources().getString(R.string.other_error) + e.toString());
                            }
                        }
                    });
                } else {
                    HttpMethods.getInstance().resetPassword(phoneNumber.getText().toString(), password.getText().toString(), code.getText().toString(), this, new BaseObserver<BaseBean<UserInfoBean>>() {
                        @Override
                        public void onResult(int code, BaseBean<UserInfoBean> userInfoBeanBaseBean, Throwable e) {
                            if (code == 1) {
                                ShowUtil.shortToast(getResources().getString(R.string.other_error) + e.toString());
                                return;
                            }
                            if (code == 0) {
                                if (userInfoBeanBaseBean.getCode() == 0) {
                                    YaoSharedPreferences.putObject(ProjectConstant.USERINFO, userInfoBeanBaseBean.getData());
                                    YaoSharedPreferences.putList(userInfoBeanBaseBean.getData().getUserToken(), userInfoBeanBaseBean.getData().getDeviceList());
                                    // startActivity(new Intent(SelfApp.getApp().getAppContext(), RopeActivity.class));
                                    startActivity(new Intent(SelfApp.getApp().getAppContext(), ToothbrushActivity.class));
                                    finish();
                                } else if(userInfoBeanBaseBean.getCode()==404) {
                                    ShowUtil.shortToast(getResources().getString(R.string.user_inexistence));
                                }
                            } else {
                                ShowUtil.shortToast(getResources().getString(R.string.other_error) + e.toString());
                            }
                        }
                    });
                }
                break;
        }
    }
}
