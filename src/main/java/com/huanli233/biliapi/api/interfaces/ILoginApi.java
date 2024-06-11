package com.huanli233.biliapi.api.interfaces;

import com.huanli233.biliapi.BiliBiliAPI;
import com.huanli233.biliapi.api.base.BaseResponse;
import com.huanli233.biliapi.api.login.Captcha;
import com.huanli233.biliapi.api.login.CountryList;
import com.huanli233.biliapi.api.login.Password;
import com.huanli233.biliapi.api.login.QrCode;
import com.huanli233.biliapi.api.login.Sms;
import com.huanli233.biliapi.httplib.annotations.API;
import com.huanli233.biliapi.httplib.annotations.Queries;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

@API(BiliBiliAPI.PASSPORT_URL)
public interface ILoginApi {
	@GET("/x/passport-login/captcha")
	@Queries(keys = {"source"}, values = {"main_web"})
	Call<BaseResponse<Captcha>> requestCaptcha();
	
	@GET("/x/passport-login/web/qrcode/generate")
	Call<BaseResponse<QrCode>> requestQrCode();
	
	@GET("/x/passport-login/web/qrcode/poll")
	Call<BaseResponse<QrCode.LoginResult>> qrCodeLogin(@Query("qrcode_key") String qrcodeKey);
	
	@GET("/web/generic/country/list")
	Call<BaseResponse<CountryList>> getCountryList();
	
	@POST("/x/passport-login/web/sms/send")
	@FormUrlEncoded
	Call<BaseResponse<Sms.Token>> sendSms(@Field("cid") int cid, @Field("tel") long phoneNum, 
			@Field("source") String loginSource, @Field("token") String token, 
			@Field("challenge") String challenge, @Field("validate") String validate, 
			@Field("seccode") String seccode);
	
	@POST("/x/passport-login/web/login/sms")
	@FormUrlEncoded
	Call<BaseResponse<Sms.LoginResult>> smsLogin(@Field("cid") int cid, @Field("tel") long phoneNum, 
			@Field("code") int code, @Field("source") String loginSource, 
			@Field("captcha_key") String token, @Field("go_url") String goUrl);
	
	@GET("/x/passport-login/web/key")
	Call<BaseResponse<Password.KeyAndHash>> getKeyAndHash();
	
	@POST("/x/passport-login/web/login")
	@FormUrlEncoded
	Call<BaseResponse<Password.LoginResult>> passwordLogin(@Field("username") String username, @Field("password") String password, 
			@Field("keep") int keep, @Field("token") String token, 
			@Field("challenge") String challenge, @Field("validate") String validate, 
			@Field("seccode") String seccode, @Field("source") String loginSource, @Field("go_url") String goUrl);
}
