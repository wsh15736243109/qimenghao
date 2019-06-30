package yh.qimenghao.product.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inuker.bluetooth.library.search.SearchResult;

import java.util.ArrayList;
import java.util.List;

import yh.qimenghao.product.R;
import yh.qimenghao.product.model.ProjectConstant;
import yh.qimenghao.product.model.bean.UserInfoBean;
import yh.qimenghao.product.util.YaoSharedPreferences;

/**
 * Created by Joy on 2018/10/30.17:40
 */
public class DeviceListAdapter extends RecyclerView.Adapter {
    private LinkListener linkListener;
    private Context context;
    private List<SearchResult> deviceList = new ArrayList();
    private List<UserInfoBean.DeviceListBean> localDeviceList;

    public DeviceListAdapter(Context context, LinkListener linkListener) {
        this.linkListener = linkListener;
        this.context = context;
        String userToken = ((UserInfoBean) YaoSharedPreferences.getObject(ProjectConstant.USERINFO)).getUserToken();
        localDeviceList = YaoSharedPreferences.getList(userToken);
    }

    public DeviceListAdapter(Context context, List<SearchResult> deviceList, LinkListener linkListener) {
        this.linkListener = linkListener;
        this.context = context;
        this.deviceList = deviceList;
        String userToken = ((UserInfoBean) YaoSharedPreferences.getObject(ProjectConstant.USERINFO)).getUserToken();
        localDeviceList = YaoSharedPreferences.getList(userToken);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.device_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (localDeviceList != null) {
                String nickName=checkNickName(deviceList.get(position));
                if(nickName.equals("null")){
                    ((ViewHolder) holder).name.setText(deviceList.get(position).getName() + " " + deviceList.get(position).getAddress());
                }else {
                    ((ViewHolder) holder).name.setText(nickName);
                }

        } else {
            ((ViewHolder) holder).name.setText(deviceList.get(position).getName() + " " + deviceList.get(position).getAddress());
        }
        ((ViewHolder) holder).name.setOnClickListener((v -> {
            if (linkListener != null) linkListener.link(deviceList.get(position),((ViewHolder) holder).name.getText().toString());
        }));
    }

    private String checkNickName(SearchResult device) {
        for (UserInfoBean.DeviceListBean deviceListBean : localDeviceList) {
            if (deviceListBean.getDeviceId().equals(device.getAddress())) {
                return deviceListBean.getDeviceNickname();
            }
        }
        return "null";
    }

    @Override
    public int getItemCount() {
        return deviceList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.deviceName);
        }
    }

    public interface LinkListener {
        void link(SearchResult device,String nickName);
    }

    public List<SearchResult> getDeviceList() {
        return deviceList;
    }

    public void addDevice(SearchResult device) {
        if (deviceList.size() == 0) {
            deviceList.add(device);
            //notifyDataSetChanged();
            notifyItemChanged(deviceList.size() - 1);
        } else {
            if (!checkDevice(device)) {
                deviceList.add(device);
                notifyItemChanged(deviceList.size() - 1);
                //notifyDataSetChanged();
            }
        }

    }

    private boolean checkDevice(SearchResult device) {
        for (SearchResult searchResult : deviceList) {
            if (device.getAddress().equals(searchResult.getAddress())) {
                return true;
            }
        }
        return false;
    }

    public void clearDeviceList() {
        deviceList.clear();
        notifyDataSetChanged();
    }

    public void setDeviceList(List<SearchResult> list) {
        this.deviceList = list;
        notifyDataSetChanged();
    }
}
