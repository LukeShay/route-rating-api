package com.routerating.api.common.utils;

import java.util.HashMap;
import java.util.Map;

public class BodyUtils {

	public static Map<String, String> error(String message) {
		Map<String, String> map = new HashMap<>(1);
		map.put("error", message);
		return map;
	}
}
