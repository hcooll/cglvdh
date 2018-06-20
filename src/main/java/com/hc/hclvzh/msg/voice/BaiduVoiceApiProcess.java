package com.hc.hclvzh.msg.voice;

import com.hc.hclvzh.api_baidu.AuthService;

public class BaiduVoiceApiProcess {

	public String getVoiceResult(String voiceData) {

		try {
			String token = AuthService.getAuth();

			VoiceEntity voiceEntity = new VoiceDetect().detect(voiceData, token);

			if (voiceEntity != null) {

				String result = "";

				for (int i = 0; i < voiceEntity.results.size(); i++) {

					result += voiceEntity.results.get(0) + "\r\n";

				}

				return result;
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		return "语音识别失败";
	}
}
