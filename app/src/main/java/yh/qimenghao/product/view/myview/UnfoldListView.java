package yh.qimenghao.product.view.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Joy on 2018/12/11.18:19
 */
public class UnfoldListView extends ListView {
    public UnfoldListView(Context context) {
        super(context);
    }

    public UnfoldListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UnfoldListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    /**
     * 重写该方法，达到使ListView适应ScrollView的效果
     */
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
