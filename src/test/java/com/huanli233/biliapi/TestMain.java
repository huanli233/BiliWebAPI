package com.huanli233.biliapi;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.junit.Test;

import com.huanli233.biliapi.api.base.BaseResponse;
import com.huanli233.biliapi.api.login.QrCode;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestMain {
	@Test public void main() throws IOException, InterruptedException {
		CountDownLatch countDownLatch = new CountDownLatch(1);
		QrCode.generate().enqueue(new Callback<BaseResponse<QrCode>>() {
			
			@Override
			public void onResponse(Call<BaseResponse<QrCode>> call, Response<BaseResponse<QrCode>> response) {
				System.out.println(response.body());
				countDownLatch.countDown();
			}
			
			@Override
			public void onFailure(Call<BaseResponse<QrCode>> call, Throwable t) {
				countDownLatch.countDown();
			}
		});
		countDownLatch.await();
	}
}
