package yh.qimenghao.product.model.bean;

/**
 * Created by Joy on 2019/1/4.10:57
 */
public class ToothbrushUpReturnBean {

    /**
     * totalcount : 0
     */

    private int totalcount;

    public int getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(int totalcount) {
        this.totalcount = totalcount;
    }

    @Override
    public String toString() {
        return "ToothbrushUpReturnBean{" +
                "totalcount=" + totalcount +
                '}';
    }
}
