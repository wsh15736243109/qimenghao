package yh.qimenghao.product.model.net;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * Created by Joy on 2018/11/27.18:36
 */
public abstract class BaseObserver<T> implements Observer<T> {
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {
        onResult(0,t,null);
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable e) {
        onResult(1,null,e);
        if (e instanceof HttpException) {     //   HTTP错误

        } else if (e instanceof ConnectException
                || e instanceof UnknownHostException) {   //   连接错误

        } else if (e instanceof InterruptedIOException) {   //  连接超时

        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {  //  解析错误

        }else {

        }
    }
    abstract public void onResult(int code,T t,Throwable e);
}
