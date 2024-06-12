package com.huanli233.biliapi.api.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Country {
	@Expose private int id;
	@Expose private String cname;
	@SerializedName("country_id")
	@Expose private String countryId;
}
