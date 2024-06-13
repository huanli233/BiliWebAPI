package com.huanli233.biliapi.api.logininfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.huanli233.biliapi.api.user.LevelInfo;
import com.huanli233.biliapi.api.user.Official;
import com.huanli233.biliapi.api.user.OfficialVerify;
import com.huanli233.biliapi.api.user.Pendant;
import com.huanli233.biliapi.api.user.VipLabel;
import com.huanli233.biliapi.api.user.Wallet;

import lombok.Data;

@Data
public class NavInfo {
	@Expose private boolean isLogin;
	@SerializedName("email_verified")
	@Expose private int emailVerified;
	@Expose private String face;
	@SerializedName("level_info")
	@Expose private LevelInfo levelInfo;
	@Expose private long mid;
	@SerializedName("mobile_verified")
	@Expose private int mobileVerified;
	@Expose private int money;
	@Expose private int moral;
	@Expose private Official official;
	@Expose private OfficialVerify officialVerify;
	@Expose private Pendant pendant;
	@Expose private int scores;
	@Expose private String uname;
	@Expose private long vipDueDate;
	@Expose private int vipStatus;
	@Expose private int vipType;
	@SerializedName("vip_pay_type")
	@Expose private int vipPayType;
	@SerializedName("vip_theme_type")
	@Expose private int vipThemeType;
	@SerializedName("vip_label")
	@Expose private VipLabel vipLabel;
	@SerializedName("vip_avatar_subscript")
	@Expose private int vipAvatarSubscript;
	@SerializedName("vip_nickname_color")
	@Expose private String vipNicknameColor;
	@Expose private Wallet wallet;
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
