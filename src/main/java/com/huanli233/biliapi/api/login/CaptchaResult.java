package com.huanli233.biliapi.api.login;

import com.google.gson.annotations.Expose;

import lombok.Data;

@Data
public class CaptchaResult {
	@Expose private String validate;
	@Expose private String seccode;
}
