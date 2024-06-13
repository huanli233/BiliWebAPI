package com.huanli233.biliapi.api.logininfo;

import com.huanli233.biliapi.BiliBiliAPI;
import com.huanli233.biliapi.api.base.Base;
import com.huanli233.biliapi.api.base.BaseResponse;
import com.huanli233.biliapi.api.interfaces.ILoginInfoApi;
import com.huanli233.biliapi.httplib.utils.Cookies;

import lombok.Getter;
import retrofit2.Call;

public class LoginInfo extends Base {
	
	public static final String LOGIN_SOURCE_MAIN_WEB = "main_web";
	public static final String LOGIN_SOURCE_MAIN_MINI = "main_mini";
	
	private Cookies cookies;
	private @Getter String refreshToken;
	private LoginInfoChangeListener loginInfoChangeListener;
	private final Runnable onCookieChange = () -> {
		if (loginInfoChangeListener != null) {
			loginInfoChangeListener.onLoginInfoChange(this);
		}
	};
	
	public Cookies getCookies() {
		if (cookies == null) {
			cookies = new Cookies("");
		}
		cookies.setOnChange(onCookieChange);
		return cookies;
	}
	
	public void setCookies(Cookies cookies) {
		this.cookies = cookies;
		if (loginInfoChangeListener != null) {
			loginInfoChangeListener.onLoginInfoChange(this);
		}
	}
	
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
		if (loginInfoChangeListener != null) {
			loginInfoChangeListener.onLoginInfoChange(this);
		}
	}
	
	public void setLoginInfoChangeListener(LoginInfoChangeListener listener) {
		this.loginInfoChangeListener = listener;
	}
	
	public long getMid(long defaultValue) {
		try {
			return Long.valueOf(cookies.get("DedeUserID"));
		} catch (Throwable ignored) {
			return defaultValue;
		}
	}
	
	public String getCsrf() {
		return cookies.getOrDefault("bili_jct", "");
	}
	
	public static Call<BaseResponse<NavInfo>> navInfo() {
		return BiliBiliAPI.getInstance().getApi(ILoginInfoApi.class).requestNavInfo();
	}
	
	public static Call<BaseResponse<NavStat>> navStat() {
		return BiliBiliAPI.getInstance().getApi(ILoginInfoApi.class).requestNavStat();
	}
	
	public static Call<BaseResponse<CoinCount>> coinCount() {
		return BiliBiliAPI.getInstance().getApi(ILoginInfoApi.class).requestCoinCount();
	}
	
	public interface LoginInfoChangeListener {
		void onLoginInfoChange(LoginInfo loginInfo);
	}

}
