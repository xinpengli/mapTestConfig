package com.geekplus.maptest.Common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;



/**
 * JSON工具类
 */
public final class JSONUtil {




	private JSONUtil() {
	}
/**
 * JsonObejet 转json string
 *
 **/

	public static String toJSONString(Object value) {
		if (value == null) {
			return null;
		}


			return  JSON.toJSONString(value);

	}
	/**
	 * json string 转bean
	 *
	 **/
	public static <T> T jsonToBean(String json, Class<T> clz) {
		if (json == null || clz == null) {
			return null;
		}


			return JSON.parseObject(json,clz);

	}

	public static JSONObject beanToJsonObeject(Object javaObeject) {
		if (javaObeject == null  ) {
			return null;
		}

		 JSONObject jsonObject=(JSONObject) JSONObject.toJSON(javaObeject);
		return jsonObject;
	}
	/**
	 * JsonObejet 转json string
	 *
	 **/
	public static String beanToJsonString(Object javaObeject) {
		if (javaObeject == null  ) {
			return null;
		}

		JSONObject jsonObject=(JSONObject) JSONObject.toJSON(javaObeject);
		return jsonObject.toJSONString();
	}


}
