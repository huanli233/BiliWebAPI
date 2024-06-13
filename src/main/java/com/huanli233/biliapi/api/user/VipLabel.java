package com.huanli233.biliapi.api.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class VipLabel {
	@Expose private String path;
	@Expose private String text;
	@SerializedName("label_theme")
	@Expose private String labelTheme;
}
