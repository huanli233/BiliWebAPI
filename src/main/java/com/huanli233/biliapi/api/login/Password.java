package com.huanli233.biliapi.api.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

public class Password {
	@Data
	public static class KeyAndHash {
		@Expose String hash;
		@Expose String key;
	}
	@Data
	public static class LoginResult {
		@Expose String message;
		@Expose String url;
		@SerializedName("refresh_token")
		@Expose String refreshToken;
		@Expose long timestamp;
	}
}
