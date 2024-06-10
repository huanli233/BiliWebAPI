package com.huanli233.biliapi.api;

import com.huanli233.biliapi.httplib.utils.Cookies;

public class LoginInfo {
	
	private Cookies cookies;
	private LoginInfoChangeListener loginInfoChangeListener;
	
	public Cookies getCookies() {
		if (cookies != null) {
			cookies.setOnChange(() -> loginInfoChangeListener.onLoginInfoChange(this));
		}
		return cookies;
	}
	
	public void setCookies(Cookies cookies) {
		this.cookies = cookies;
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
	
	interface LoginInfoChangeListener {
		void onLoginInfoChange(LoginInfo loginInfo);
	}

}
