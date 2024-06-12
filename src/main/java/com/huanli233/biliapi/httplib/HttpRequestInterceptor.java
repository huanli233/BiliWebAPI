package com.huanli233.biliapi.httplib;

import java.io.IOException;

import com.huanli233.biliapi.BiliBiliAPI;
import com.huanli233.biliapi.httplib.annotations.API;
import com.huanli233.biliapi.httplib.annotations.Queries;
import com.huanli233.biliapi.httplib.utils.Cookies;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Invocation;

public class HttpRequestInterceptor implements Interceptor {

	public Response intercept(Chain chain) throws IOException { 
		return chain.proceed(processUrlParam(overrideUrl(handleHeaders(handleCookies(chain)).build())));
	}
	
	private Request.Builder handleCookies(Chain chain) {
		Request request = chain.request();
		if (BiliBiliAPI.getInstance().getLoginInfo() != null) {
			Cookies cookies = BiliBiliAPI.getInstance().getLoginInfo().getCookies();
			if (cookies != null) {
				return request.newBuilder()
						.header(HttpConstants.HeaderNames.COOKIES, cookies.toString());
			}
		}
		return request.newBuilder();
	}
	
	private Request.Builder handleHeaders(Request.Builder builder) {
		return builder
				.header(HttpConstants.HeaderNames.USER_AGENT, HttpConstants.HeaderValues.USER_AGENT_VAL)
				.header(HttpConstants.HeaderNames.SEC_CH_UA, HttpConstants.HeaderValues.SEC_CH_UA)
				.header(HttpConstants.HeaderNames.SEC_CH_UA_PLATFORM, HttpConstants.HeaderValues.SEC_CH_UA_PLATFORM)
				.header(HttpConstants.HeaderNames.SEC_CH_UA_MOBILE, HttpConstants.HeaderValues.SEC_CH_UA_MOBILE);
	}
	
	private Request overrideUrl(Request request) {
		Invocation invocation;
		if (request == null || (invocation = (Invocation) request.tag(Invocation.class)) == null) return request;
		API api;
		if ((api = invocation.method().getAnnotation(API.class)) != null) {
			return request.newBuilder()
					.url(request.url().newBuilder().host(api.value()).build())
					.build();
		}
		return request;
	}
	
	private Request processUrlParam(Request request) {
		Invocation invocation;
		if (request == null || (invocation = (Invocation) request.tag(Invocation.class)) == null) return request;
		Queries queries;
		if ((queries = invocation.method().getAnnotation(Queries.class)) != null || (queries = invocation.instance().getClass().getAnnotation(Queries.class)) != null) {
			HttpUrl.Builder builder = request.url().newBuilder();
			if (queries.keys().length != queries.values().length) {
				throw new IllegalArgumentException("@Queries keys.length != values.length");
			}
			for (int i = 0; i < queries.keys().length; i++) {
				builder.addQueryParameter(queries.keys()[i], queries.values()[i]);
			}
			return request.newBuilder()
					.url(builder.build())
					.build();
		}
		return request;
	}

}
