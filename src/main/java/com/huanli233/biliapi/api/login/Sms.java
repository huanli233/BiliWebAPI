package com.huanli233.biliapi.api.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.huanli233.biliapi.BiliBiliAPI;
import com.huanli233.biliapi.api.base.BaseResponse;
import com.huanli233.biliapi.api.interfaces.ILoginApi;

import lombok.Data;
import retrofit2.Call;

public class Sms {
	@Data
	public static class Token {
		@SerializedName("captcha_key")
		@Expose private String captchaKey;
		
		public Call<BaseResponse<LoginResult>> login(Country country, long phoneNum, int code) {
			return BiliBiliAPI.getInstance().getApi(ILoginApi.class).smsLogin(country.getId(), phoneNum, code, LoginInfo.LOGIN_SOURCE_MAIN_WEB, getCaptchaKey(), "https://bilibili.com");
		}
	}
	
	@Data
	public static class LoginResult {
		@SerializedName("is_new")
		@Expose private boolean isNew;
		@Expose private int status;
		@Expose private String url;
	}
	
	public static Call<BaseResponse<Token>> sendCode(Country country, long phoneNum, Captcha captcha, CaptchaResult captchaResult) {
		return BiliBiliAPI.getInstance().getApi(ILoginApi.class).sendSms(country.getId(), phoneNum, LoginInfo.LOGIN_SOURCE_MAIN_WEB, captcha.getToken(), captcha.getGeetest().getChallenge(), captchaResult.getValidate(), captchaResult.getSeccode());
	}
}
