package yh.qimenghao.product.view.myview;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import yh.qimenghao.product.R;
import yh.qimenghao.product.util.UnitChange;

/**
 * Created by Joy on 2018/12/25.10:51
 */
public class YaoDialog {
    private Context context;
    private Dialog controlDialog;
    private @LayoutRes
    int layout;
    private int width = 300;
    private boolean canceledOnTouchOutside = false;
    private boolean cancelAble = true;

    public void setCancelAble(boolean cancelAble) {
        this.cancelAble = cancelAble;
    }

    private float dimAmount = 0.3f;
    private int gravity = Gravity.CENTER;
    private OnViewLinster onViewLinster;
    private View view;

    public YaoDialog(Context context, OnViewLinster onViewLinster) {
        this.context = context;
        this.onViewLinster = onViewLinster;
    }

    public YaoDialog(Context context, int layout, float dimAmount,boolean canceledOnTouchOutside,boolean cancelAble) {
        this.context = context;
        this.layout = layout;
        this.dimAmount=dimAmount;
        this.cancelAble=cancelAble;
        this.canceledOnTouchOutside=canceledOnTouchOutside;
    }

    public YaoDialog(Context context, @LayoutRes int layout, float dimAmount, OnViewLinster onViewLinster) {
        this.context = context;
        this.layout = layout;
        this.dimAmount = dimAmount;
        this.onViewLinster = onViewLinster;
    }
    public YaoDialog(Context context, @LayoutRes int layout, float dimAmount, boolean canceledOnTouchOutside,OnViewLinster onViewLinster) {
        this.context = context;
        this.layout = layout;
        this.dimAmount = dimAmount;
        this.onViewLinster = onViewLinster;
        this.canceledOnTouchOutside=canceledOnTouchOutside;
    }
    public YaoDialog(Context context, @LayoutRes int layout, float dimAmount) {
        this.context = context;
        this.layout = layout;
        this.dimAmount = dimAmount;
    }

    public YaoDialog(Context context, @LayoutRes int layout, boolean canceledOnTouchOutside, OnViewLinster onViewLinster) {
        this.context = context;
        this.layout = layout;
        this.canceledOnTouchOutside = canceledOnTouchOutside;
        this.onViewLinster = onViewLinster;
    }
    public YaoDialog(Context context, @LayoutRes int layout, boolean canceledOnTouchOutside,boolean cancelAble, OnViewLinster onViewLinster) {
        this.context = context;
        this.layout = layout;
        this.canceledOnTouchOutside = canceledOnTouchOutside;
        this.cancelAble=cancelAble;
        this.onViewLinster = onViewLinster;
    }

    public YaoDialog(Context context, @LayoutRes int layout, int width, OnViewLinster onViewLinster) {
        this.context = context;
        this.layout = layout;
        this.width = width;
        this.onViewLinster = onViewLinster;
    }

    public void setLayout(int layout) {
        this.layout = layout;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        this.canceledOnTouchOutside = canceledOnTouchOutside;
    }

    public void setDimAmount(float dimAmount) {
        this.dimAmount = dimAmount;
    }

    public void setGravity(int gravity) {
        this.gravity = gravity;
    }

    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(layout, null);
        if (onViewLinster != null) onViewLinster.onView(view);
        controlDialog = builder.create();
        controlDialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
        if(!cancelAble)controlDialog.setCancelable(false);
        controlDialog.show();
        WindowManager.LayoutParams lp = controlDialog.getWindow().getAttributes();
        lp.width = UnitChange.dip2px(context, width);//定义宽度
        controlDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        controlDialog.getWindow().setAttributes(lp);
        controlDialog.getWindow().setContentView(view);//自定义布局应该在这里添加，要在dialog.show()的后面
        controlDialog.getWindow().setGravity(gravity);//可以设置显示的位置
        controlDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        controlDialog.getWindow().setDimAmount(dimAmount);
    }
    public boolean isShowing(){
        return controlDialog.isShowing();
    }

    public void cancel() {
        if (controlDialog != null && controlDialog.isShowing()) {
            controlDialog.cancel();
        }
    }

    public interface OnViewLinster {
        void onView(View view);
    }
}
