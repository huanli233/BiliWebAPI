package com.huanli233.biliapi.api.sign.wbi;

import com.google.gson.annotations.Expose;

import lombok.Data;

@Data
public class WbiSignKeyInfo {
	@Expose private String mixinKey;
	@Expose private long lastUpdateTimestamp;
}
