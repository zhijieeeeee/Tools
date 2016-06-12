package com.tzj.frame.volleyhttp;

import java.util.HashMap;
import java.util.Map;

/**
 * <p> FileName： RequestParam</p>
 * <p>
 * Description：自定义的请求参数类
 * </p>
 *
 * @author tangzhijie
 * @version 1.0
 * @createdate 2016-01-12 11:32
 */
public class RequestParam {

    private Map<String, String> map;

    public RequestParam() {
        map = new HashMap<>();
    }

    public <T> void setBodyParam(String key, T value) {
        if (map != null) {
            map.put(key, value.toString());
        }
    }

    public Map<String, String> getMap() {
        return map;
    }
}
