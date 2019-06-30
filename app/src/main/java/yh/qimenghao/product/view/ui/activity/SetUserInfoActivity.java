package yh.qimenghao.product.view.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.zyyoona7.popup.EasyPopup;
import com.zyyoona7.popup.XGravity;
import com.zyyoona7.popup.YGravity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
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
import yh.qimenghao.product.view.myview.CustomDatePicker;
import yh.qimenghao.product.view.ui.activity.toothbrush_activitys.ToothbrushActivity;

public class SetUserInfoActivity extends BaseActivity {


    @BindView(R.id.jumpButton)
    Button jumpButton;
    @BindView(R.id.userName)
    EditText userName;
    @BindView(R.id.nanRb)
    RadioButton nanRb;
    @BindView(R.id.nvRb)
    RadioButton nvRb;
    @BindView(R.id.gradeTv)
    TextView gradeTv;
    @BindView(R.id.statureEt)
    EditText statureEt;
    @BindView(R.id.weightEt)
    EditText weightEt;
    @BindView(R.id.birthdayTv)
    TextView birthdayTv;
    @BindView(R.id.preference_1)
    RadioButton preference1;
    @BindView(R.id.preference_2)
    RadioButton preference2;
    @BindView(R.id.preference_3)
    RadioButton preference3;
    @BindView(R.id.ok)
    Button ok;
    @BindView(R.id.preferenceLL)
    LinearLayout preferenceLL;
    private String nickName, gender, stature, weight, grade, birthday, preference;
    private UserInfoBean userInfoBean;
    private CustomDatePicker customDatePicker;
    private int setInfo=0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_user_info;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setInfo=getIntent().getIntExtra("SETINFO",0);
        Log.d("Joy", setInfo+"");
        if(setInfo!=0){jumpButton.setVisibility(View.INVISIBLE);}
        userInfoBean = YaoSharedPreferences.getObject(ProjectConstant.USERINFO);
        if (null == userInfoBean) return;
        nickName = userInfoBean.getNickName() == null ? " " : userInfoBean.getNickName();
        userName.setText(nickName);
        gender = userInfoBean.getGender() == null ? "1" : userInfoBean.getGender();
        if (gender.equals("1")) {
            nanRb.setChecked(true);
        } else {
            nvRb.setChecked(true);
        }
        stature = userInfoBean.getStature() == null ? "0" : userInfoBean.getStature();
        statureEt.setText(stature);
        weight = userInfoBean.getWeight() == null ? "0" : userInfoBean.getWeight();
        weightEt.setText(weight);
        grade = userInfoBean.getGrade() == null ? "一年级" : userInfoBean.getGrade();
        gradeTv.setText(grade);
        birthday = userInfoBean.getBirthday() == null ? "1970-01-01" : userInfoBean.getBirthday();
        String[] birthdays = birthday.split("-");
        birthdayTv.setText(birthdays[0] + getResources().getString(R.string.year) + birthdays[1] + getResources().getString(R.string.month) + birthdays[2] + getResources().getString(R.string.day));
        preference = userInfoBean.getPreference() == null ? "0" : userInfoBean.getPreference();
        if (preference.equals("0")) {
            preference1.setChecked(true);
        } else if (preference.equals("1")) {
            preference2.setChecked(true);
        } else if(preference.equals("-1")){
            preferenceLL.setVisibility(View.GONE);
        }else {
            preference3.setChecked(true);
        }
    }

    private void showGradePop() {
        EasyPopup mPop;
        ListView gradelv;
        mPop = EasyPopup.create()
                .setContentView(this, R.layout.grade_layout)
                //.setAnimationStyle(R.style.RightPopAnim)
                //是否允许点击PopupWindow之外的地方消失
                .setFocusAndOutsideEnable(true)
                .apply();
        gradelv = mPop.findViewById(R.id.gradeLv);
        String[] list = getResources().getStringArray(R.array.grades);
        gradelv.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list));
        gradelv.setOnItemClickListener((parent, view, position, id) -> {
            gradeTv.setText(list[position]);
            grade = list[position];
            mPop.dismiss();
        });

        mPop.showAtAnchorView(gradeTv, YGravity.BELOW, XGravity.ALIGN_LEFT);

    }

    @OnClick({R.id.jumpButton, R.id.gradeTv, R.id.birthdayTv, R.id.ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.jumpButton:
                startActivity(new Intent(SelfApp.getApp().getAppContext(), ToothbrushActivity.class));
                finish();
                break;
            case R.id.gradeTv:
                showGradePop();
                break;
            case R.id.birthdayTv:
                initDatePicker(birthday);
                break;
            case R.id.ok:
                //ok.setEnabled(false);
                setUserInfo();
                break;
        }
    }

    private void setUserInfo() {
        HttpMethods.getInstance().setUserInfo(userInfoBean.getUserToken(),userName.getText().toString(), gender, grade, statureEt.getText().toString(), weightEt.getText().toString(), birthday, preference, this, new BaseObserver<BaseBean<UserInfoBean>>() {
            @Override
            public void onResult(int code, BaseBean<UserInfoBean> userInfoBeanBaseBean, Throwable e) {
                if(code==0){
                    if(userInfoBeanBaseBean.getCode()==0){
                        YaoSharedPreferences.putObject(ProjectConstant.USERINFO,userInfoBeanBaseBean.getData());
                        ShowUtil.shortToast(getResources().getString(R.string.set_success));
                        if(setInfo==0)startActivity(new Intent(SelfApp.getApp().getAppContext(), ToothbrushActivity.class));
                        finish();
                    }else {
                        ShowUtil.shortToast(getResources().getString(R.string.set_error)+userInfoBeanBaseBean.getCode());
                    }
                }else {
                    ShowUtil.shortToast(getResources().getString(R.string.other_error)+e.toString());
                }
            }
        });
    }

    @OnCheckedChanged({R.id.nanRb, R.id.nvRb, R.id.preference_1, R.id.preference_2, R.id.preference_3})
    public void onRadioCheck(CompoundButton view, boolean ischanged) {
        switch (view.getId()) {
            case R.id.nanRb:
                if (ischanged) {
                    gender = "1";
                }
                break;
            case R.id.nvRb:
                if (ischanged) {
                    gender = "0";
                }
                break;
            case R.id.preference_1:
                if (ischanged) {
                    preference="0";
                }
                break;
            case R.id.preference_2:
                if (ischanged) {
                    preference="1";
                }
                break;
            case R.id.preference_3:
                if (ischanged) {
                    preference="2";
                }
                break;
            default:
                break;
        }
    }
    private void initDatePicker(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        customDatePicker = new CustomDatePicker(this, time -> { // 回调接口，获得选中的时间
            String[] t=time.split(" ")[0].split("-");
            birthdayTv.setText(t[0]+getResources().getString(R.string.year)+" "+t[1]+getResources().getString(R.string.month)+" "+t[2]+getResources().getString(R.string.day));
            birthday=t[0]+"-"+t[1]+"-"+t[2];
        }, "1970-01-01 12:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker.showSpecificTime(false); // 不显示时和分
        customDatePicker.setIsLoop(true); // 允许循环滚动
        customDatePicker.show(date+" 12:00");
    }
}
