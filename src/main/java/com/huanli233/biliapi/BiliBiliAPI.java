package com.huanli233.biliapi;

import java.util.HashMap;

import com.huanli233.biliapi.api.LoginInfo;
import com.huanli233.biliapi.httplib.HttpManager;

public class BiliBiliAPI {
	public static final String BASE_API_URL = "https://api.bilibili.com";
	
	private static BiliBiliAPI INSTANCE;
	public static BiliBiliAPI getInstance() {
		if (INSTANCE == null) {
			synchronized (BiliBiliAPI.class) {
				INSTANCE = new BiliBiliAPI();
			}
		}
		return INSTANCE;
	}
	
	private LoginInfo loginInfo;
	public void setLoginInfo(LoginInfo loginInfo) {
		this.loginInfo = loginInfo;
	}
	public LoginInfo getLoginInfo() {
		return this.loginInfo;
	}

	private HashMap<Class<?>, Object> apiObjectMap = new HashMap<>();
	@SuppressWarnings("unchecked")
	public <T> T getApi(Class<T> clazz) {
		if (apiObjectMap.get(clazz) == null) {
			return HttpManager.getInstance().createApi(clazz);
		} else {
			return (T) apiObjectMap.get(clazz);
		}
	}
}
