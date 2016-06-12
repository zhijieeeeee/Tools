package com.tzj.frame.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * <p> FileName： Frame</p>
 * <p>
 * Description：适配器基类
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 * @CreateDate 2015/10/20
 */
public abstract class FrameBaseAdapter<T> extends BaseAdapter implements View.OnClickListener {

    public Context context;
    public List<T> list;

    public FrameBaseAdapter(Context context, List<T> list) {
        this.context = context;
        this.list = list;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    /**
     * 获取布局View
     */
    public View inflateView(int layoutId, ViewGroup parentView) {
        return LayoutInflater.from(context).inflate(layoutId, parentView, false);
    }

    /**
     * 获取控件，封装了findViewById
     *
     * @param view  布局
     * @param resId 资源id
     * @return <T> 控件
     */
    public <V extends View> V getView(View view, int resId) {
        View v = view.findViewById(resId);
        return (V) v;
    }

    /**
     * 获取控件，封装了findViewById和onClickListener
     *
     * @param view  布局
     * @param resId 资源id
     * @return <T> 控件
     */
    public <V extends View> V getViewAndClick(View view, int resId) {
        View v = view.findViewById(resId);
        v.setOnClickListener(this);
        return (V) v;
    }

    /**
     * 控件点击事件
     *
     * @param v
     */
    public abstract void onViewClick(View v);

    public int getCount() {
        return list != null ? list.size() : 0;
    }

    public Object getItem(int position) {
        return list != null ? list.get(position) : null;
    }

    public long getItemId(int position) {
        return position;
    }

    public abstract View getView(int position, View convertView, ViewGroup parent);

    @Override
    public void onClick(View v) {
        onViewClick(v);
    }
}