package com.huanli233.biliapi.api.logininfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class NavStat {
	@Expose private int following;
	@Expose private int follower;
	@SerializedName("dynamic_count")
	@Expose private int dynamicCount;
}
