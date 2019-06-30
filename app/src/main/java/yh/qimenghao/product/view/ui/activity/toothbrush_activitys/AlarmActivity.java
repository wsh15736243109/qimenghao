package yh.qimenghao.product.view.ui.activity.toothbrush_activitys;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.aigestudio.wheelpicker.WheelPicker;
import com.inuker.bluetooth.library.search.SearchResult;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yh.qimenghao.product.R;
import yh.qimenghao.product.SelfApp;
import yh.qimenghao.product.model.ProjectConstant;
import yh.qimenghao.product.model.bean.AlarmInfoBean;
import yh.qimenghao.product.presenter.PersenterBleI;
import yh.qimenghao.product.presenter.PersenterBleImpl;
import yh.qimenghao.product.util.ShowUtil;
import yh.qimenghao.product.util.YaoSharedPreferences;
import yh.qimenghao.product.view.myview.YaoDialog;
import yh.qimenghao.product.view.ui.activity.BaseActivity;
import yh.qimenghao.product.view.view_interface.ToothbrushMainI;

public class AlarmActivity extends BaseActivity implements ToothbrushMainI, CompoundButton.OnCheckedChangeListener {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.alarmTv1)
    TextView alarmTv1;
    @BindView(R.id.alarmSwitch1)
    Switch alarmSwitch1;
    @BindView(R.id.alarmTv2)
    TextView alarmTv2;
    @BindView(R.id.alarmSwitch2)
    Switch alarmSwitch2;
    @BindView(R.id.ok)
    Button ok;
    @BindView(R.id.alarmInfoTv1)
    TextView alarmInfoTv1;
    @BindView(R.id.alarmInfoTv2)
    TextView alarmInfoTv2;
    @BindView(R.id.alarmTv3)
    TextView alarmTv3;
    @BindView(R.id.alarmInfoTv3)
    TextView alarmInfoTv3;
    @BindView(R.id.alarmSwitch3)
    Switch alarmSwitch3;
    @BindView(R.id.alarmTv4)
    TextView alarmTv4;
    @BindView(R.id.alarmInfoTv4)
    TextView alarmInfoTv4;
    @BindView(R.id.alarmSwitch4)
    Switch alarmSwitch4;
    @BindView(R.id.alarmTv5)
    TextView alarmTv5;
    @BindView(R.id.alarmInfoTv5)
    TextView alarmInfoTv5;
    @BindView(R.id.alarmSwitch5)
    Switch alarmSwitch5;
    @BindView(R.id.alarmTv6)
    TextView alarmTv6;
    @BindView(R.id.alarmInfoTv6)
    TextView alarmInfoTv6;
    @BindView(R.id.alarmSwitch6)
    Switch alarmSwitch6;
    @BindView(R.id.ll1)
    LinearLayout ll1;
    @BindView(R.id.ll2)
    LinearLayout ll2;
    @BindView(R.id.ll3)
    LinearLayout ll3;
    @BindView(R.id.ll4)
    LinearLayout ll4;
    @BindView(R.id.ll5)
    LinearLayout ll5;
    @BindView(R.id.ll6)
    LinearLayout ll6;
    private int m1 = 7, m2 = 7, m3 = 7, m4 = 7, m5 = 7, m6 = 7, s1 = 30, s2 = 30, s3 = 30, s4 = 30, s5 = 30, s6 = 30;
    private int alarmItem1 = 1, alarmItem2 = 1, alarmItem3 = 1, alarmItem4 = 1, alarmItem5 = 1, alarmItem6 = 1;
    private PersenterBleI persenterBle;
    private List<byte[]> cmds;
    private List<AlarmInfoBean> alarmInfoBeanList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_alarm;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        persenterBle = new PersenterBleImpl(this);
        cmds = new ArrayList<>();
        alarmSwitch1.setOnCheckedChangeListener(this);
        alarmSwitch2.setOnCheckedChangeListener(this);
        alarmSwitch3.setOnCheckedChangeListener(this);
        alarmSwitch4.setOnCheckedChangeListener(this);
        alarmSwitch5.setOnCheckedChangeListener(this);
        alarmSwitch6.setOnCheckedChangeListener(this);
        List<AlarmInfoBean> alarmList = YaoSharedPreferences.getList(ProjectConstant.ALARMINFOBEANLIST);
        String[] infoArray = getResources().getStringArray(R.array.alarm_info_array);
        if (alarmList != null) {
            for (int p = 0; p < alarmList.size(); p++) {
                AlarmInfoBean alarmInfoBean = alarmList.get(p);
                String s0 = alarmInfoBean.getS() < 10 ? "0" + alarmInfoBean.getS() : alarmInfoBean.getS() + "";
                switch (p + 1) {
                    case 1:
                        alarmTv1.setText(alarmInfoBean.getM() + ":" + s0);
                        alarmInfoTv1.setText(infoArray[alarmInfoBean.getNum() - 1]);
                        alarmSwitch1.setChecked(true);
                        m1 = alarmInfoBean.getM();
                        s1 = alarmInfoBean.getS();
                        alarmItem1 = alarmInfoBean.getNum();
                        ll1.setBackgroundColor(getResources().getColor(R.color.white));
                        break;
                    case 2:
                        alarmTv2.setText(alarmInfoBean.getM() + ":" + s0);
                        alarmInfoTv2.setText(infoArray[alarmInfoBean.getNum() - 1]);
                        alarmSwitch2.setChecked(true);
                        m2 = alarmInfoBean.getM();
                        s2 = alarmInfoBean.getS();
                        alarmItem2 = alarmInfoBean.getNum();
                        ll2.setBackgroundColor(getResources().getColor(R.color.white));
                        break;
                    case 3:
                        alarmTv3.setText(alarmInfoBean.getM() + ":" + s0);
                        alarmInfoTv3.setText(infoArray[alarmInfoBean.getNum() - 1]);
                        alarmSwitch3.setChecked(true);
                        m3 = alarmInfoBean.getM();
                        s3 = alarmInfoBean.getS();
                        alarmItem3 = alarmInfoBean.getNum();
                        ll3.setBackgroundColor(getResources().getColor(R.color.white));
                        break;
                    case 4:
                        alarmTv4.setText(alarmInfoBean.getM() + ":" + s0);
                        alarmInfoTv4.setText(infoArray[alarmInfoBean.getNum() - 1]);
                        alarmSwitch4.setChecked(true);
                        m4 = alarmInfoBean.getM();
                        s4 = alarmInfoBean.getS();
                        alarmItem4 = alarmInfoBean.getNum();
                        ll4.setBackgroundColor(getResources().getColor(R.color.white));
                        break;
                    case 5:
                        alarmTv5.setText(alarmInfoBean.getM() + ":" + s0);
                        alarmInfoTv5.setText(infoArray[alarmInfoBean.getNum() - 1]);
                        alarmSwitch5.setChecked(true);
                        m5 = alarmInfoBean.getM();
                        s5 = alarmInfoBean.getS();
                        alarmItem5 = alarmInfoBean.getNum();
                        ll5.setBackgroundColor(getResources().getColor(R.color.white));
                        break;
                    case 6:
                        alarmTv6.setText(alarmInfoBean.getM() + ":" + s0);
                        alarmInfoTv6.setText(infoArray[alarmInfoBean.getNum() - 1]);
                        alarmSwitch6.setChecked(true);
                        m6 = alarmInfoBean.getM();
                        s6 = alarmInfoBean.getS();
                        alarmItem6 = alarmInfoBean.getNum();
                        ll6.setBackgroundColor(getResources().getColor(R.color.white));
                        break;
                }
            }
        }
    }

    YaoDialog yaoDialog, choiceAlarmItemDialog;

    private void choiceAlarmTime(int t) {
        yaoDialog = new YaoDialog(this, R.layout.time_picker_layout, true, view1 -> {
            WheelPicker mMinPicker;
            WheelPicker mSecondPicker;
            Button commit;
            mMinPicker = view1.findViewById(R.id.minPicker);
            mSecondPicker = view1.findViewById(R.id.secondPicker);
            commit = view1.findViewById(R.id.commit);
            ArrayList secondData = new ArrayList();
            for (int i = 0; i <60; i++) {
                secondData.add(i);
            }
            mSecondPicker.setData(secondData);
            mMinPicker.setSelectedItemPosition(2);
            commit.setOnClickListener(v -> {
                setBrushTime(t, mMinPicker.getCurrentItemPosition() + 1, mSecondPicker.getCurrentItemPosition());
                yaoDialog.cancel();
            });

        });
        yaoDialog.show();
    }

    private void choiceAlarmItem(int i) {
        choiceAlarmItemDialog = new YaoDialog(this, R.layout.alarm_list_layout, true, view -> {
            TextView mAlarmItem1;
            TextView mAlarmItem2;
            TextView mAlarmItem3;
            TextView mAlarmItem4;
            TextView mAlarmItem5;
            TextView mAlarmItem6;
            mAlarmItem1 = view.findViewById(R.id.alarm_item1);
            mAlarmItem2 = view.findViewById(R.id.alarm_item2);
            mAlarmItem3 = view.findViewById(R.id.alarm_item3);
            mAlarmItem4 = view.findViewById(R.id.alarm_item4);
            mAlarmItem5 = view.findViewById(R.id.alarm_item5);
            mAlarmItem6 = view.findViewById(R.id.alarm_item6);
            mAlarmItem1.setOnClickListener(v -> {
                switch (i) {
                    case 1:
                        alarmItem1 = 1;
                        alarmInfoTv1.setText(mAlarmItem1.getText());
                        break;
                    case 2:
                        alarmItem2 = 1;
                        alarmInfoTv2.setText(mAlarmItem1.getText());
                        break;
                    case 3:
                        alarmItem3 = 1;
                        alarmInfoTv3.setText(mAlarmItem1.getText());
                        break;
                    case 4:
                        alarmItem4 = 1;
                        alarmInfoTv4.setText(mAlarmItem1.getText());
                        break;
                    case 5:
                        alarmItem5 = 1;
                        alarmInfoTv5.setText(mAlarmItem1.getText());
                        break;
                    case 6:
                        alarmItem6 = 1;
                        alarmInfoTv6.setText(mAlarmItem1.getText());
                        break;
                }
                choiceAlarmItemDialog.cancel();
            });
            mAlarmItem2.setOnClickListener(v -> {
                switch (i) {
                    case 1:
                        alarmItem1 = 2;
                        alarmInfoTv1.setText(mAlarmItem2.getText());
                        break;
                    case 2:
                        alarmItem2 = 2;
                        alarmInfoTv2.setText(mAlarmItem2.getText());
                        break;
                    case 3:
                        alarmItem3 = 2;
                        alarmInfoTv3.setText(mAlarmItem2.getText());
                        break;
                    case 4:
                        alarmItem4 = 2;
                        alarmInfoTv4.setText(mAlarmItem2.getText());
                        break;
                    case 5:
                        alarmItem5 = 2;
                        alarmInfoTv5.setText(mAlarmItem2.getText());
                        break;
                    case 6:
                        alarmItem6 = 2;
                        alarmInfoTv6.setText(mAlarmItem2.getText());
                        break;
                }
                choiceAlarmItemDialog.cancel();
            });
            mAlarmItem3.setOnClickListener(v -> {
                switch (i) {
                    case 1:
                        alarmItem1 = 3;
                        alarmInfoTv1.setText(mAlarmItem3.getText());
                        break;
                    case 2:
                        alarmItem2 = 3;
                        alarmInfoTv2.setText(mAlarmItem3.getText());
                        break;
                    case 3:
                        alarmItem3 = 3;
                        alarmInfoTv3.setText(mAlarmItem3.getText());
                        break;
                    case 4:
                        alarmItem4 = 3;
                        alarmInfoTv4.setText(mAlarmItem3.getText());
                        break;
                    case 5:
                        alarmItem5 = 3;
                        alarmInfoTv5.setText(mAlarmItem3.getText());
                        break;
                    case 6:
                        alarmItem6 = 3;
                        alarmInfoTv6.setText(mAlarmItem3.getText());
                        break;
                }
                choiceAlarmItemDialog.cancel();
            });
            mAlarmItem4.setOnClickListener(v -> {
                switch (i) {
                    case 1:
                        alarmItem1 = 4;
                        alarmInfoTv1.setText(mAlarmItem4.getText());
                        break;
                    case 2:
                        alarmItem2 = 4;
                        alarmInfoTv2.setText(mAlarmItem4.getText());
                        break;
                    case 3:
                        alarmItem3 = 4;
                        alarmInfoTv3.setText(mAlarmItem4.getText());
                        break;
                    case 4:
                        alarmItem4 = 4;
                        alarmInfoTv4.setText(mAlarmItem4.getText());
                        break;
                    case 5:
                        alarmItem5 = 4;
                        alarmInfoTv5.setText(mAlarmItem4.getText());
                        break;
                    case 6:
                        alarmItem6 = 4;
                        alarmInfoTv6.setText(mAlarmItem4.getText());
                        break;
                }
                choiceAlarmItemDialog.cancel();
            });
            mAlarmItem5.setOnClickListener(v -> {
                switch (i) {
                    case 1:
                        alarmItem1 = 5;
                        alarmInfoTv1.setText(mAlarmItem5.getText());
                        break;
                    case 2:
                        alarmItem2 = 5;
                        alarmInfoTv2.setText(mAlarmItem5.getText());
                        break;
                    case 3:
                        alarmItem3 = 5;
                        alarmInfoTv3.setText(mAlarmItem5.getText());
                        break;
                    case 4:
                        alarmItem4 = 5;
                        alarmInfoTv4.setText(mAlarmItem5.getText());
                        break;
                    case 5:
                        alarmItem5 = 5;
                        alarmInfoTv5.setText(mAlarmItem5.getText());
                        break;
                    case 6:
                        alarmItem6 = 5;
                        alarmInfoTv6.setText(mAlarmItem5.getText());
                        break;
                }
                choiceAlarmItemDialog.cancel();
            });
            mAlarmItem6.setOnClickListener(v -> {
                switch (i) {
                    case 1:
                        alarmItem1 = 6;
                        alarmInfoTv1.setText(mAlarmItem6.getText());
                        break;
                    case 2:
                        alarmItem2 = 6;
                        alarmInfoTv2.setText(mAlarmItem6.getText());
                        break;
                    case 3:
                        alarmItem3 = 6;
                        alarmInfoTv3.setText(mAlarmItem6.getText());
                        break;
                    case 4:
                        alarmItem4 = 6;
                        alarmInfoTv4.setText(mAlarmItem6.getText());
                        break;
                    case 5:
                        alarmItem5 = 6;
                        alarmInfoTv5.setText(mAlarmItem6.getText());
                        break;
                    case 6:
                        alarmItem6 = 6;
                        alarmInfoTv6.setText(mAlarmItem6.getText());
                        break;
                }
                choiceAlarmItemDialog.cancel();
            });
        });
        choiceAlarmItemDialog.show();
    }

    private void setBrushTime(int t, int m, int s) {
        ShowUtil.d(m + " " + s + " ");
        String s0 = s > 9 ? s + "" : "0" + s;
        if (t == 1) {
            m1 = m;
            s1 = s;
            alarmTv1.setText(m + ":" + s0);
        } else if (t == 2) {
            m2 = m;
            s2 = s;
            alarmTv2.setText(m + ":" + s0);
        } else if (t == 3) {
            m3 = m;
            s3 = s;
            alarmTv3.setText(m + ":" + s0);
        } else if (t == 4) {
            m4 = m;
            s4 = s;
            alarmTv4.setText(m + ":" + s0);
        } else if (t == 5) {
            m5 = m;
            s5 = s;
            alarmTv5.setText(m + ":" + s0);
        } else if (t == 6) {
            m6 = m;
            s6 = s;
            alarmTv6.setText(m + ":" + s0);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @OnClick({R.id.back, R.id.alarmTv1, R.id.alarmTv2, R.id.alarmTv3, R.id.alarmTv4, R.id.alarmTv5, R.id.alarmTv6,
            R.id.alarmInfoTv1, R.id.alarmInfoTv2, R.id.alarmInfoTv3, R.id.alarmInfoTv4, R.id.alarmInfoTv5, R.id.alarmInfoTv6, R.id.ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.alarmTv1:
                choiceAlarmTime(1);
                break;
            case R.id.alarmTv2:
                choiceAlarmTime(2);
                break;
            case R.id.alarmTv3:
                choiceAlarmTime(3);
                break;
            case R.id.alarmTv4:
                choiceAlarmTime(4);
                break;
            case R.id.alarmTv5:
                choiceAlarmTime(5);
                break;
            case R.id.alarmTv6:
                choiceAlarmTime(6);
                break;
            case R.id.alarmInfoTv1:
                choiceAlarmItem(1);
                break;
            case R.id.alarmInfoTv2:
                choiceAlarmItem(2);
                break;
            case R.id.alarmInfoTv3:
                choiceAlarmItem(3);
                break;
            case R.id.alarmInfoTv4:
                choiceAlarmItem(4);
                break;
            case R.id.alarmInfoTv5:
                choiceAlarmItem(5);
                break;
            case R.id.alarmInfoTv6:
                choiceAlarmItem(6);
                break;
            case R.id.ok:
                cmds.clear();
                alarmInfoBeanList.clear();
                if (alarmSwitch1.isChecked()) {
                    alarmInfoBeanList.add(new AlarmInfoBean(m1, s1, alarmItem1));
                }
                if (alarmSwitch2.isChecked()) {
                    if (!checkRepeat(m2, s2)) {
                        alarmInfoBeanList.add(new AlarmInfoBean(m2, s2, alarmItem1));
                    }

                }
                if (alarmSwitch3.isChecked()) {
                    if (!checkRepeat(m3, s3)) {
                        alarmInfoBeanList.add(new AlarmInfoBean(m3, s3, alarmItem1));
                    }

                }
                if (alarmSwitch4.isChecked()) {
                    if (!checkRepeat(m4, s4)) {
                        alarmInfoBeanList.add(new AlarmInfoBean(m4, s4, alarmItem1));
                    }
                }
                if (alarmSwitch5.isChecked()) {
                    if (!checkRepeat(m5, s5)) {
                        alarmInfoBeanList.add(new AlarmInfoBean(m5, s5, alarmItem1));
                    }
                }
                if (alarmSwitch6.isChecked()) {
                    if (!checkRepeat(m6, s6)) {
                        alarmInfoBeanList.add(new AlarmInfoBean(m6, s6, alarmItem1));
                    }
                }

                if (alarmInfoBeanList.size() > 1) {
                    alarmInfoBeanList.sort(Comparator.naturalOrder());
                }
                for (int i = 1; i < alarmInfoBeanList.size() + 1; i++) {
                    byte alarmItem = 1;
                    switch (i) {
                        case 1:
                            alarmItem = (byte) alarmItem1;
                            break;
                        case 2:
                            alarmItem = (byte) alarmItem2;
                            break;
                        case 3:
                            alarmItem = (byte) alarmItem3;
                            break;
                        case 4:
                            alarmItem = (byte) alarmItem4;
                            break;
                        case 5:
                            alarmItem = (byte) alarmItem5;
                            break;
                        case 6:
                            alarmItem = (byte) alarmItem6;
                            break;
                    }
                    cmds.add(new byte[]{(byte) 0xff, (byte) 0x02, (byte) 0x00, (byte) alarmInfoBeanList.size(), (byte) 0x00, (byte) i, (byte) alarmInfoBeanList.get(i - 1).getM(), (byte) alarmInfoBeanList.get(i - 1).getS(), alarmItem});
                }
                if (!SelfApp.getApp().isLinked()) {
                    ShowUtil.shortToast(getResources().getString(R.string.no_link));
                }
                if (alarmInfoBeanList.size() == 0) {
                    persenterBle.sendData(new byte[]{(byte) 0xff, (byte) 0x02, (byte) 0x00, (byte) 0, (byte) 0x00, (byte) 0, (byte) 0, (byte) 0, 0});
                } else {
                    persenterBle.sendData(cmds.get(0));
                }
                ok.setEnabled(false);
                break;
        }
    }

    private boolean checkRepeat(int m, int s) {
        boolean f=false;
        for (AlarmInfoBean alarmInfoBean : alarmInfoBeanList) {
            if (m == alarmInfoBean.getM() && s == alarmInfoBean.getS()) {
                f=true;
                return true;
            } else {
                f=false;
            }
        }
        return f;
    }

    @Override
    public void linkSuccess(SearchResult device) {

    }

    @Override
    public void disConnected(SearchResult device) {

    }

    @Override
    public void linkFailed() {

    }

    @Override
    public void receiveData(byte[] buf) {
        if (buf[5] != 0) {
            ShowUtil.d(buf[5] + "");
            persenterBle.sendData(cmds.get(buf[5] - 1));
        } else {
            ShowUtil.shortToast(getResources().getString(R.string.set_success));
            YaoSharedPreferences.putList(ProjectConstant.ALARMINFOBEANLIST, alarmInfoBeanList);
            ok.setEnabled(true);
        }
    }

    @Override
    public void autoLinkFail() {

    }

    @Override
    public void autoLinkSuccess() {

    }

    @Override
    public void searchDeviceResult(SearchResult device) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.alarmSwitch1:
                if (isChecked) {
                    ll1.setBackgroundColor(getResources().getColor(R.color.white));
                } else {
                    ll1.setBackgroundColor(getResources().getColor(R.color.grey_200));
                }
                break;
            case R.id.alarmSwitch2:
                if (isChecked) {
                    ll2.setBackgroundColor(getResources().getColor(R.color.white));
                } else {
                    ll2.setBackgroundColor(getResources().getColor(R.color.grey_200));
                }
                break;
            case R.id.alarmSwitch3:
                if (isChecked) {
                    ll3.setBackgroundColor(getResources().getColor(R.color.white));
                } else {
                    ll3.setBackgroundColor(getResources().getColor(R.color.grey_200));
                }
                break;
            case R.id.alarmSwitch4:
                if (isChecked) {
                    ll4.setBackgroundColor(getResources().getColor(R.color.white));
                } else {
                    ll4.setBackgroundColor(getResources().getColor(R.color.grey_200));
                }
                break;
            case R.id.alarmSwitch5:
                if (isChecked) {
                    ll5.setBackgroundColor(getResources().getColor(R.color.white));
                } else {
                    ll5.setBackgroundColor(getResources().getColor(R.color.grey_200));
                }
                break;
            case R.id.alarmSwitch6:
                if (isChecked) {
                    ll6.setBackgroundColor(getResources().getColor(R.color.white));
                } else {
                    ll6.setBackgroundColor(getResources().getColor(R.color.grey_200));
                }
                break;
        }
    }
}
