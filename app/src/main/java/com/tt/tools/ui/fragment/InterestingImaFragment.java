package com.tt.tools.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.tt.tools.R;
import com.tt.tools.common.Config;
import com.tt.tools.common.HttpConfig;
import com.tt.tools.model.bean.GifBean;
import com.tt.tools.model.retrofit.HttpResultFunc;
import com.tt.tools.model.retrofit.RetrofitUtil;
import com.tt.tools.model.retrofit.api.GifServiceApi;
import com.tt.tools.ui.adapter.GifAdapter;
import com.tt.tools.view.recyclerview.MyRecyclerView;
import com.tzj.frame.base.FrameBaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * <p> FileName： InterestingImaFragment</p>
 * <p>
 * Description：趣图Fragment
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 */
public class InterestingImaFragment extends FrameBaseFragment {

    /**
     * 当前页
     */
    private int mPage = 1;

    @Bind(R.id.srl)
    SwipeRefreshLayout srl;
    @Bind(R.id.rv_interesting_img)
    MyRecyclerView rv_interesting_img;

    private GifAdapter gifAdapter;
    private List<GifBean.InterestingImg> mGifList;

    private GifServiceApi gifServiceApi;
    private Subscription subscription;
//    private Call<GifBean> call;

    @Override
    public int getContentViewLayoutId() {
        return R.layout.fragment_interestingimg;
    }

    @Override
    public void initView() {
        rv_interesting_img.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_interesting_img.setOnLoadMoreListener(new LoadMoreListener());
        srl.setColorSchemeResources(R.color.colorPrimary);
        srl.setOnRefreshListener(new SwipeLayoutRefreshListener());
    }

    @Override
    public void initData() {
        mGifList = new ArrayList<>();
        gifAdapter = new GifAdapter(getActivity(), mGifList);
        rv_interesting_img.setAdapter(gifAdapter);
        //SwipeLayout自动刷新
        srl.post(new Runnable() {
            @Override
            public void run() {
                srl.setRefreshing(true);
            }
        });
        gifServiceApi = RetrofitUtil.createApi(GifServiceApi.class);
        findGifList();
    }

    /**
     * 下拉刷新
     */
    final class SwipeLayoutRefreshListener implements SwipeRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh() {
            mPage = 1;
            findGifList();
        }
    }

    /**
     * 自动加载更多
     */
    final class LoadMoreListener implements MyRecyclerView.OnLoadMoreListener {
        @Override
        public void onLoadMore() {
            mPage++;
            findGifList();
        }
    }


    @Override
    public View getVaryView() {
        return null;
    }

    /**
     * 获取最新笑话
     */
    private void findGifList() {
        subscription = gifServiceApi.getGifList(HttpConfig.APIKEY_JOKE, mPage, Config.NUM_PAGE)
                .map(new HttpResultFunc<GifBean>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GifBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
//                        Toast(getActivity().getString(com.tzj.frame.R.string.net_disable));
                        Toast(e.getMessage());
                        srl.setRefreshing(false);
                        rv_interesting_img.setLoadComplete();
                    }

                    @Override
                    public void onNext(GifBean gifBean) {
                        if (null != gifBean) {
                            List<GifBean.InterestingImg> gifList = gifBean.getData();
                            if (mPage == 1) {
                                mGifList.clear();
                            }
                            mGifList.addAll(gifList);
                            gifAdapter.notifyDataSetChanged();
                        }
                        srl.setRefreshing(false);
                        rv_interesting_img.setLoadComplete();
                    }
                });


//        call = gifServiceApi.getGifList(HttpConfig.APIKEY_JOKE, mPage, Config.NUM_PAGE);
//        call.enqueue(new Callback<GifBean>() {
//            @Override
//            public void onResponse(Call<GifBean> call, Response<GifBean> response) {
//                GifBean gifBean = response.body();
//                if (null != gifBean) {
//                    if (gifBean.getErrorCode() == 0) {
//                        List<GifBean.InterestingImg> gifList = gifBean.getResult().getData();
//                        if (mPage == 1) {
//                            mGifList.clear();
//                        }
//                        mGifList.addAll(gifList);
//                        gifAdapter.notifyDataSetChanged();
//                    } else {
//                        Toast(gifBean.getReason());
//                    }
//                }
//                srl.setRefreshing(false);
//                rv_interesting_img.setLoadComplete();
//            }
//
//            @Override
//            public void onFailure(Call<GifBean> call, Throwable t) {
//                Toast(getActivity().getString(com.tzj.frame.R.string.net_disable));
//                srl.setRefreshing(false);
//                rv_interesting_img.setLoadComplete();
//            }
//        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        subscription.unsubscribe();
    }
}
