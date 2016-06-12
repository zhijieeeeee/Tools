package com.tzj.frame.util;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * <p> ProjectName： Frame</p>
 * <p>
 * Description：ListView列表项相关工具类
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 * @CreateDate 2015-12-17 11:17
 */
public class ListViewUtil {

    /**
     * 精确计算listView的高度，注：可计算item不同高的listView
     *
     * @param listView
     * @return
     */
    public static int getListViewHeight(ListView listView) {
        if (listView == null) {
            return 0;
        }
        ListAdapter adapter = listView.getAdapter();
        if (adapter == null || adapter.getCount() == 0) {
            return 0;
        }
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            //获得子项的View
            View listItem = adapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }
        //计算入divider的高度
        totalHeight += listView.getDividerHeight() * (adapter.getCount() - 1);
        return totalHeight;
    }

    /**
     * 动态改变ListView的高度
     *
     * @param listView
     */
    public static void updateListViewHeight(ListView listView) {
        if (listView == null) {
            return;
        }
        int height = getListViewHeight(listView);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) listView.getLayoutParams();
        layoutParams.height = height;
        listView.setLayoutParams(layoutParams);
    }

    /**
     * 获得ListView Item的高度，默认第0个
     *
     * @param listView
     * @return
     */
    public static int getItemHeight(ListView listView) {
        if (listView == null) {
            return 0;
        }
        ListAdapter adapter = listView.getAdapter();
        if (adapter == null || adapter.getCount() == 0) {
            return 0;
        }
        View itemView = adapter.getView(0, null, listView);
        itemView.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        return itemView.getMeasuredHeight();
    }

    /**
     * 获得ListView Item的高度
     *
     * @param listView
     * @param index    item位置
     * @return
     */
    public static int getItemHeightByIndex(ListView listView, int index) {
        if (listView == null) {
            return 0;
        }
        ListAdapter adapter = listView.getAdapter();
        if (adapter == null || adapter.getCount() <= index) {
            return 0;
        }
        View itemView = adapter.getView(index, null, listView);
        itemView.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        return itemView.getMeasuredHeight();
    }

    //    /**
//     * 返回ListView的高度(把divider的高度也计算进来了),注：此方法只能计算item等高的listView
//     *
//     * @param listView
//     * @return
//     */
//    public static int getListViewHeight(ListView listView) {
//        if (listView == null) {
//            return 0;
//        }
//        ListAdapter adapter = listView.getAdapter();
//        if (adapter == null || adapter.getCount() == 0) {
//            return 0;
//        }
//        View itemView = adapter.getView(0, null, listView);
//        itemView.measure(
//                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
//                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//        return listView.getDividerHeight() * (adapter.getCount() - 1) + itemView.getMeasuredHeight() * adapter.getCount();
//    }

}
