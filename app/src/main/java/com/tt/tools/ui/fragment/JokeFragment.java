package com.tt.tools.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.ClipboardManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tt.tools.R;
import com.tt.tools.common.Config;
import com.tt.tools.common.HttpConfig;
import com.tt.tools.model.bean.MyJoke;
import com.tt.tools.model.retrofit.HttpResult;
import com.tt.tools.model.retrofit.HttpResultFunc;
import com.tt.tools.model.retrofit.RetrofitUtil;
import com.tt.tools.model.retrofit.api.JokeServiceApi;
import com.tt.tools.ui.adapter.JokeAdapter;
import com.tt.tools.util.SharePreferenceUtil;
import com.tt.tools.view.recyclerview.MyRecyclerView;
import com.tzj.frame.base.FrameBaseFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * <p> FileName： JokeFragment</p>
 * <p>
 * Description：笑话大全fragment
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 * @createdate 2016-02-18 11:50
 */
public class JokeFragment extends FrameBaseFragment {


    @Bind(R.id.rv_joke)
    MyRecyclerView rvJoke;
    @Bind(R.id.srl)
    SwipeRefreshLayout srl;
    @Bind(R.id.fab_top)
    FloatingActionButton fabTop;
    /**
     * 当前页
     */
    private int mPage = 1;

    private JokeAdapter jokeAdapter;
    private List<MyJoke.Joke> mJokeList;

    /**
     * 剪贴板
     */
    private ClipboardManager myClipboard;

    //Retrofit学习
    private JokeServiceApi jokeServiceApi;
    private Subscription subscription;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.fragment_joke;
    }

    @Override
    public void initView() {
        myClipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        fabTop.hide();
        rvJoke.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvJoke.setOnLoadMoreListener(new LoadMoreListener());
        rvJoke.setOnDragListener(new RecyclerViewDragListener());
        srl.setColorSchemeResources(R.color.colorPrimary);
        srl.setOnRefreshListener(new SwipeLayoutRefreshListener());
        //设置RecyclerView的左滑删除列表项
//        initRecyclerView();
    }

    /**
     * 下拉刷新
     */
    final class SwipeLayoutRefreshListener implements SwipeRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh() {
            mPage = 1;
            findJokeList();
        }
    }

    /**
     * 自动加载更多
     */
    final class LoadMoreListener implements MyRecyclerView.OnLoadMoreListener {
        @Override
        public void onLoadMore() {
            mPage++;
            findJokeList();
        }
    }

    final class RecyclerViewDragListener implements MyRecyclerView.OnDragListener {
        @Override
        public void onDragUp() {
            fabTop.hide();
        }

        @Override
        public void onDragDown() {
            fabTop.show();
        }
    }

    final class JokeLongClickListener implements JokeAdapter.OnTextLongClickListener {
        @Override
        public void onTextLongClick(String content) {
            myClipboard.setText(content);
            Snackbar.make(srl, "已复制到剪贴板", Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void initData() {
        mJokeList = new ArrayList<>();
        jokeAdapter = new JokeAdapter(getActivity(), mJokeList);
        rvJoke.setAdapter(jokeAdapter);
        jokeAdapter.setOnTextLongClickListener(new JokeLongClickListener());
        //SwipeLayout自动刷新
        srl.post(new Runnable() {
            @Override
            public void run() {
                srl.setRefreshing(true);
            }
        });
        mPage = SharePreferenceUtil.getPage(getActivity());
        jokeServiceApi = RetrofitUtil.createApi(JokeServiceApi.class);
        findJokeList();
    }

    @Override
    public View getVaryView() {
        return null;
    }

    /**
     * 获取最新笑话
     */
    private void findJokeList() {
        Map<String, String> requestParam = new HashMap<>();
        requestParam.put("key", HttpConfig.APIKEY_JOKE);
        requestParam.put("page", mPage + "");
        requestParam.put("pagesize", Config.NUM_PAGE + "");
        subscription = jokeServiceApi.getJokeList(requestParam)
                .map(new HttpResultFunc<MyJoke>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MyJoke>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast(e.getMessage());
                        srl.setRefreshing(false);
                        rvJoke.setLoadComplete();
                    }

                    @Override
                    public void onNext(MyJoke myJoke) {
                        if (null != myJoke) {
                            if (mPage == 1) {
                                mJokeList.clear();
                            }
                            mJokeList.addAll(myJoke.getData());
                            jokeAdapter.notifyDataSetChanged();
                            //保存最新的页数
                            SharePreferenceUtil.savePage(getActivity(), mPage);
                        }
                        srl.setRefreshing(false);
                        rvJoke.setLoadComplete();
                    }
                });
//        Call<MyJoke> call = jokeServiceApi.getJokeList(requestParam);
//        call.enqueue(new Callback<MyJoke>() {
//            @Override
//            public void onResponse(Call<MyJoke> call, Response<MyJoke> response) {
//                MyJoke myJoke = response.body();
//                if (null != myJoke) {
//                    if (myJoke.getErrorCode() == 0) {//请求成功
//                        if (mPage == 1) {
//                            mJokeList.clear();
//                        }
//                        mJokeList.addAll(myJoke.getResult().getData());
//                        jokeAdapter.notifyDataSetChanged();
//                        //保存最新的页数
//                        SharePreferenceUtil.savePage(getActivity(), mPage);
//                    } else {//请求失败
//                        Toast(myJoke.getReason());
//                    }
//                } else {
//                    Toast(response.message());
//                }
//                srl.setRefreshing(false);
//                rvJoke.setLoadComplete();
//            }
//
//            @Override
//            public void onFailure(Call<MyJoke> call, Throwable t) {
//                Toast(getActivity().getString(com.tzj.frame.R.string.net_disable));
//                srl.setRefreshing(false);
//                rvJoke.setLoadComplete();
//            }
//        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        subscription.unsubscribe();
    }

    /**
     * 设置RecyclerView的左滑删除列表项
     */
    private void initRecyclerView() {
        ItemTouchHelper.Callback callBack = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                //获得操作的列表项position
                int position = viewHolder.getAdapterPosition();
                mJokeList.remove(position);
                jokeAdapter.notifyItemRemoved(position);

                //解决用户把item都滑动删除掉导致的无法加载下一页
                if (mJokeList.size() < 3) {
                    mPage++;
                    //SwipeLayout自动刷新
                    srl.post(new Runnable() {
                        @Override
                        public void run() {
                            srl.setRefreshing(true);
                        }
                    });
                    findJokeList();
                }
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callBack);
//        itemTouchHelper.attachToRecyclerView(rv_joke);
    }


    @OnClick({R.id.fab_top})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_top://点击回到顶部
                rvJoke.scrollToPosition(0);
                fabTop.hide();
                break;
            default:
                break;
        }
    }

}
