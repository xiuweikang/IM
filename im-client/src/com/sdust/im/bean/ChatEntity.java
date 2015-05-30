package com.sdust.im.bean;

import java.io.Serializable;

public class ChatEntity implements Serializable{
	public static final int  RECEIVE = 0;
	public static final int SEND = 1;
	private int senderId;
	private int receiverId;
	private String sendDate;
	private int messageType;
	private String content;
	public int getSenderId() {
		return senderId;
	}
	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}
	public int getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(int receiverId) {
		this.receiverId = receiverId;
	}
	
	public int getMessageType() {
		return messageType;
	}
	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}
	public String getSendTime() {
		return sendDate;
	}
	public void setSendTime(String sendDate) {
		this.sendDate = sendDate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
