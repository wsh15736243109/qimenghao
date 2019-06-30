package yh.qimenghao.product.model.bean;

/**
 * Created by Joy on 2019/3/8.15:48
 */
public class UpAppBean {

    /**
     * result : 1
     * newversion : 2
     * updateinfo : 修复bug
     * url : http://XXXXXX
     */

    private int result;
    private String newversion;
    private String updateinfo;
    private String url;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getNewversion() {
        return newversion;
    }

    public void setNewversion(String newversion) {
        this.newversion = newversion;
    }

    public String getUpdateinfo() {
        return updateinfo;
    }

    public void setUpdateinfo(String updateinfo) {
        this.updateinfo = updateinfo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "UpAppBean{" +
                "result=" + result +
                ", newversion=" + newversion +
                ", updateinfo='" + updateinfo + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
