package com.huanli233.biliapi.httplib.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Cookies {
	private String firstKey;
    private final Map<String, String> cookieMap = new HashMap<>();
    private Runnable onChange;

    public Cookies(String cookieString) {
        parseCookieString(cookieString);
    }

    private void parseCookieString(String cookieString) {
        cookieMap.clear();
        String[] cookies = cookieString.split("; ");
        boolean isFirstKey = true;
        for (String cookie : cookies) {
            String[] parts = cookie.split("=");
            if (parts.length == 2) {
                cookieMap.put(parts[0], parts[1]);
                if (isFirstKey) {
					firstKey = parts[0];
					isFirstKey = false;
				}
            }
        }
    }
    
    public Set<String> keySet() {
    	return cookieMap.keySet();
    }

    public void set(String key, String value) {
        cookieMap.put(key, value);
        if (onChange != null) {
			onChange.run();
		}
    }

    public String get(String key) {
        return cookieMap.get(key);
    }

    public String getOrDefault(String key, String defaultVal) {
        String val = cookieMap.get(key);
        return val != null ? val : defaultVal;
    }

    public boolean containsKey(String key) {
        return cookieMap.containsKey(key);
    }
    
    public String getFirstKey() {
    	return this.firstKey;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : cookieMap.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("; ");
        }
        return sb.toString();
    }
    
    public void setOnChange(Runnable runnable) {
    	if (this.onChange == null) {
    		this.onChange = runnable;
		}
    }

}
