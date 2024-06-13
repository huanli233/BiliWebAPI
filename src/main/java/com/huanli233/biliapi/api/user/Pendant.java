package com.huanli233.biliapi.api.user;

import com.google.gson.annotations.Expose;

import lombok.Data;

@Data
public class Pendant {
	@Expose private int pid;
	@Expose private String name;
	@Expose private String image;
	@Expose private int expire;
}
