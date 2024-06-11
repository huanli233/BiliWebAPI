package com.huanli233.biliapi.httplib.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class FormData {
    private final Map<String, String> data;
    private boolean isUrlParam;

    public FormData() {
        data = new HashMap<>();
    }

    public FormData remove(String key) {
        data.remove(key);
        return this;
    }

    public FormData put(String key, Object value) {
        data.put(key, String.valueOf(value));
        return this;
    }

    public FormData setUrlParam(boolean isUrlParam) {
        this.isUrlParam = isUrlParam;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (isUrlParam) sb.append("?");

        try {
            for (String key : data.keySet()) {
                if (sb.length() > 0) {
                    sb.append("&");
                }
                sb.append(URLEncoder.encode(key, "UTF-8"));
                sb.append("=");
                sb.append(URLEncoder.encode(data.get(key), "UTF-8"));
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        return sb.toString();
    }
}