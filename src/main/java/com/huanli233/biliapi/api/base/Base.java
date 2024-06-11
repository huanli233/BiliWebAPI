package com.huanli233.biliapi.api.base;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.huanli233.biliapi.httplib.annotations.RequestParam;

public class Base {
	
	public Map<String, Object> asMap() {
		Map<String, Object> map = new HashMap<>();
		Field[] fields = this.getClass().getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			if (field.getAnnotation(RequestParam.class) != null) {
				Object fieldValue;
				try {
					fieldValue = field.get(this);
					map.put(field.getName(), String.valueOf(fieldValue));
				} catch (IllegalArgumentException|IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			}
		}
		return map;
	}
	
}
