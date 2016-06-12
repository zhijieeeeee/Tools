package com.tt.tools.view.recyclerview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tt.tools.R;

/**
 * <p> FileName： MyRecyclerView</p>
 * <p>
 * Description：自定义自动加载更多的RecyclerView
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 * @CreateDate 2016/2/20
 */
public class MyRecyclerView extends RecyclerView {

    /**
     * 是否正在加载更多
     */
    private boolean isLoading;

    /**
     * 加载更多监听
     */
    private OnLoadMoreListener onLoadMoreListener;

    /**
     * 拖动监听，判断正在上拉还是下拉
     */
    private OnDragListener onDragListener;

    /**
     * 封装了footer的adapter
     */
    private MyFooterRecyclerAdapter myFooterRecyclerAdapter;

    private View footerView;

    public void setOnDragListener(OnDragListener onDragListener) {
        this.onDragListener = onDragListener;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public MyRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        footerView = LayoutInflater.from(getContext()).inflate(R.layout.public_footer, null);
        footerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (onLoadMoreListener != null && !isLoading && dy > 0) {
                    int lastVisiblePosition = getLastVisiblePosition();
                    //滑到倒数第一项时显示footer
                    if (lastVisiblePosition + 1 == getAdapter().getItemCount()) {
                        isLoading = true;
                        footerView.setVisibility(VISIBLE);
                        onLoadMoreListener.onLoadMore();
                    }
                }
                if (dy > 20) {//向上拖
                    if (onDragListener != null) {
                        onDragListener.onDragUp();
                    }
                }
                if (dy < 0) {//往下拖
                    if (onDragListener != null) {
                        onDragListener.onDragDown();
                    }
                }
            }
        });
    }

    @Override
    public void setAdapter(Adapter adapter) {
        myFooterRecyclerAdapter = new MyFooterRecyclerAdapter(adapter, footerView);
        super.setAdapter(myFooterRecyclerAdapter);
        adapter.registerAdapterDataObserver(mDataObserver);
    }

    /**
     * 设置上拉加载完成
     */
    public void setLoadComplete() {
        isLoading = false;
        footerView.setVisibility(GONE);
    }

    /**
     * 获取最后一条展示的位置
     *
     * @return
     */
    private int getLastVisiblePosition() {
        int position;
        if (getLayoutManager() instanceof LinearLayoutManager) {
            position = ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
        } else if (getLayoutManager() instanceof GridLayoutManager) {
            position = ((GridLayoutManager) getLayoutManager()).findLastVisibleItemPosition();
        } else if (getLayoutManager() instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) getLayoutManager();
            int[] lastPositions = layoutManager.findLastVisibleItemPositions(new int[layoutManager.getSpanCount()]);
            position = getMaxPosition(lastPositions);
        } else {
            position = getLayoutManager().getItemCount() - 1;
        }
        return position;
    }

    /**
     * 获得最大的位置
     *
     * @param positions
     * @return
     */
    private int getMaxPosition(int[] positions) {
        int size = positions.length;
        int maxPosition = Integer.MIN_VALUE;
        for (int i = 0; i < size; i++) {
            maxPosition = Math.max(maxPosition, positions[i]);
        }
        return maxPosition;
    }

    /**
     * 加载更多监听
     */
    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    /**
     * 滑动监听
     */
    public interface OnDragListener {
        /**
         * 向上拖
         */
        void onDragUp();

        /**
         * 向下拖
         */
        void onDragDown();
    }


    private RecyclerView.AdapterDataObserver mDataObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            myFooterRecyclerAdapter.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            myFooterRecyclerAdapter.notifyItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            myFooterRecyclerAdapter.notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            myFooterRecyclerAdapter.notifyItemRangeChanged(positionStart, itemCount, payload);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            myFooterRecyclerAdapter.notifyItemRangeRemoved(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            myFooterRecyclerAdapter.notifyItemMoved(fromPosition, toPosition);
        }
    };

}
