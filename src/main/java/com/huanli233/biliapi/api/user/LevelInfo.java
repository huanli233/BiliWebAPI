package com.huanli233.biliapi.api.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class LevelInfo {
	@SerializedName("current_level")
	@Expose private int currentLevel;
	@SerializedName("current_min")
	@Expose private int currentMin;
	@SerializedName("current_exp")
	@Expose private int currentExp;
	@SerializedName("next_exp")
	@Expose private String nextExp;
}
