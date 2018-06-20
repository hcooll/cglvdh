package com.hc.hclvzh.api_weixin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class AuthService {

	private static String token;
	private static long expiresIn; // token过期时间

	public String getToken() {
		if (token != null && token.length() > 0 && expiresIn > System.currentTimeMillis()) {
			return token;
		}
		String appid = "wx21807fe0bbdf9cab";
		String secret = "09523bb2d57a21277e6c7b2944bc8edb";
		return getToken(appid, secret);
	}

	public String getToken(String appid, String secret) {
		// 获取token地址
		String authHost = "https://api.weixin.qq.com/cgi-bin/token?";
		String getAccessTokenUrl = authHost
				// 1. grant_type为固定参数
				+ "grant_type=client_credential"
				// 2. 官网获取的 API Key
				+ "&appid=" + appid
				// 3. 官网获取的 Secret Key
				+ "&secret=" + secret;
		try {
			URL realUrl = new URL(getAccessTokenUrl);
			// 打开和URL之间的连接
			HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.err.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String result = "";
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
			/**
			 * 返回结果示例
			 */
			System.err.println("result:" + result);
			JSONObject jsonObject = new JSONObject(result);
			String access_token = jsonObject.getString("access_token");
			long expires_in = jsonObject.getLong("expires_in");
			expiresIn = System.currentTimeMillis() + expires_in * 1000;
			token = access_token;
		} catch (Exception e) {
			System.err.printf("获取token失败！");
			e.printStackTrace(System.err);
		}
		return token;
	}
}
