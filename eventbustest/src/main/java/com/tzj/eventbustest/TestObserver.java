package com.tzj.eventbustest;

/**
 * <p> FileName： TestObserver</p>
 * <p>
 * Description：
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 * @CreateDate 2016/2/28
 */
public class TestObserver {

    private String msg;

    public TestObserver(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
