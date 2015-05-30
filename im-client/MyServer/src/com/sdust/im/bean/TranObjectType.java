package com.sdust.im.bean;

/**
 * 传输对象类型
 * 
 * @author way
 * 
 */
public enum TranObjectType {
	REGISTER, // 注册
	REGISTER_ACCOUNT,//注册的账号验证
	LOGIN, // 用户登录
	LOGOUT, // 用户退出登录
	FRIENDLOGIN, // 好友上线
	FRIENDLOGOUT, // 好友下线
	MESSAGE, // 用户发送消息
	UNCONNECTED, // 无法连接
	FILE, // 传输文件
	REFRESH, // 刷新
	SEARCH_FRIEND,//找朋友
	FRIEND_REQUEST;//好友申请
}
