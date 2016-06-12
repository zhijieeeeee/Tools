package com.tt.tools.view.recyclerview;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

/**
 * <p> FileName： MyFooterRecyclerAdapter</p>
 * <p>
 * Description：经过封装的带footer的RecyclerAdapter
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 * @createdate 2016-03-01 16:47
 */
public class MyFooterRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /**
     * 普通的adapter
     */
    private RecyclerView.Adapter adapter;

    private static final int TYPE_FOOTER = 999;
    private static final int TYPE_NORMAL = 1000;

    private View footerView;

    public MyFooterRecyclerAdapter(RecyclerView.Adapter adapter, View footerView) {
        this.adapter = adapter;
        this.footerView = footerView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER)
            return new SimpleViewHolder(footerView);
        return adapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (adapter != null) {
            int count = adapter.getItemCount();
            if (position < count) {
                adapter.onBindViewHolder(holder, position);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (adapter != null && adapter.getItemCount() > 0) {
            //由于加了footer，数量+1
            return adapter.getItemCount() + 1;
        } else {
            return 0;
        }
    }

    @Override
    public long getItemId(int position) {
        if (adapter != null) {
            int count = adapter.getItemCount();
            if (position < count) {
                return adapter.getItemId(position);
            }
        }
        return -1;
    }

    @Override
    public int getItemViewType(int position) {
        if (isFooter(position)) {
            return TYPE_FOOTER;
        }
        if (adapter != null) {
            int count = adapter.getItemCount();
            if (position < count) {
                return adapter.getItemViewType(position);
            }
        }
        return TYPE_NORMAL;
    }

    /**
     * 当前布局是否为Footer
     *
     * @param position
     * @return
     */
    public boolean isFooter(int position) {
        return position == getItemCount() - 1;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return (getItemViewType(position) == RecyclerView.INVALID_TYPE || getItemViewType(position) == RecyclerView.INVALID_TYPE - 1)
                            ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null
                && lp instanceof StaggeredGridLayoutManager.LayoutParams
                && isFooter(holder.getLayoutPosition())) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(true);
        }
    }

    private class SimpleViewHolder extends RecyclerView.ViewHolder {
        public SimpleViewHolder(View itemView) {
            super(itemView);
        }
    }
}
