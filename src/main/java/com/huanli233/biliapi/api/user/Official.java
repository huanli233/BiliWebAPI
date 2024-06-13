package com.huanli233.biliapi.api.user;

import com.google.gson.annotations.Expose;

import lombok.Data;

@Data
public class Official {
	@Expose private int role;
	@Expose private String title;
	@Expose private String desc;
	@Expose private int type;
}
