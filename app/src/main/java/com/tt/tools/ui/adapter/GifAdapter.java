package com.tt.tools.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tt.tools.R;
import com.tt.tools.model.bean.GifBean;

import java.util.List;

/**
 * <p> FileName： JokeAdapter</p>
 * <p>
 * Description：趣图适配器
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 * @CreateDate 2016/2/20
 */
public class GifAdapter extends RecyclerView.Adapter<GifAdapter.GifViewHolder> {

    private Context context;

    private List<GifBean.InterestingImg> gifList;

    public GifAdapter(Context context, List<GifBean.InterestingImg> gifList) {
        this.context = context;
        this.gifList = gifList;
    }

    @Override
    public GifAdapter.GifViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_gif, viewGroup, false);
        GifViewHolder viewHolder = new GifViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final GifViewHolder viewHolder, int position) {
        final GifBean.InterestingImg interestingImg = gifList.get(position);
        viewHolder.tv_content.setText(interestingImg.getContent());
        viewHolder.tv_time.setText(interestingImg.getUpdateTime());
        Uri uri = Uri.parse(interestingImg.getUrl());
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setAutoPlayAnimations(true)
                .build();
        viewHolder.iv_gif.setController(controller);
    }

    @Override
    public int getItemCount() {
        return gifList.size();
    }

    static class GifViewHolder extends RecyclerView.ViewHolder {

        TextView tv_content;
        TextView tv_time;
        SimpleDraweeView iv_gif;
        ImageView iv_play;

        public GifViewHolder(View itemView) {
            super(itemView);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            iv_gif = (SimpleDraweeView) itemView.findViewById(R.id.iv_gif);
            iv_play = (ImageView) itemView.findViewById(R.id.iv_play);
        }
    }

}
