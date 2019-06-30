package yh.qimenghao.product.util;

import android.util.Log;
import android.widget.Toast;

import yh.qimenghao.product.SelfApp;

/**
 * Created by Joy on 2018/10/31.14:14
 */
public class ShowUtil {
    private static boolean showLog=true;
    public static void d(String tag,String info){
        if(showLog)Log.d(tag, info);
    }
    public static void d(String info){
        if(showLog)Log.d("Joy", info);
    }
    public static void i(String tag,String info){
        if(showLog)Log.i(tag, info);
    }
    public static void i(String info){
        if(showLog)Log.i("Joy", info);
    }
    public static void longToast(String info){
        Toast.makeText(SelfApp.getApp().getAppContext(), info, Toast.LENGTH_LONG).show();
    }
    public static void shortToast(String info){
        Toast.makeText(SelfApp.getApp().getAppContext(), info, Toast.LENGTH_SHORT).show();
    }
}
