package com.huanli233.biliapi;

import java.util.HashMap;

import com.huanli233.biliapi.api.logininfo.LoginInfo;
import com.huanli233.biliapi.api.sign.wbi.DefaultWbiSignDataManager;
import com.huanli233.biliapi.api.sign.wbi.WbiSignDataManager;
import com.huanli233.biliapi.httplib.HttpManager;

import lombok.Getter;
import lombok.Setter;

public class BiliBiliAPI {
	public static final String BASE_API_URL = "api.bilibili.com";
	public static final String PASSPORT_URL = "passport.bilibili.com";
	public static final String VC_API_URL = "api.vc.bilibili.com";
	public static final String ACCOUNT_URL = "account.bilibili.com";
	
	private static BiliBiliAPI INSTANCE;
	public static BiliBiliAPI getInstance() {
		if (INSTANCE == null) {
			synchronized (BiliBiliAPI.class) {
				INSTANCE = new BiliBiliAPI();
			}
		}
		return INSTANCE;
	}
	
	@Getter @Setter
	private RequestParamGenerateErrorHandler requestParamGenerateErrorHandler;
	@Getter @Setter
	private WbiSignDataManager wbiSignDataManager = new DefaultWbiSignDataManager();
	@Setter
	private LoginInfo loginInfo;
	public LoginInfo getLoginInfo() {
		if (this.loginInfo == null) {
			this.loginInfo = new LoginInfo();
		}
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
	
	public interface RequestParamGenerateErrorHandler {
		void handleError(Throwable throwable);
	}
}
