package com.huanli233.biliapi.api.utils;

import java.io.IOException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.huanli233.biliapi.api.base.BaseResponse;
import com.huanli233.biliapi.api.requestparam.BiliTicket;

import retrofit2.Call;

public class BiliTicketUtil {
	
	public static Call<BaseResponse<BiliTicket>> genBiliTicket() {
        int ts = (int) (System.currentTimeMillis() / 1000);
        String o = hmacSha256("XgwSnGZ1p", "ts" + ts);
        return BiliTicket.generate("ec02", o, String.valueOf(ts));
    }
	
	public static BaseResponse<BiliTicket> genBiliTicketSync() throws IOException {
		return genBiliTicket().execute().body();
	}
	
	private static String hmacSha256(String key, String message) {
        try {
        	Mac sha256Hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "HmacSHA256");
            sha256Hmac.init(secretKey);
            byte[] hashBytes = sha256Hmac.doFinal(message.getBytes());
            StringBuilder hexHash = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexHash.append('0');
                hexHash.append(hex);
            }
            return hexHash.toString();
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
    }
}
