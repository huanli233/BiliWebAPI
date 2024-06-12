package com.huanli233.biliapi.api.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.huanli233.biliapi.BiliBiliAPI;
import com.huanli233.biliapi.api.base.BaseResponse;
import com.huanli233.biliapi.api.logininfo.LoginInfo;
import com.huanli233.biliapi.api.logininfo.NavInfo;
import com.huanli233.biliapi.api.sign.wbi.WbiSignKeyInfo;

import okhttp3.HttpUrl;
import retrofit2.Response;

public class WbiUtil {
	private static final int[] MIXIN_KEY_ENC_TAB = {46, 47, 18, 2, 53, 8, 23, 32, 15, 50, 10, 31, 58, 3, 45, 35, 27, 43, 5, 49,
	        33, 9, 42, 19, 29, 28, 14, 39, 12, 38, 41, 13, 37, 48, 7, 16, 24, 55, 40,
	        61, 26, 17, 0, 1, 60, 51, 30, 4, 22, 25, 54, 21, 56, 59, 6, 63, 57, 62, 11,
	        36, 20, 34, 44, 52};
	
	private static String getWBIKey() throws IOException {
		WbiSignKeyInfo wbiSignKeyInfo;
		long currentTimeStamp = System.currentTimeMillis();
        if ((wbiSignKeyInfo = BiliBiliAPI.getInstance().getWbiSignDataManager().getWbiData()) != null 
        		&& ((wbiSignKeyInfo.getLastUpdateTimestamp() < currentTimeStamp && currentTimeStamp - wbiSignKeyInfo.getLastUpdateTimestamp() < (24 * 60 * 60 * 1000))
        		|| wbiSignKeyInfo.getLastUpdateTimestamp() >= currentTimeStamp)
        		&& wbiSignKeyInfo.getMixinKey() != null) {
			return wbiSignKeyInfo.getMixinKey();
		} else {
			Response<BaseResponse<NavInfo>> response = LoginInfo.navInfo().execute();
	        if (response.isSuccessful()) {
	        	BaseResponse<NavInfo> baseResponse = response.body();
	        	if (baseResponse.getData() != null) {
	        		String result = getWBIMixinKey(getFileFirstName(getFileNameFromLink(baseResponse.getData().getWbiImg().getImgUrl())) + getFileFirstName(getFileNameFromLink(baseResponse.getData().getWbiImg().getSubUrl())));
	        		wbiSignKeyInfo.setLastUpdateTimestamp(currentTimeStamp);
	        		wbiSignKeyInfo.setMixinKey(result);
	        		return result;
				}
			}
			throw new IOException("WBI Sign request navInfo fail: " + response.code());
		}
    }
	
	private static String getWBIMixinKey(String raw_key) {
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            key.append(raw_key.charAt(MIXIN_KEY_ENC_TAB[i]));
        }
        return key.toString();
    }
	
	private static String getFileFirstName(String file){
        for (int i = 0; i < file.length(); i++) {
            if(file.charAt(i)=='.'){
                return file.substring(0,i);
            }
        }
        return "fail";
    }
	
	public static HttpUrl signUrl(HttpUrl url) throws IOException {
		String wts = String.valueOf(System.currentTimeMillis() / 1000);
		String mixinKey = getWBIKey();
		HttpUrl.Builder builder = url.newBuilder().addQueryParameter("wts", wts);
		HttpUrl sortedUrl = sortQueryParameters(builder.build());
		String w_rid = md5(encodeUrl(sortedUrl.query()) + mixinKey);
		return url.newBuilder()
				.addQueryParameter("wts", wts)
				.addQueryParameter("w_rid", w_rid)
				.build();
	}
	
	private static HttpUrl sortQueryParameters(HttpUrl url) {
        Map<String, List<String>> sortedQueryParameters = new TreeMap<>();
        
        for (int i = 0; i < url.querySize(); i++) {
            String name = url.queryParameterName(i);
            String value = url.queryParameterValue(i);

            if (!sortedQueryParameters.containsKey(name)) {
                sortedQueryParameters.put(name, new ArrayList<>());
            }
			sortedQueryParameters.get(name).add(value);
        }

        HttpUrl.Builder urlBuilder = url.newBuilder().query(null);
        for (Map.Entry<String, List<String>> entry : sortedQueryParameters.entrySet()) {
            String key = entry.getKey();
            for (String value : entry.getValue()) {
                urlBuilder.addQueryParameter(key, value);
            }
        }

        return urlBuilder.build();
    }
	
	private static String encodeUrl(String input) {
        if (input == null) {
            return null;
        }

        try {
            String encoded = URLEncoder.encode(input, "UTF-8");
            encoded = encoded.replace("+", "%20");
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < encoded.length(); i++) {
                char c = encoded.charAt(i);
                result.append(c);
                if (c == '%' && i + 2 < encoded.length()) {
                    result.append(Character.toUpperCase(encoded.charAt(i + 1)));
                    result.append(Character.toUpperCase(encoded.charAt(i + 2)));
                    i += 2;
                }
            }
            return result.toString();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 encoding is not supported", e);
        }
    }
	
	private static String getFileNameFromLink(String link){
        int length = link.length();
        for (int i = length - 1; i > 0; i--) {
            if(link.charAt(i)=='/'){
                return link.substring(i+1);
            }
        }
        return "fail";
    }
	
	private static String md5(String plainText) {
        byte[] secretBytes;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            secretBytes = md.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        StringBuilder md5code = new StringBuilder(new BigInteger(1, secretBytes).toString(16));
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code.insert(0, "0");
        }
        return md5code.toString();
    }
}
