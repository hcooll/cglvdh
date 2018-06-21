package com.hc.hclvzh.api_weixin;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class MediaService {

    public byte[] getMedia(String token, String mediaId) {
        // 获取token地址
        String authHost = "http://file.api.weixin.qq.com/cgi-bin/media/get?";
        String getAccessTokenUrl = authHost
                // 1. grant_type为固定参数
                + "access_token=" + token
                // 2. 官网获取的 API Key
                + "&media_id=" + mediaId
                // 3. 官网获取的 Secret Key
                + "&method=GET&body=0";
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
            InputStream inStream = connection.getInputStream();

            ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = inStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
            outSteam.close();
            inStream.close();
            return outSteam.toByteArray();

        } catch (Exception e) {
            System.err.println("获取Media资源失败！");
            e.printStackTrace(System.err);
        }
        return null;
    }

}
