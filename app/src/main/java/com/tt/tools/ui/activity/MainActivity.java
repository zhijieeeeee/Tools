package com.tt.tools.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.tt.tools.R;
import com.tt.tools.ui.dialog.ShareDialog;
import com.tt.tools.ui.fragment.InterestingImaFragment;
import com.tt.tools.ui.fragment.JokeFragment;
import com.tt.tools.ui.fragment.RobotFragment;
import com.tzj.frame.base.FrameBaseActivity;
import com.tzj.frame.util.FunctionUtil;
import com.tzj.frame.util.MyLog;
import com.tzj.frame.util.MyToast;
import com.umeng.update.UmengUpdateAgent;

import butterknife.Bind;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * <p> FileName： MainActivity</p>
 * <p>
 * Description：程序主页
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 * @CreateDate 2016/2/20
 */
public class MainActivity extends FrameBaseActivity implements View.OnClickListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.nv_navigation)
    NavigationView nvNavigation;
    @Bind(R.id.dl_main_drawer)
    DrawerLayout dlMainDrawer;

    /**
     * 抽屉头部View
     */
    private View headerView;

    /**
     * 头像
     */
    private SimpleDraweeView iv_photo;

    /**
     * 分享按钮
     */
    private ImageView iv_share;

    private ShareDialog shareDialog;

    private JokeFragment jokeFragment;

    private InterestingImaFragment gifFragment;

    private RobotFragment robotFragment;

    private FragmentManager fragmentManager;

    private long mExitTime;

    @Override
    public void before(Bundle savedInstanceState) {
        fragmentManager = getSupportFragmentManager();
        if (savedInstanceState != null) {
            jokeFragment = (JokeFragment) fragmentManager.findFragmentByTag("JokeFragment");
            gifFragment = (InterestingImaFragment) fragmentManager.findFragmentByTag("InterestingImaFragment");
            robotFragment = (RobotFragment) fragmentManager.findFragmentByTag("RobotFragment");
        }
    }

    @Override
    public int getContentViewLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        //友盟自动更新
        UmengUpdateAgent.update(this);
        toolbar.setTitle(R.string.joke);
        setSupportActionBar(toolbar);
        headerView = nvNavigation.getHeaderView(0);
        iv_photo = getView(headerView, R.id.iv_photo);
        iv_share = getView(headerView, R.id.iv_share);
        iv_share.setOnClickListener(this);
        //抽屉菜单项点击事件
        nvNavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                selectFragmentByIndex(item.getItemId());
                toolbar.setTitle(item.getTitle());
                dlMainDrawer.closeDrawers();
                return true;
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlMainDrawer.openDrawer(Gravity.LEFT);
                FunctionUtil.hideSoftKeyboard(toolbar, MainActivity.this);
            }
        });
        //开始默认选中第一个
        selectFragmentByIndex(R.id.nav_joke);
        shareDialog = new ShareDialog(this);
//        setTranslucentStatus(true);
//        SystemBarTintManager tintManager = new SystemBarTintManager(this);
//        tintManager.setStatusBarTintEnabled(true);
//        tintManager.setStatusBarTintResource(R.color.colorPrimary);//通知栏所需颜色


        //Rxjava学习
        //compile 'io.reactivex:rxjava:1.1.0'
        //compile 'io.reactivex:rxandroid:1.1.0'

        //观察者Subscriber或者Observer
        Subscriber<String> observer = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                MyLog.i(s);
            }
        };

        //被观察者
        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("RxJava");
            }
        });
        //订阅
        observable.subscribe(observer);

        ////////连写
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("连写");
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                MyLog.i(s);
            }
        });

        //不完整定义的回调
        Action1<String> onNextAction = new Action1<String>() {
            @Override
            public void call(String s) {

            }
        };

        Action1<Throwable> onErrorAction = new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        };

        Action0 onCompleteAction = new Action0() {
            @Override
            public void call() {

            }
        };

//        observable.subscribe(onNextAction, onErrorAction, onCompleteAction);

        //例子
        //observeOn() 指定的是它之后的操作所在的线程
        //subscribeOn() 的位置放在哪里都可以，但它是只能调用一次的。
        //当使用了多个 subscribeOn() 的时候，只有第一个 subscribeOn() 起作用。
        String[] name = {"tom", "jack", "luna"};
        Observable.from(name)
                .subscribeOn(Schedulers.io())//指定subscribe发生在io线程（子线程）
                .observeOn(AndroidSchedulers.mainThread())//指定观察者的回调在Android主线程
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
//                        MyToast.show(MainActivity.this, s);
                    }
                });


        //.map 转换
        //简便写法
        Observable.just(1)
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        return integer + "sss";
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        MyLog.i(s);
                    }
                });

        //完整写法
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(22);
            }
        }).map(new Func1<Integer, String>() {
            @Override
            public String call(Integer integer) {
                return integer + "ssssss";
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                MyLog.i(s);
            }
        });

    }

    @Override
    public void initData() {
        Uri uri = Uri.parse("http://img1.imgtn.bdimg.com/it/u=3606430639,476895012&fm=21&gp=0.jpg");
        iv_photo.setImageURI(uri);
    }

    @Override
    public View getVaryView() {
        return null;
    }

    @Override
    protected void onBuildVersionGT_LOLLIPOP(SystemBarTintManager.SystemBarConfig systemBarConfig) {
        setKitkatStatusBar(toolbar, systemBarConfig);
        setKitkatStatusBar(headerView, systemBarConfig);
    }

    /**
     * 选中fragment
     *
     * @param menuId 菜单项id
     */
    private void selectFragmentByIndex(int menuId) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        hideFragment(fragmentTransaction);
        switch (menuId) {
            case R.id.nav_joke://笑话
                if (jokeFragment == null) {
                    jokeFragment = new JokeFragment();
                    fragmentTransaction.add(R.id.rl_drawer_content, jokeFragment, "JokeFragment");
                } else {
                    fragmentTransaction.show(jokeFragment);
                }
                break;
            case R.id.nav_gif://趣图
                if (gifFragment == null) {
                    gifFragment = new InterestingImaFragment();
                    fragmentTransaction.add(R.id.rl_drawer_content, gifFragment, "InterestingImaFragment");
                } else {
                    fragmentTransaction.show(gifFragment);
                }
                break;
            case R.id.nav_robot://和机器人对话
                if (robotFragment == null) {
                    robotFragment = new RobotFragment();
                    fragmentTransaction.add(R.id.rl_drawer_content, robotFragment, "RobotFragment");
                } else {
                    fragmentTransaction.show(robotFragment);
                }
                break;
            default:
                break;
        }
        fragmentTransaction.commit();
    }

    /**
     * 隐藏所有fragment
     *
     * @param fragmentTransaction
     */
    private void hideFragment(FragmentTransaction fragmentTransaction) {
        if (jokeFragment != null) {
            fragmentTransaction.hide(jokeFragment);
        }
        if (gifFragment != null) {
            fragmentTransaction.hide(gifFragment);
        }
        if (robotFragment != null) {
            fragmentTransaction.hide(robotFragment);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {//关于
            startActivity(new Intent(this, AboutActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - mExitTime) > 1500) {
            Toast("再按一次退出程序");
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_share://分享
                shareDialog.show();
                dlMainDrawer.closeDrawers();
                break;
            default:
                break;
        }
    }

    public void setKitkatStatusBar(View v, SystemBarTintManager.SystemBarConfig systemBarConfig) {
        if (systemBarConfig != null) {
            ViewGroup.LayoutParams params = v.getLayoutParams();
            params.height = systemBarConfig.getStatusBarHeight() + params.height;
            v.setLayoutParams(params);
            v.setPadding(0, systemBarConfig.getStatusBarHeight(), 0, 0);
        }
    }
}
