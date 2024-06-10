package com.huanli233.biliapi.httplib;

import java.io.IOException;

import com.huanli233.biliapi.BiliBiliAPI;
import com.huanli233.biliapi.httplib.utils.Cookies;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HttpRequestInterceptor implements Interceptor {

	public Response intercept(Chain chain) throws IOException { 
		return chain.proceed(handleCookies(chain).build());
	}
	
	private Request.Builder handleCookies(Chain chain) {
		Request request = chain.request();
		Cookies cookies = BiliBiliAPI.getInstance().getLoginInfo().getCookies();
		return request.newBuilder()
				.header("Cookies", cookies.toString());
	}

}
