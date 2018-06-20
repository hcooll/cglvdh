package com.hc.hclvzh.msg.image;

import com.hc.hclvzh.api_baidu.AuthService;

public class BaiduFaceApiProcess {

	public String getFaceResult(String imageUrl) {

		try {
			String token = AuthService.getAuth();

			ImageEntity imageEntity = new FaceDetect().detect(imageUrl, token);

			if (imageEntity != null) {

				String result = "";

				for (int i = 0; i < imageEntity.resultEntity.faceNum; i++) {

					result += imageEntity.resultEntity.faceListEntities.get(0).age + "嵗 \r\n";

				}

				return result;
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		return "圖像識別失敗";
	}
}
