package yh.qimenghao.product.view.ui.activity;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.WindowManager;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import yh.qimenghao.product.R;

/**
 * Created by Joy on 2018/11/27.11:36
 */
public abstract class BaseActivity extends RxAppCompatActivity {
    protected Unbinder mUnbinder;
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(getLayoutId());
        setImmersiveStateBar();
        mUnbinder = ButterKnife.bind(this);
        init(savedInstanceState);
    }

    protected abstract @LayoutRes
    int getLayoutId();

    protected abstract void init(Bundle savedInstanceState);

    // fragment的切换
    FragmentTransaction fragmentTransaction;
    protected void selectFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager
                .beginTransaction();
        if (currentFragment == null) {
            fragmentTransaction.add(R.id.frame_area, fragment).commit();
            currentFragment = fragment;
        }
        if (currentFragment != fragment) {
            // 先判断是否被add过
            if (!fragment.isAdded()) {
                // 隐藏当前的fragment，add下一个到Activity中
                fragmentTransaction.hide(currentFragment)
                        .add(R.id.frame_area, fragment).commit();
            } else {
                // 隐藏当前的fragment，显示下一个
                fragmentTransaction.hide(currentFragment).show(fragment)
                        .commit();
            }
            currentFragment = fragment;
        }

    }
    protected void removeFragment(Fragment fragment){
        fragmentTransaction.remove(fragment);
    }

    /**
     * 设置沉浸式状态栏
     */
    private void setImmersiveStateBar() {
        if (getWindow() != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            View view = getWindow().getDecorView();
            if (view != null) {
                view.setSystemUiVisibility(option);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                        | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
        }
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }
}
