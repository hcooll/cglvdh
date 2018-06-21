package com.hc.hclvzh.msg.voice;

import com.hc.hclvzh.utils.GsonUtils;
import com.hc.hclvzh.utils.HttpUtil;
import com.hc.hclvzh.utils.MACUtil;
import org.json.JSONObject;

import javax.xml.bind.DatatypeConverter;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * 人脸检测与属性分析
 */
public class VoiceDetect {

    /**
     * https://ai.baidu.com/docs#/Face-Detect-V3/top 重要提示代码中所需工具类
     * FileUtil,Base64Util,HttpUtil,GsonUtils请从
     * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
     * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
     * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
     * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3 下载
     */
    public VoiceEntity detect(byte[] voiceData, String token) {
        // 请求url
        String url = "http://vop.baidu.com/server_api";
        try {

            //String voiceCode = Base64Util.encode(from)

            Map<String, Object> map = new HashMap<>();
            map.put("format", "amr");
            map.put("rate", "16000");
            map.put("dev_pid", 1537);
            map.put("channel", "1");
            map.put("token", token);
            map.put("cuid", MACUtil.getMACAddress(InetAddress.getLocalHost()));
            map.put("len", voiceData.length);
            map.put("speech", DatatypeConverter.printBase64Binary(voiceData));


            String param = GsonUtils.toJson(map);

            System.out.println("param: " + param);

            String result = HttpUtil.post(url, null, "application/json", param);

            System.out.println("result: " + result);

            return GsonUtils.fromJson(result, VoiceEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public VoiceEntity detect_2(byte[] voiceData, String token) {

        // 请求url
        String serverURL = "http://vop.baidu.com/server_api";

        try {

            HttpURLConnection conn = (HttpURLConnection) new URL(serverURL
                    + "?cuid=" + MACUtil.getMACAddress(InetAddress.getLocalHost()) + "&token=" + token).openConnection();

            // add request header
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "audio/amr; rate=8000");

            conn.setDoInput(true);
            conn.setDoOutput(true);

            // send request
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.write(voiceData);
            wr.flush();
            wr.close();

            String result = printResponse(conn);

            System.out.println("result: " + result);

            return GsonUtils.fromJson(result, VoiceEntity.class);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    private static String printResponse(HttpURLConnection conn) throws Exception {
        if (conn.getResponseCode() != 200) {
            // request error
            return "";
        }
        InputStream is = conn.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        String line;
        StringBuffer response = new StringBuffer();
        while ((line = rd.readLine()) != null) {
            response.append(line);
            response.append('\r');
        }
        rd.close();
        return response.toString();
    }
}