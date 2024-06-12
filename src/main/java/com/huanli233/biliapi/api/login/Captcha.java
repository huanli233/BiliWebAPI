package com.huanli233.biliapi.api.login;

import com.google.gson.annotations.Expose;
import com.huanli233.biliapi.BiliBiliAPI;
import com.huanli233.biliapi.api.base.BaseResponse;
import com.huanli233.biliapi.api.interfaces.ILoginApi;

import lombok.Data;
import retrofit2.Call;

@Data
public class Captcha {
	@Expose private String type;
	@Expose private String token;
	@Expose private Geetest geetest;
	
	@Data
	public static class Geetest {
		@Expose private String challenge;
		@Expose private String gt;
	}
	
	public static Call<BaseResponse<Captcha>> getCaptcha() {
		return BiliBiliAPI.getInstance().getApi(ILoginApi.class).requestCaptcha();
	}
}
