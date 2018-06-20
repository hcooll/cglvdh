package com.hc.hclvzh.msg.voice;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class VoiceEntity {

	@SerializedName("corpus_no")
	public String corpus_no;
	
	@SerializedName("err_msg")
	public String err_msg;
	
	@SerializedName("err_no")
	public int err_no;
	
	@SerializedName("result")
	public ArrayList<String> results;
	
	@SerializedName("sn")
	public String sn;
	
}
