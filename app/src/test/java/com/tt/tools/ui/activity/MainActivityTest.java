package com.tt.tools.ui.activity;

import com.tt.tools.BuildConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * <p> FileName： MainActivityTest</p>
 * <p>
 * Description：
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 * @createdate 2016-03-02 10:08
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class MainActivityTest {

//    private MainActivity mMainActivity;

    @Before
    public void setUp() throws Exception {
        // 获取待测Activity
//        mMainActivity = Robolectric.buildActivity(MainActivity.class).create().resume().get();
    }

    // 测试界面初始化结果
    @Test
    public void testInit() throws Exception {
        final MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        assertNotNull(activity);
        assertEquals(4, 2 + 2);
        // 判断包名
//        assertEquals("com.tt.tools", mMainActivity.getPackageName());

//        // 判断textView默认显示的内容
//        assertEquals("Hello world!", textView.getText().toString());
    }
}
