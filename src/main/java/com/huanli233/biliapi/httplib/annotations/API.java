package com.huanli233.biliapi.httplib.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.huanli233.biliapi.BiliBiliAPI;

@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface API {
	String value() default BiliBiliAPI.BASE_API_URL;
}
