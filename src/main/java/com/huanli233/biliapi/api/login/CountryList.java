package com.huanli233.biliapi.api.login;

import java.util.List;

import com.google.gson.annotations.Expose;

import lombok.Data;

@Data
public class CountryList {
	@Expose List<Country> common;
	@Expose List<Country> others;
}
