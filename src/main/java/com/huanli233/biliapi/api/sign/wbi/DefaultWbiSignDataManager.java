package com.huanli233.biliapi.api.sign.wbi;

public class DefaultWbiSignDataManager implements WbiSignDataManager {
	WbiSignKeyInfo wbiSignKeyInfo;
	@Override
	public void setWbiData(WbiSignKeyInfo newInfo) {
		this.wbiSignKeyInfo = newInfo;
	}
	
	@Override
	public WbiSignKeyInfo getWbiData() {
		return this.wbiSignKeyInfo;
	}
}
