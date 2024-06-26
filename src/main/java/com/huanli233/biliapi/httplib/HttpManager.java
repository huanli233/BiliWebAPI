package com.huanli233.biliapi.httplib;

import com.huanli233.biliapi.BiliBiliAPI;
import com.huanli233.biliapi.gson.GsonUtil;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpManager {
	
	private static HttpManager INSTANCE;
	public static HttpManager getInstance() {
		if (INSTANCE == null) {
			synchronized (HttpManager.class) {
				INSTANCE = new HttpManager();
			}
		}
		return INSTANCE;
	}
	
	private OkHttpClient mHttpClient;
	private Retrofit mRetrofit;
	private HttpManager() {
		this.mHttpClient = createHttpClient().build();
	}
	private OkHttpClient.Builder createHttpClient() {
		return new OkHttpClient.Builder()
				.addInterceptor(new HttpRequestInterceptor())
				.addInterceptor(new HttpResponseInterceptor());
	}
	private Retrofit getRetrofit() {
		if (this.mRetrofit == null) {
			this.mRetrofit = new Retrofit.Builder()
					.addConverterFactory(GsonConverterFactory.create(GsonUtil.builder().create()))
					.client(mHttpClient)
					.baseUrl(HttpConstants.Protocols.HTTPS + BiliBiliAPI.BASE_API_URL)
					.build();
		}
		return this.mRetrofit;
	}
	public <T> T createApi(Class<T> clazz) {
		return (T) getRetrofit().create(clazz);
	}
	public OkHttpClient getHttpClient() {
		return this.mHttpClient;
	}

}