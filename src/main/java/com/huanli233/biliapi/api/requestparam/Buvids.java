package com.huanli233.biliapi.api.requestparam;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.huanli233.biliapi.BiliBiliAPI;
import com.huanli233.biliapi.api.base.BaseResponse;
import com.huanli233.biliapi.api.interfaces.IRequestParamApi;

import lombok.Data;
import retrofit2.Call;

@Data
public class Buvids {
	@SerializedName("b_3")
	@Expose private String buvid3;
	@SerializedName("b_4")
	@Expose private String buvid4;
	
	public static Call<BaseResponse<Buvids>> generate() {
		return BiliBiliAPI.getInstance().getApi(IRequestParamApi.class).requestBuvids();
	}
}
