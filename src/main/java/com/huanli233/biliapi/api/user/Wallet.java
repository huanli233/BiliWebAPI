package com.huanli233.biliapi.api.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Wallet {
	@Expose private long mid;
	@SerializedName("bcoin_balance")
	@Expose private int bcoinBalance;
	@SerializedName("coupon_balance")
	@Expose private int couponBalance;
	@SerializedName("coupon_due_time")
	@Expose private long couponDueTime;
}
