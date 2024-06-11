package com.huanli233.biliapi.gson;

import com.google.gson.GsonBuilder;

public class GsonUtil {
	
	public static GsonBuilder builder() {
		return new GsonBuilder()
				.excludeFieldsWithoutExposeAnnotation()
				.setLenient();
	}
	
}
