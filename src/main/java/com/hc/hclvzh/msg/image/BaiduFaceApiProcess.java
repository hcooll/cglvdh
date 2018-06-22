package com.hc.hclvzh.msg.image;

import com.hc.hclvzh.api_baidu.FaceAuthService;

public class BaiduFaceApiProcess {

    public String getFaceResult(String imageUrl) {

        try {
            String token = FaceAuthService.getAuth();

            ImageEntity imageEntity = new FaceDetect().detect(imageUrl, token);

            if (imageEntity != null) {

                StringBuilder result = new StringBuilder();

                for (int i = 0; i < imageEntity.resultEntity.faceNum; i++) {

                    ImageFaceListEntity listEntity = imageEntity.resultEntity.faceListEntities.get(0);

                    result.append(listEntity.age)
                            .append("岁, ")
                            .append(listEntity.gender.type)
                            .append(", 颜值：")
                            .append(listEntity.beauty)
                            .append(", 表情：")
                            .append(listEntity.expression.type);

                }

                return result.toString();
            }

        } catch (Exception e) {
            // TODO: handle exception
        }

        return "图像识别失败";
    }
}
