package com.edu.common.util;

import java.util.HashMap;
import java.util.Map;

public class Message {
	private Integer code;//响应状态码，约定200成功，失败500
	private String msg;//代表响应信息
	private Map<String, Object> info=new HashMap<>();
	public static Message success() {
		Message message=new Message();
		message.setCode(200);
		message.setMsg("成功");
		return message;
	}
	public static Message fail() {
		Message message=new Message();
		message.setCode(500);
		message.setMsg("失败");
		return message;
	}
	public Message addInfo(String key,Object value) {
		getInfo().put(key, value);
		return this;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Map<String, Object> getInfo() {
		return info;
	}
	public void setInfo(Map<String, Object> info) {
		this.info = info;
	}
	
}
