/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.study.lecture.common.utils;

import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * 响应类，返回数据
 *
 * @author zqc
 */
public class R extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	
	public R() {
		put("code", ResultCodeEnum.SUCCESS.getCode());
		put("msg", ResultCodeEnum.SUCCESS.getMessage());
	}
	
	public static R error() {
		return error(ResultCodeEnum.ERROR.getCode(), ResultCodeEnum.ERROR.getMessage());
	}
	
	public static R error(String msg) {
		return error(ResultCodeEnum.ERROR.getCode(), msg);
	}
	
	public static R error(int code, String msg) {
		R r = new R();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static R ok(String msg) {
		R r = new R();
		r.put("msg", msg);
		return r;
	}
	
	public static R ok(Map<String, ?> map) {
		R r = new R();
		r.putAll(map);
		return r;
	}
	
	public static R ok() {
		return new R();
	}

	@Override
	public R put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}
