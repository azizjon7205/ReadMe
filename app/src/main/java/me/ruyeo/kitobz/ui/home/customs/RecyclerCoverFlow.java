package me.ruyeo.kitobz.ui.home.customs;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Chen Xiaoping (562818444@qq.com)
 * @version V1.0
 * @Datetime 2017-04-18
 */

public class RecyclerCoverFlow extends RecyclerView {

    private float mDownX;

    private CoverFlowLayoutManger.Builder mManagerBuilder;

    public RecyclerCoverFlow(Context context) {
        super(context);
        init();
    }

    public RecyclerCoverFlow(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RecyclerCoverFlow(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        createManageBuilder();
        setLayoutManager(mManagerBuilder.build());
        setChildrenDrawingOrderEnabled(true); //开启重新排序
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    private void createManageBuilder() {
        if (mManagerBuilder == null) {
            mManagerBuilder = new CoverFlowLayoutManger.Builder();
        }
    }

    public void setFlatFlow(boolean isFlat) {
        createManageBuilder();
        mManagerBuilder.setFlat(isFlat);
        setLayoutManager(mManagerBuilder.build());
    }

    public void setGreyItem(boolean greyItem) {
        createManageBuilder();
        mManagerBuilder.setGreyItem(greyItem);
        setLayoutManager(mManagerBuilder.build());
    }

    public void setAlphaItem(boolean alphaItem) {
        createManageBuilder();
        mManagerBuilder.setAlphaItem(alphaItem);
        setLayoutManager(mManagerBuilder.build());
    }

    public void setLoop() {
        createManageBuilder();
        mManagerBuilder.loop();
        setLayoutManager(mManagerBuilder.build());
    }

    public void set3DItem(boolean d3) {
        createManageBuilder();
        mManagerBuilder.set3DItem(d3);
        setLayoutManager(mManagerBuilder.build());
    }

    public void setIntervalRatio(float intervalRatio) {
        createManageBuilder();
        mManagerBuilder.setIntervalRatio(intervalRatio);
        setLayoutManager(mManagerBuilder.build());
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        if (!(layout instanceof CoverFlowLayoutManger)) {
            throw new IllegalArgumentException("The layout manager must be CoverFlowLayoutManger");
        }
        super.setLayoutManager(layout);
    }

    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        int center = getCoverFlowLayout().getCenterPosition();
        // 获取 RecyclerView 中第 i 个 子 view 的实际位置
        int actualPos = getCoverFlowLayout().getChildActualPos(i);

        // 距离中间item的间隔数
        int dist = actualPos - center;
        int order;
        if (dist < 0) { // [< 0] 说明 item 位于中间 item 左边，按循序绘制即可
            order = i;
        } else { // [>= 0] 说明 item 位于中间 item 右边，需要将顺序颠倒绘制
            order = childCount - 1 - dist;
        }

        if (order < 0) order = 0;
        else if (order > childCount -1) order = childCount - 1;

        return order;
    }

    public CoverFlowLayoutManger getCoverFlowLayout() {
        return ((CoverFlowLayoutManger)getLayoutManager());
    }

    public int getSelectedPos() {
        return getCoverFlowLayout().getSelectedPos();
    }

    public void setOnItemSelectedListener(CoverFlowLayoutManger.OnSelected l) {
        getCoverFlowLayout().setOnSelectedListener(l);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = ev.getX();
                getParent().requestDisallowInterceptTouchEvent(true); //设置父类不拦截滑动事件
                break;
            case MotionEvent.ACTION_MOVE:
                if ((ev.getX() > mDownX && getCoverFlowLayout().getCenterPosition() == 0) ||
                        (ev.getX() < mDownX && getCoverFlowLayout().getCenterPosition() ==
                                getCoverFlowLayout().getItemCount() -1)) {
                    //如果是滑动到了最前和最后，开放父类滑动事件拦截
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else {
                    //滑动到中间，设置父类不拦截滑动事件
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}