package com.huanli233.biliapi.api.login;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.huanli233.biliapi.BiliBiliAPI;
import com.huanli233.biliapi.api.base.BaseResponse;
import com.huanli233.biliapi.api.interfaces.ILoginApi;

import lombok.Data;
import retrofit2.Call;

@Data
public class CountryList {
	@Expose private List<Country> common;
	@Expose private List<Country> others;
	
	public static Call<BaseResponse<CountryList>> getList() {
		return BiliBiliAPI.getInstance().getApi(ILoginApi.class).getCountryList();
	}
}
