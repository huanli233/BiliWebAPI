package com.huanli233.biliapi.httplib;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.huanli233.biliapi.BiliBiliAPI;
import com.huanli233.biliapi.api.base.BaseResponse;
import com.huanli233.biliapi.api.requestparam.BiliTicket;
import com.huanli233.biliapi.api.requestparam.Buvids;
import com.huanli233.biliapi.api.utils.BiliTicketUtil;
import com.huanli233.biliapi.api.utils.RequestParamUtil;
import com.huanli233.biliapi.api.utils.WbiUtil;
import com.huanli233.biliapi.httplib.annotations.API;
import com.huanli233.biliapi.httplib.annotations.Queries;
import com.huanli233.biliapi.httplib.annotations.WbiSign;
import com.huanli233.biliapi.httplib.utils.Cookies;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Invocation;

public class HttpRequestInterceptor implements Interceptor {

	public Response intercept(Chain chain) throws IOException { 
		checkCookieParams();
		return chain.proceed(handleWbiSign(processUrlParam(overrideUrl(handleHeaders(handleCookies(chain)).build()))));
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
	
	private static final Map<String, String> otherCookieMap = new HashMap<>() {
		private static final long serialVersionUID = 1L;
	{
        put("enable_web_push", "DISABLE");
        put("header_theme_version", "undefined");
        put("home_feed_column", "4");
        put("browser_resolution", "839-959");
    }};
	private void checkCookieParams() {
		Cookies cookies = BiliBiliAPI.getInstance().getLoginInfo().getCookies();

        // bili_ticket
        if (!cookies.containsKey("bili_ticket" ) || cookies.get("bili_ticket").equals("null") || !cookies.containsKey("bili_ticket_expires") || parseInt(cookies.get("bili_ticket_expires")) == null || parseInt(cookies.get("bili_ticket_expires")) < (int) (System.currentTimeMillis() / 1000)) {
			try {
				BaseResponse<BiliTicket> baseResponse = BiliTicketUtil.genBiliTicketSync();
	            if (baseResponse != null) {
					BiliTicket biliTicket = baseResponse.getData();
					BiliBiliAPI.getInstance().getLoginInfo().getCookies().set("bili_ticket", biliTicket.getTicket());
					BiliBiliAPI.getInstance().getLoginInfo().getCookies().set("bili_ticket_expires", String.valueOf(biliTicket.getCreatedTime() + (3 * 24 * 60 * 60)));
				}
			} catch (IOException e) {
				if (BiliBiliAPI.getInstance().getRequestParamGenerateErrorHandler() != null) {
					BiliBiliAPI.getInstance().getRequestParamGenerateErrorHandler().handleError(e);
				} else {
					throw new RuntimeException(e);
				}
			}
        }

        // _uuid
        if (!cookies.containsKey("_uuid")) {
        	BiliBiliAPI.getInstance().getLoginInfo().getCookies().set("_uuid", RequestParamUtil.gen_uuid_infoc());
        }

        // b_lsid
        if (!cookies.containsKey("b_lsid")) {
        	BiliBiliAPI.getInstance().getLoginInfo().getCookies().set("b_lsid", RequestParamUtil.gen_b_lsid());
        }

        // buvid_fp. Hardcoded.
        if (!cookies.containsKey("buvid_fp")) {
        	BiliBiliAPI.getInstance().getLoginInfo().getCookies().set("buvid_fp", /* gen_buvid_fp(NetWorkUtil.USER_AGENT_WEB + System.currentTimeMillis(), 31) */ "30c3020be6cee8345ddc4c3c6b77f60f");
        }

        // buvid3 & buvid4. Get from http API.
        if (!cookies.containsKey("buvid3") || !cookies.containsKey("buvid4")) {
        	try {
				BaseResponse<Buvids> baseResponse = Buvids.generate().execute().body();
	            if (baseResponse != null) {
					Buvids buvids = baseResponse.getData();
					BiliBiliAPI.getInstance().getLoginInfo().getCookies().set("buvid3", buvids.getBuvid3());
					BiliBiliAPI.getInstance().getLoginInfo().getCookies().set("buvid4", buvids.getBuvid4());
				}
			} catch (IOException e) {
				if (BiliBiliAPI.getInstance().getRequestParamGenerateErrorHandler() != null) {
					BiliBiliAPI.getInstance().getRequestParamGenerateErrorHandler().handleError(e);
				} else {
					throw new RuntimeException(e);
				}
			}
        }

        // b_nut
        if (!cookies.containsKey("b_nut")) {
        	BiliBiliAPI.getInstance().getLoginInfo().getCookies().set("b_nut", RequestParamUtil.gen_b_nut());
        }

        // Others
        for (Map.Entry<String, String> entry : otherCookieMap.entrySet()) {
            if (!cookies.containsKey(entry.getKey())) {
            	BiliBiliAPI.getInstance().getLoginInfo().getCookies().set(entry.getKey(), entry.getValue());
            }
        }
	}
	
	private static Integer parseInt(String string) {
        try {
            return Integer.parseInt(string);
        } catch (Throwable ignored) {
            return null;
        }
    }
	
	private Request handleWbiSign(Request request) {
		Invocation invocation;
		if (request == null) return request;
		if ((invocation = (Invocation) request.tag(Invocation.class)) != null) {
			if (invocation.method().isAnnotationPresent(WbiSign.class)) {
				try {
					return request.newBuilder()
							.url(WbiUtil.signUrl(request.url()))
							.build();
				} catch (Throwable e) {
					if (BiliBiliAPI.getInstance().getRequestParamGenerateErrorHandler() != null) {
						BiliBiliAPI.getInstance().getRequestParamGenerateErrorHandler().handleError(e);
					} else {
						throw new RuntimeException(e);
					}
				}
			}
		}
		return request;
	}
	
	private Request overrideUrl(Request request) {
		Invocation invocation;
		if (request == null || (invocation = (Invocation) request.tag(Invocation.class)) == null) return request;
		API api;
		if ((api = invocation.method().getAnnotation(API.class)) != null || (api = invocation.service().getAnnotation(API.class)) != null) {
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
