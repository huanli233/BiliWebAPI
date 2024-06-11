package com.huanli233.biliapi.api.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

public class Sms {
	@Data
	public static class Token {
		@SerializedName("captcha_key")
		@Expose String captchaKey;
	}
	
	@Data
	public static class LoginResult {
		@SerializedName("is_new")
		@Expose boolean isNew;
		@Expose int status;
		@Expose String url;
	}
}
