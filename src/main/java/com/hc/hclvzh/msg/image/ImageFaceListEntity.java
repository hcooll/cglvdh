package com.hc.hclvzh.msg.image;

import com.google.gson.annotations.SerializedName;

public class ImageFaceListEntity {

	
	@SerializedName("face_token")
	public String faceToken;
	

	@SerializedName("age")
	public int age;

	@SerializedName("beauty")
	public double beauty;

	@SerializedName("gender")
	public GenderEntity gender;

	@SerializedName("expression")
	public ExpressionEntity expression;

}
