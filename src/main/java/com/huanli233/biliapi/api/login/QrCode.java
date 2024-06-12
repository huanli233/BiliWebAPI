package com.huanli233.biliapi.api.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.huanli233.biliapi.BiliBiliAPI;
import com.huanli233.biliapi.api.base.BaseResponse;
import com.huanli233.biliapi.api.interfaces.ILoginApi;

import lombok.Data;
import retrofit2.Call;

@Data
public class QrCode {
	@Expose private String url;
	@SerializedName("qrcode_key")
	@Expose private String qrcodeKey;
	
	@Data
	public static class LoginResult {
		@Expose private int code;
		@Expose private String message;
		@Expose private String url;
		@SerializedName("refresh_token")
		@Expose private String refreshToken;
		@Expose private long timestamp;
	}
	
	public static Call<BaseResponse<QrCode>> generate() {
		return BiliBiliAPI.getInstance().getApi(ILoginApi.class).requestQrCode();
	}
	
	public Call<BaseResponse<LoginResult>> poll() {
		return BiliBiliAPI.getInstance().getApi(ILoginApi.class).qrCodeLogin(getQrcodeKey());
	}
}
