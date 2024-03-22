package com.example.food.util;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Component;

@Component	//@Component가 서비스보다 범위가 크다
public class JsonUtil {
	
	public String list2Json(List<String> list) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("list", list);
		return jsonObj.toString();
	}
	
	public List<String> json2List(String jsonStr){
		JSONParser parser = new JSONParser();
		List<String> list = null;
		try {
			JSONObject jsonObj = (JSONObject) parser.parse(jsonStr);
			JSONArray jsonArr = (JSONArray) jsonObj.get("list");
			list = (List<String>) jsonArr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
