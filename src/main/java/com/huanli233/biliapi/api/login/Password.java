package com.huanli233.biliapi.api.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.huanli233.biliapi.BiliBiliAPI;
import com.huanli233.biliapi.api.base.BaseResponse;
import com.huanli233.biliapi.api.interfaces.ILoginApi;
import com.huanli233.biliapi.api.logininfo.LoginInfo;

import lombok.Data;
import retrofit2.Call;

public class Password {
	@Data
	public static class KeyAndHash {
		@Expose private String hash;
		@Expose private String key;
	}
	@Data
	public static class LoginResult {
		@Expose private String message;
		@Expose private String url;
		@SerializedName("refresh_token")
		@Expose private String refreshToken;
		@Expose private long timestamp;
	}
	
	public static Call<BaseResponse<KeyAndHash>> getKeyAndHash() {
		return BiliBiliAPI.getInstance().getApi(ILoginApi.class).getKeyAndHash();
	}
	
	public static Call<BaseResponse<LoginResult>> login(String username, String encryptedPassword, Captcha captcha, CaptchaResult captchaResult) {
		return BiliBiliAPI.getInstance().getApi(ILoginApi.class).passwordLogin(username, encryptedPassword, 0, captcha.getToken(), captcha.getGeetest().getChallenge(), captchaResult.getValidate(), captchaResult.getSeccode(), LoginInfo.LOGIN_SOURCE_MAIN_WEB, "https://www.bilibili.com");
	}
}
