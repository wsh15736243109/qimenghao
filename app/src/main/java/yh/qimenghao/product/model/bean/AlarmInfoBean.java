package yh.qimenghao.product.model.bean;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by Joy on 2019/3/5.14:12
 */
public class AlarmInfoBean implements Serializable ,Comparable<AlarmInfoBean>{
    public int m,s,num;

    public AlarmInfoBean(int m, int s, int num) {
        this.m = m;
        this.s = s;
        this.num = num;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public int getS() {
        return s;
    }

    public void setS(int s) {
        this.s = s;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "AlarmInfoBean{" +
                "m=" + m +
                ", s=" + s +
                ", num=" + num +
                '}';
    }

    @Override
    public int compareTo(@NonNull AlarmInfoBean o) {
        int i=this.getM()-o.getM();
        if(i==0){
            i=this.getS()-o.getS();
        }
        return i;
    }
}
