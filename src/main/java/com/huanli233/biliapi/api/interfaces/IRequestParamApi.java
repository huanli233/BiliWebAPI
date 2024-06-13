package com.huanli233.biliapi.api.interfaces;

import com.huanli233.biliapi.api.base.BaseResponse;
import com.huanli233.biliapi.api.requestparam.BiliTicket;
import com.huanli233.biliapi.api.requestparam.Buvids;
import com.huanli233.biliapi.httplib.annotations.Queries;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IRequestParamApi {
	@GET("/x/frontend/finger/spi")
	Call<BaseResponse<Buvids>> requestBuvids();
	
	@POST("/bapis/bilibili.api.ticket.v1.Ticket/GenWebTicket")
	@Queries(keys = {"csrf"}, values = {""})
	Call<BaseResponse<BiliTicket>> genWebTicket(@Query("key_id") String keyId, 
			@Query("hexsign") String hexsign, 
			@Query("context[ts]") String ts);
}
