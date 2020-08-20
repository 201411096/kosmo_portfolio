package com.mycompany.util;

import java.io.FileReader;
import java.io.FileWriter;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JSONUtil {
	public static JSONObject readJsonFile(String file) {
		JSONParser jsonParser = new JSONParser();		
		JSONObject jsonObject = null;
		try {
			jsonObject = (JSONObject)jsonParser.parse(new FileReader(file));
		}catch(Exception e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
	public static void writeJsonFile(String file, JSONObject jsonObject) {
		try {
			FileWriter fw = new FileWriter(file);
			fw.write(jsonObject.toJSONString());
			fw.flush();
			fw.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}

/* 
사용예시

file = System.getProperty("user.dir") + "\\src\\test\\sample.json";
JSONObject object = JSONUtil.readJsonFile(System.getProperty("user.dir") + "\\src\\test\\sample.json");		
JSONUtil.writeJsonFile(System.getProperty("user.dir") + "\\src\\test\\sample3.json", object);

*/