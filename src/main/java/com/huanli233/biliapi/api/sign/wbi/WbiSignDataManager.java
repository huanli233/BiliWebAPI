package com.huanli233.biliapi.api.sign.wbi;

/**
 * Wbi Sign Key Manager.
 */
public interface WbiSignDataManager {
	void setWbiData(WbiSignKeyInfo newInfo);
	WbiSignKeyInfo getWbiData();
}