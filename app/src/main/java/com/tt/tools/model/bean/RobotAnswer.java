package com.tt.tools.model.bean;

/**
 * <p> FileName： RobotAnswer</p>
 * <p>
 * Description：机器人问答
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 * @createdate 2016-03-10 9:50
 */
public class RobotAnswer extends BaseBean {

    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public static class Result {
        private int code;
        private String text;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
