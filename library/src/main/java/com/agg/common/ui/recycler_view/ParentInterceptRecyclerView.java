package com.agg.common.ui.recycler_view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * <pre>
 *     author    : Agg
 *     blog      : https://blog.csdn.net/Agg_bin
 *     time      : 2019/06/21
 *     desc      : 当ViewPager中有嵌套RecyclerView，且RecyclerView横向滑动时，实现滑到头或滑到尾可以连续翻页。
 *     reference :
 *     remark    :
 * </pre>
 */
public class ParentInterceptRecyclerView extends RecyclerView {
    private int startX;

    public ParentInterceptRecyclerView(Context context) {
        super(context);
        setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
    }

    public ParentInterceptRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = (int) e.getRawX();
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                int endX = (int) e.getRawX();
                if ((endX < startX) && !canScrollHorizontally(1)) { // 手指向左，内容右滑到尾，父控件拦截
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else if ((endX > startX) && !canScrollHorizontally(-1)) { // 手指向右，内容左滑到头，父控件拦截
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                startX = endX;
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return super.onTouchEvent(e);
    }

}
