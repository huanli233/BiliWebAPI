package com.huanli233.biliapi;

import java.io.IOException;

import org.junit.Test;

import com.huanli233.biliapi.api.login.QrCode;

public class TestMain {
	@Test public void main() throws IOException {
		QrCode.generate().execute();
		System.out.println(BiliBiliAPI.getInstance().getLoginInfo().getCookies().toString());
	}
}
