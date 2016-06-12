package com.tt.tools.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tt.tools.R;
import com.tt.tools.model.bean.MyJoke;

import java.util.List;

/**
 * <p> FileName： JokeAdapter</p>
 * <p>
 * Description：笑话大全适配器
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 * @CreateDate 2016/2/20
 */
public class JokeAdapter extends RecyclerView.Adapter<JokeAdapter.JokeViewHolder> {

    private Context context;

    private List<MyJoke.Joke> jokeList;

    /**
     * 文字长按事件
     */
    private OnTextLongClickListener onTextLongClickListener;

    public void setOnTextLongClickListener(OnTextLongClickListener onLongClickListener) {
        this.onTextLongClickListener = onLongClickListener;
    }

    public JokeAdapter(Context context, List<MyJoke.Joke> jokeList) {
        this.context = context;
        this.jokeList = jokeList;
    }

    @Override
    public JokeAdapter.JokeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_joke, viewGroup, false);
        JokeViewHolder viewHolder = new JokeViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(JokeViewHolder viewHolder, int position) {
        final MyJoke.Joke joke = jokeList.get(position);
        viewHolder.tv_content.setText(joke.getContent());
        viewHolder.tv_time.setText(joke.getUpdateTime());
        viewHolder.iv_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareByMessage(joke.getContent());
            }
        });
        viewHolder.tv_content.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onTextLongClickListener != null) {
                    onTextLongClickListener.onTextLongClick(joke.getContent());
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return jokeList.size();
    }

    static class JokeViewHolder extends RecyclerView.ViewHolder {

        TextView tv_content;
        TextView tv_time;
        ImageView iv_message;

        public JokeViewHolder(View itemView) {
            super(itemView);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            iv_message = (ImageView) itemView.findViewById(R.id.iv_message);
        }
    }

    /**
     * 发送短信
     *
     * @param msg 短信内容
     */
    public void shareByMessage(String msg) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"));
        intent.putExtra("sms_body", msg);
        context.startActivity(intent);
    }

    public interface OnTextLongClickListener {
        void onTextLongClick(String content);
    }

}
