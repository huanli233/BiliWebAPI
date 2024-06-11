package com.huanli233.biliapi.api.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class QrCode {
	@Expose String url;
	@SerializedName("qrcode_key")
	@Expose String qrcodeKey;
	
	@Data
	public static class LoginResult {
		@Expose int code;
		@Expose String message;
		@Expose String url;
		@SerializedName("refresh_token")
		@Expose String refreshToken;
		@Expose long timestamp;
	}
}
