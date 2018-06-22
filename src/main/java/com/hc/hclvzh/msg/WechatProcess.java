package com.hc.hclvzh.msg;

import com.hc.hclvzh.api_weixin.AuthService;
import com.hc.hclvzh.api_weixin.MediaService;
import com.hc.hclvzh.msg.image.BaiduFaceApiProcess;
import com.hc.hclvzh.msg.text.TulingApiProcess;
import com.hc.hclvzh.msg.voice.BaiduVoiceApiProcess;

/**
 * 微信xml消息处理流程逻辑类
 *
 */
public class WechatProcess {
	/**
	 * 解析处理xml、获取智能回复结果（通过图灵机器人api接口）
	 * 
	 * @param xml
	 *            接收到的微信数据
	 * @return 最终的解析结果（xml格式数据）
	 */
	public String processWechatMag(String xml) {
		/** 解析xml数据 */
		ReceiveXmlEntity xmlEntity = new ReceiveXmlProcess().getMsgEntity(xml);

		if (xmlEntity == null) {
			return "不是微信消息！";
		}

		/** 以文本消息为例，调用图灵机器人api接口，获取回复内容 */
		String result = "";
		if ("text".endsWith(xmlEntity.getMsgType())) {
			result = new TulingApiProcess().getTulingResult(xmlEntity.getContent());
		} else if ("image".endsWith(xmlEntity.getMsgType())) {
			result = new BaiduFaceApiProcess().getFaceResult(xmlEntity.getPicUrl());
		} else if ("voice".endsWith(xmlEntity.getMsgType())) {
			String weixinToken = new AuthService().getToken();
			System.out.println("weixinToken:" + weixinToken + ", mediaId:" + xmlEntity.getMediaId());
			byte[] voiceData = new MediaService().getMedia(weixinToken, xmlEntity.getMediaId());
			System.out.println("voiceData:" + voiceData.toString());
			result = new BaiduVoiceApiProcess().getVoiceResult(voiceData);

			// 识别后智能回复
			result = new TulingApiProcess().getTulingResult(result);
		}

		if (result == null || result.length() <= 0) {
			result = "出错啦！";
		}

		System.out.println("text:" + xmlEntity.getContent() + ", result：" + result);

		/**
		 * 此时，如果用户输入的是“你好”，在经过上面的过程之后，result为“你也好”类似的内容
		 * 因为最终回复给微信的也是xml格式的数据，所有需要将其封装为文本类型返回消息
		 */

		System.out.println("FromUserName:" + xmlEntity.getFromUserName() + ", ToUserName：" + xmlEntity.getToUserName());

		result = new FormatXmlProcess().formatXmlAnswer(xmlEntity.getFromUserName(), xmlEntity.getToUserName(), result);

		return result;
	}
}
