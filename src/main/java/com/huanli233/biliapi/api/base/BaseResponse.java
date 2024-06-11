package com.huanli233.biliapi.api.base;

import com.google.gson.annotations.Expose;

import lombok.Data;

@Data
public class BaseResponse<T> {
	@Expose int code;
	@Expose String message;
	@Expose T data;
}
