package com.hc.hclvzh.msg.image;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class ImageResultEntity {

	@SerializedName("face_num")
	public int faceNum;
	
	@SerializedName("face_list")
	public ArrayList<ImageFaceListEntity> faceListEntities;
}
