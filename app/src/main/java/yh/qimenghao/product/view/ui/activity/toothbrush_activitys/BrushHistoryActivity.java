package yh.qimenghao.product.view.ui.activity.toothbrush_activitys;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.inuker.bluetooth.library.search.SearchResult;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yh.qimenghao.product.R;
import yh.qimenghao.product.model.ProjectConstant;
import yh.qimenghao.product.model.bean.BaseBean;
import yh.qimenghao.product.model.bean.ToothbrushBean;
import yh.qimenghao.product.model.bean.UserInfoBean;
import yh.qimenghao.product.model.net.BaseObserver;
import yh.qimenghao.product.model.net.HttpMethods;
import yh.qimenghao.product.presenter.PersenterBleI;
import yh.qimenghao.product.presenter.PersenterBleImpl;
import yh.qimenghao.product.util.MoreUtils;
import yh.qimenghao.product.util.ShowUtil;
import yh.qimenghao.product.view.adapter.AwardAdapter;
import yh.qimenghao.product.view.adapter.DetailDayAdapter;
import yh.qimenghao.product.view.adapter.GridAdapter;
import yh.qimenghao.product.view.myview.YaoDialog;
import yh.qimenghao.product.view.ui.activity.BaseActivity;
import yh.qimenghao.product.view.ui.fragment.toothbrush_fragments.MyFragment;
import yh.qimenghao.product.view.view_interface.ToothbrushMainI;

import static yh.qimenghao.product.util.ShowUtil.d;
import static yh.qimenghao.product.util.YaoSharedPreferences.getList;
import static yh.qimenghao.product.util.YaoSharedPreferences.getObject;

public class BrushHistoryActivity extends BaseActivity implements ToothbrushMainI {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.data21RecyclerView)
    XRecyclerView data21RecyclerView;
    @BindView(R.id.nodataTv)
    TextView noDataTv;
    @BindView(R.id.atar1)
    ImageView atar1;
    @BindView(R.id.atar2)
    ImageView atar2;
    @BindView(R.id.todayList)
    ListView todayList;
    List<ToothbrushBean> lists = new ArrayList<>();
    @BindView(R.id.emptyInfo)
    TextView emptyInfo;
    @BindView(R.id.titInfo)
    TextView titInfo;
    @BindView(R.id.award)
    ImageView award;
    private GridAdapter gridAdapter;
    private int page = 1;
    private UserInfoBean userInfoBean;
    private List<UserInfoBean.DeviceListBean> deviceList;
    PersenterBleI persenterBle;

    private int dayIndex = 3, choice3Index = 0, choice7Index = 0, choice10Index = 0, choice14Index = 0, choice18Index = 0, choice21Index = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_brush_history;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        persenterBle = new PersenterBleImpl(this);
        data21RecyclerView.setLayoutManager(new GridLayoutManager(this, 7));
        // data21RecyclerView.setLayoutManager(layoutManager);
        data21RecyclerView.setPullRefreshEnabled(false);
        data21RecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallPulse);
        gridAdapter = new GridAdapter(this);
        gridAdapter.setLists(lists);
        data21RecyclerView.setAdapter(gridAdapter);

        userInfoBean = getObject(ProjectConstant.USERINFO);
        deviceList = getList(userInfoBean.getUserToken());
        DetailDayAdapter detailDayAdapter = new DetailDayAdapter(BrushHistoryActivity.this);
        if (deviceList != null && deviceList.size() != 0) {
            noDataTv.setVisibility(View.GONE);
            data21RecyclerView.setVisibility(View.VISIBLE);
            HttpMethods.getInstance().getToothbrushData(userInfoBean.getUserToken(), deviceList.get(0).getDeviceId(), page, this, new BaseObserver<BaseBean<List<ToothbrushBean>>>() {
                @Override
                public void onResult(int code, BaseBean<List<ToothbrushBean>> toothbrushBeanBaseBean, Throwable e) {
                    if (toothbrushBeanBaseBean != null) d(toothbrushBeanBaseBean.toString());
                    if (code != 0) {
                        ShowUtil.shortToast(e.toString());
                        return;
                    }
                    if (toothbrushBeanBaseBean.getCode() == 204) {
                        for (int i = 0; i < 21; i++) {
                            List<ToothbrushBean.DetailsBean> detailsBeanList = new ArrayList<>();
                            ToothbrushBean toothbrushBean = new ToothbrushBean();
                            toothbrushBean.setDate((System.currentTimeMillis() / 1000) - 86400 * i);
                            toothbrushBean.setDetails(detailsBeanList);
                            lists.add(toothbrushBean);
                        }
                        gridAdapter.setLists(lists);
                        todayList.setVisibility(View.GONE);
                        emptyInfo.setVisibility(View.VISIBLE);
                        atar1.setVisibility(View.GONE);
                        atar2.setVisibility(View.GONE);
                        data21RecyclerView.setNoMore(true);
                    } else if (toothbrushBeanBaseBean.getCode() == 403) {
                        ShowUtil.shortToast(getResources().getString(R.string.other_error));
                    } else {
                        lists = toothbrushBeanBaseBean.getData();
                        if(lists!=null)gridAdapter.setLists(lists);
                        page += 1;
                        if (lists != null && lists.size() != 0) {
                            titInfo.setText(MoreUtils.timeStamp2Date2(lists.get(0).getDate(), null) + " " + MoreUtils.dateToWeek(MoreUtils.timeStamp2Date2(lists.get(0).getDate(), null)));
                            if (lists.get(0).getDetails() != null)
                                detailDayAdapter.setDetailsBeanList(lists.get(0).getDetails());
                            todayList.setAdapter(detailDayAdapter);
                            todayList.setVisibility(View.VISIBLE);
                            emptyInfo.setVisibility(View.GONE);
                            if (lists.get(0).getDetails() != null && lists.get(0).getDetails().size() != 0) {
                                if (lists.get(0).getDetails().size() == 1) {
                                    atar1.setVisibility(View.VISIBLE);
                                    atar2.setVisibility(View.GONE);
                                } else {
                                    atar1.setVisibility(View.VISIBLE);
                                    atar2.setVisibility(View.VISIBLE);
                                }
                            } else {
                                atar1.setVisibility(View.GONE);
                                atar2.setVisibility(View.GONE);
                                todayList.setVisibility(View.GONE);
                                emptyInfo.setVisibility(View.VISIBLE);
                            }
                        } else {
                            todayList.setVisibility(View.GONE);
                            emptyInfo.setVisibility(View.VISIBLE);
                            atar1.setVisibility(View.GONE);
                            atar2.setVisibility(View.GONE);
                        }
                    }
                }
            });
        } else {
            noDataTv.setVisibility(View.VISIBLE);
            data21RecyclerView.setVisibility(View.GONE);
        }

        gridAdapter.setOnClickListener(p -> {
            List<ToothbrushBean.DetailsBean> detailDatas = lists.get(p).getDetails();
            if (detailDatas != null && detailDatas.size() != 0) {
                detailDayAdapter.setDetailsBeanList(detailDatas);
                todayList.setVisibility(View.VISIBLE);
                emptyInfo.setVisibility(View.GONE);
                if (detailDatas.size() == 1) {
                    atar1.setVisibility(View.VISIBLE);
                    atar2.setVisibility(View.GONE);
                } else {
                    atar1.setVisibility(View.VISIBLE);
                    atar2.setVisibility(View.VISIBLE);
                }

            } else {
                todayList.setVisibility(View.GONE);
                emptyInfo.setVisibility(View.VISIBLE);
                atar1.setVisibility(View.GONE);
                atar2.setVisibility(View.GONE);
            }
            titInfo.setText(MoreUtils.timeStamp2Date2(lists.get(p).getDate(), null) + " " + MoreUtils.dateToWeek(MoreUtils.timeStamp2Date2(lists.get(p).getDate(), null)));
        });
        data21RecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onLoadMore() {
                HttpMethods.getInstance().getToothbrushData(userInfoBean.getUserToken(), deviceList.get(0).getDeviceId(), page, BrushHistoryActivity.this, new BaseObserver<BaseBean<List<ToothbrushBean>>>() {
                    @Override
                    public void onResult(int code, BaseBean<List<ToothbrushBean>> listBaseBean, Throwable e) {
                        if (code != 0) {
                            ShowUtil.shortToast(e.toString());
                            return;
                        }
                        if (listBaseBean.getCode() == 0) {
                            gridAdapter.addLists(listBaseBean.getData());
                            page += 1;
                            data21RecyclerView.loadMoreComplete();
                        } else if (listBaseBean.getCode() == 208) {
                            data21RecyclerView.setNoMore(true);
                        }
                    }
                });
            }
        });
    }


    @OnClick({R.id.back, R.id.award})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.award:
                showAwardDialog();
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    YaoDialog awardDialog;
    AwardAdapter awardAdapter;

    private void showAwardDialog() {
        awardDialog = new YaoDialog(this, R.layout.award_layout, true, view -> {
            RadioButton mAwardRb3;
            RadioButton mAwardRb7;
            RadioButton mAwardRb10;
            RadioButton mAwardRb14;
            RadioButton mAwardRb18;
            RadioButton mAwardRb21;
            RecyclerView mAwardRv;
            Button commit;
            mAwardRb3 = view.findViewById(R.id.awardRb3);
            mAwardRb7 = view.findViewById(R.id.awardRb7);
            mAwardRb10 = view.findViewById(R.id.awardRb10);
            mAwardRb14 = view.findViewById(R.id.awardRb14);
            mAwardRb18 = view.findViewById(R.id.awardRb18);
            mAwardRb21 = view.findViewById(R.id.awardRb21);
            mAwardRv = view.findViewById(R.id.awardRv);
            commit = view.findViewById(R.id.ok);
            mAwardRb3.setOnCheckedChangeListener(onCheckedChangeListener);
            mAwardRb7.setOnCheckedChangeListener(onCheckedChangeListener);
            mAwardRb10.setOnCheckedChangeListener(onCheckedChangeListener);
            mAwardRb14.setOnCheckedChangeListener(onCheckedChangeListener);
            mAwardRb18.setOnCheckedChangeListener(onCheckedChangeListener);
            mAwardRb21.setOnCheckedChangeListener(onCheckedChangeListener);
            awardAdapter = new AwardAdapter(this);
            StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(7, StaggeredGridLayoutManager.HORIZONTAL);
            //manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
            mAwardRv.setLayoutManager(manager);
            mAwardRv.setAdapter(awardAdapter);
            awardAdapter.setOnSelectListener(index -> {
                d(index + "");
                switch (dayIndex) {
                    case 3:
                        choice3Index = index;
                        break;
                    case 7:
                        choice7Index = index;
                        break;
                    case 10:
                        choice10Index = index;
                        break;
                    case 14:
                        choice14Index = index;
                        break;
                    case 18:
                        choice18Index = index;
                        break;
                    case 21:
                        choice21Index = index;
                        break;
                }
            });
            commit.setOnClickListener(v -> {

                persenterBle.sendData(new byte[]{(byte) 0xff, (byte) 0x06, (byte) 0x00, (byte) 0x01, (byte) 0x00,
                        (byte) 0x01, (byte) choice3Index, (byte) choice7Index, (byte) choice10Index,
                        (byte) choice14Index, (byte) choice18Index, (byte) choice21Index});
                dayIndex = 3;
                choice3Index = 0;
                choice7Index = 0;
                choice10Index = 0;
                choice14Index = 0;
                choice18Index = 0;
                choice21Index = 0;
                awardDialog.cancel();
            });

        });
        awardDialog.show();
    }

    CompoundButton.OnCheckedChangeListener onCheckedChangeListener = (buttonView, isChecked) -> {
        switch (buttonView.getId()) {
            case R.id.awardRb3:
                if (isChecked) {
                    dayIndex = 3;
                    awardAdapter.setIndex(choice3Index);
                }
                break;
            case R.id.awardRb7:
                if (isChecked) {
                    dayIndex = 7;
                    awardAdapter.setIndex(choice7Index);
                }
                break;
            case R.id.awardRb10:
                if (isChecked) {
                    dayIndex = 10;
                    awardAdapter.setIndex(choice10Index);
                }
                break;
            case R.id.awardRb14:
                if (isChecked) {
                    dayIndex = 14;
                    awardAdapter.setIndex(choice14Index);
                }
                break;
            case R.id.awardRb18:
                if (isChecked) {
                    dayIndex = 18;
                    awardAdapter.setIndex(choice18Index);
                }
                break;
            case R.id.awardRb21:
                if (isChecked) {
                    dayIndex = 21;
                    awardAdapter.setIndex(choice21Index);
                }
                break;
            default:
                break;
        }
    };

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
        if (buf[1] == 0x06) {
            if (buf[6] == 0x00) {
                ShowUtil.shortToast(getResources().getString(R.string.set_success));
            } else {
                ShowUtil.shortToast(getResources().getString(R.string.set_error));
            }
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
}
