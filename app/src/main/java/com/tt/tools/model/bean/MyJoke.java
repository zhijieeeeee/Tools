package com.tt.tools.model.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * <p> FileName： MyJoke</p>
 * <p>
 * Description：笑话
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 * @CreateDate 2016/3/9
 */
public class MyJoke {

    private List<Joke> data;

    public List<Joke> getData() {
        return data;
    }

    public void setData(List<Joke> data) {
        this.data = data;
    }

    public static class Joke {

        /**
         * id
         */
        private String hashId;

        /**
         * 内容
         */
        private String content;

        /**
         * 更新时间
         */
        @SerializedName("updatetime")
        private String updateTime;

        public String getHashId() {
            return hashId;
        }

        public void setHashId(String hashId) {
            this.hashId = hashId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }
}
