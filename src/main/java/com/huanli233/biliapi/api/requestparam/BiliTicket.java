package com.huanli233.biliapi.api.requestparam;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.huanli233.biliapi.BiliBiliAPI;
import com.huanli233.biliapi.api.base.BaseResponse;
import com.huanli233.biliapi.api.interfaces.IRequestParamApi;

import lombok.Data;
import retrofit2.Call;

@Data
public class BiliTicket {
	@Expose private String ticket;
	@SerializedName("created_at")
	@Expose private int createdTime;
	
	public static Call<BaseResponse<BiliTicket>> generate(String keyId, String hexSign, String ts) {
		return BiliBiliAPI.getInstance().getApi(IRequestParamApi.class).genWebTicket(keyId, hexSign, ts);
	}
}
