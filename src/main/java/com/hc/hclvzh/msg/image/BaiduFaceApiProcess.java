package com.hc.hclvzh.msg.image;

import com.hc.hclvzh.api_baidu.FaceAuthService;

public class BaiduFaceApiProcess {

	public String getFaceResult(String imageUrl) {

		try {
			String token = FaceAuthService.getAuth();

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

		return "图像识别失败";
	}
}
