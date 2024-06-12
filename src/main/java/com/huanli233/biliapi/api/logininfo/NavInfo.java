package com.huanli233.biliapi.api.logininfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
//TODO 完善等级信息level_info、认证信息official、认证信息2officialVerify、会员标签vip_label、B币信息wallet
public class NavInfo {
	@Expose private boolean isLogin;
	@SerializedName("email_verified")
	@Expose private int emailVerified;
	@Expose private String face;
	@Expose private long mid;
	@SerializedName("mobile_verified")
	@Expose private int mobileVerified;
	@Expose private int money;
	@Expose private int moral;
	@Expose private int scores;
	@Expose private String uname;
	@Expose private long vipDueDate;
	@Expose private int vipStatus;
	@Expose private int vipType;
	@SerializedName("vip_pay_type")
	@Expose private int vipPayType;
	@SerializedName("vip_theme_type")
	@Expose private int vipThemeType;
	@SerializedName("vip_avatar_subscript")
	@Expose private int vipAvatarSubscript;
	@SerializedName("vip_nickname_color")
	@Expose private String vipNicknameColor;
	@SerializedName("has_shop")
	@Expose private boolean hasShop;
	@SerializedName("shop_url")
	@Expose private String shopUrl;
	@SerializedName("allowance_count")
	@Expose private int allowanceCount;
	@SerializedName("answer_status")
	@Expose private int answerStatus;
	@SerializedName("is_senior_member")
	@Expose private int isSeniorMember;
	@SerializedName("wbi_img")
	@Expose private WbiImg wbiImg;
	@SerializedName("is_jury")
	@Expose private boolean isJury;
	
	@Data
	public static class WbiImg {
		@SerializedName("img_url")
		@Expose private String imgUrl;
		@SerializedName("sub_url")
		@Expose private String subUrl;
	}
}
