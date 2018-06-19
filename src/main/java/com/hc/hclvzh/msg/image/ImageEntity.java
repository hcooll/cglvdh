package com.hc.hclvzh.msg.image;

import com.google.gson.annotations.SerializedName;

public class ImageEntity {

	@SerializedName("error_code")
	public String errorCode;

	@SerializedName("error_msg")
	public String errorMsg;

	@SerializedName("result")
	public ImageResultEntity resultEntity;
}
