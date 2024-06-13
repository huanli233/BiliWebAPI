package com.huanli233.biliapi.httplib.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.huanli233.biliapi.BiliBiliAPI;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface API {
	String value() default BiliBiliAPI.BASE_API_URL;
}
