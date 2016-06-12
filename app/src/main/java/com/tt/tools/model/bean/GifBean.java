package com.tt.tools.model.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * <p> FileName： GifBean</p>
 * <p>
 * Description：趣图
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 * @createdate 2016-03-10 9:30
 */
public class GifBean extends BaseBean {

    private List<InterestingImg> data;

    public List<InterestingImg> getData() {
        return data;
    }

    public void setData(List<InterestingImg> data) {
        this.data = data;
    }


    public static class InterestingImg {

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

        /**
         * gif 地址
         */
        private String url;

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

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
