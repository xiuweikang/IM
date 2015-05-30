package com.sdust.im.bean;

import java.sql.Date;

/**
 * 类名：MessageEntity 说明：messageFragment显示的消息
 */
public class MessageTabEntity {
	public static final int MAKE_FRIEND_REQUEST = 0;// 收到的是交友的请求
	public static final int MAKE_FRIEND_RESPONSE_ACCEPT = 1;//收到回复，对方接受
	public static final int MAKE_FRIEND_RESPONSE_REJECT = 2;//收到回复，对方拒绝
	public static final int FRIEND_MESSAGE = 3;// 收到的是朋友的信息
	private int unReadCount;
	private int senderId;// 发送方的Id
	private String content;
	private int messageType;
	private String sendTime;
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	private byte[] photo;

	public int getUnReadCount() {
		return unReadCount;
	}

	public void setUnReadCount(int count) {
		this.unReadCount = count;
	}

	public int getSenderId() {
		return senderId;
	}

	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getMessageType() {
		return messageType;
	}

	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}

}
