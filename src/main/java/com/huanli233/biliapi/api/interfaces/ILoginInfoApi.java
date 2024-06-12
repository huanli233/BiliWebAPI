package com.huanli233.biliapi.api.interfaces;

import com.huanli233.biliapi.api.base.BaseResponse;
import com.huanli233.biliapi.api.logininfo.NavInfo;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ILoginInfoApi {
	@GET("/x/web-interface/nav")
	Call<BaseResponse<NavInfo>> requestNavInfo();
}
