package com.huanli233.biliapi.httplib;

import java.io.IOException;
import java.util.List;

import com.huanli233.biliapi.BiliBiliAPI;
import com.huanli233.biliapi.httplib.utils.Cookies;

import okhttp3.Interceptor;
import okhttp3.Response;

public class HttpResponseInterceptor implements Interceptor {

	public Response intercept(Chain chain) throws IOException {
		Response proceed = chain.proceed(chain.request());
		return handleCookiesSet(proceed);
	}
	
	private Response handleCookiesSet(Response response) {
		List<String> setCookies = response.headers(HttpConstants.HeaderNames.SET_COOKIE);
		if (setCookies.isEmpty()) return response;
		for (String string : setCookies) {
			Cookies cookieItem = new Cookies(string);
			if (cookieItem.containsKey("Domain") && !cookieItem.get("Domain").endsWith("bilibili.com")) continue;
			BiliBiliAPI.getInstance().getLoginInfo().getCookies().set(cookieItem.getFirstKey(), cookieItem.get(cookieItem.getFirstKey()));
		}
		return response;
	}

}
