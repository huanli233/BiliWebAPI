package com.huanli233.biliapi.api.interfaces;

import com.huanli233.biliapi.BiliBiliAPI;
import com.huanli233.biliapi.api.base.BaseResponse;
import com.huanli233.biliapi.api.logininfo.CoinCount;
import com.huanli233.biliapi.api.logininfo.NavInfo;
import com.huanli233.biliapi.api.logininfo.NavStat;
import com.huanli233.biliapi.httplib.annotations.API;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ILoginInfoApi {
	@GET("/x/web-interface/nav")
	Call<BaseResponse<NavInfo>> requestNavInfo();
	
	@GET("/x/web-interface/nav/stat")
	Call<BaseResponse<NavStat>> requestNavStat();
	
	@GET("/site/getCoin")
	@API(BiliBiliAPI.ACCOUNT_URL)
	Call<BaseResponse<CoinCount>> requestCoinCount();
}
