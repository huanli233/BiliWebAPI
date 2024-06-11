package com.huanli233.biliapi.api.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Country {
	@Expose int id;
	@Expose String cname;
	@SerializedName("country_id")
	@Expose String countryId;
}
