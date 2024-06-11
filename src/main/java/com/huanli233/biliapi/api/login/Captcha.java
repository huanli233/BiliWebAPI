package com.huanli233.biliapi.api.login;

import com.google.gson.annotations.Expose;

import lombok.Data;

@Data
public class Captcha {
	@Expose String type;
	@Expose String token;
	@Expose Geetest geetest;
	
	@Data
	public static class Geetest {
		@Expose String challenge;
		@Expose String gt;
	}
}
