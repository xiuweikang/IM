package com.sdust.im.bean;

import java.io.Serializable;
import java.sql.Date;

import com.sdust.im.global.Result;

public class TranObject implements Serializable {

	private static final long serialVersionUID = 1L;
	private Object object;
	private TranObjectType tranType;
	private Result result;
	private String sendTime;
	private int sendId;
	private int receiveId;
	private String sendName;
	public String getSendName() {
		return sendName;
	}
	public void setSendName(String sendName) {
		this.sendName = sendName;
	}
	public TranObject(){
		
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public int getSendId() {
		return sendId;
	}
	public void setSendId(int sendId) {
		this.sendId = sendId;
	}
	public int getReceiveId() {
		return receiveId;
	}
	public void setReceiveId(int receiveId) {
		this.receiveId = receiveId;
	}
	public TranObject(Object object,TranObjectType tranType) {
	
		this.object = object;
		this.tranType = tranType;
	}
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	public TranObjectType getTranType() {
		return tranType;
	}
	public void setTranType(TranObjectType tranType) {
		this.tranType = tranType;
	}
	
	
}
