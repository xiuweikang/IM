package com.sdust.im.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.sdust.im.DataBase.DBPool;
import com.sdust.im.DataBase.FriendDao;
import com.sdust.im.DataBase.SaveMsgDao;
import com.sdust.im.DataBase.UserDao;
import com.sdust.im.bean.TranObject;
import com.sdust.im.bean.TranObjectType;
import com.sdust.im.bean.User;
import com.sdust.im.global.Result;
import com.sdust.im.server.ServerListen;

/**
 * @author Administrator 客户端线程
 */
public class ClientActivity {
	/*  发送队列， 因为服务器有多个监听客户端的线程，当很多好友一起向他发送消息，每个服务器线程
	   都同时调用此实例的socket争夺send ，并发控制异常。*/
	private LinkedList<TranObject> sendQueue;
	private ServerListen mServer; // 服务器
	private User user;
	private Socket mClient; // 客户端连接
	private ClientListenThread mClientListen; // 客户端监听进程
	private ClientSendThread mClientSend; // 客户端发送进程
	private ObjectOutputStream mOutput;
	private ObjectInputStream mInput;

	public ClientActivity(ServerListen mServer, Socket mClient) {
		user = new User();
		sendQueue = new LinkedList<TranObject>();
		this.mServer = mServer;
		this.mClient = mClient;
		try {
			mOutput = new ObjectOutputStream(mClient.getOutputStream());
			mInput = new ObjectInputStream(mClient.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		mClientListen = new ClientListenThread(mInput, this);
		mClientSend = new ClientSendThread(this);
		Thread listen = new Thread(mClientListen);
		Thread send = new Thread(mClientSend);
		listen.start();
		send.start();
	}

	public Socket getmClient() {
		return mClient;
	}

	public void setmClient(Socket mClient) {
		this.mClient = mClient;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user.setAccount(user.getAccount());
		this.user.setAge(user.getAge());
		this.user.setBirthday(user.getBirthday());
		this.user.setGender(user.getGender());
		this.user.setLocation(user.getLocation());
		this.user.setId(user.getId());
		this.user.setUserName(user.getUserName());
		this.user.setPhoto(user.getPhoto());
	}

	/**
	 * 检查注册账号是否存在
	 */
	public void checkAccount(String account) {
		mServer.addClient(user.getId(), this);
		boolean isExisted = UserDao.selectAccount(account);
		TranObject tran = new TranObject("", TranObjectType.REGISTER_ACCOUNT);
		if (isExisted)
			tran.setResult(Result.ACCOUNT_EXISTED);
		else
			tran.setResult(Result.ACCOUNT_CAN_USE);
		send(tran);
	}

	/**
	 * 检查账号和用户名是否存在
	 */


	public void login(TranObject tran) {
		User user = (User) tran.getObject();
		// 验证密码和用户名是否存在，若存在则为user对象赋值
		boolean isExisted = UserDao.login(user);
		if (isExisted == true) {
			UserDao.updateIsOnline(user.getId(), 1);
			setUser(user);
			System.out.println(user.getAccount() + "上线了");
			tran.setResult(Result.LOGIN_SUCCESS);
			mServer.addClient(user.getId(), this);
			System.out.println("当前在线人数：" + mServer.size());
			// 获取好友列表
			ArrayList<User> friendList = FriendDao.getFriend(user.getId());
			user.setFriendList(friendList);

			tran.setObject(user);

		} else
			tran.setResult(Result.LOGIN_FAILED);
		send(tran);
		/*try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		// 获取离线信息
		ArrayList<TranObject> offMsg = SaveMsgDao.selectMsg(user.getId());
		for (int i = 0; i < offMsg.size(); i++)
			insertQueue(offMsg.get(i));
		SaveMsgDao.deleteSaveMsg(user.getId());

	}

	public synchronized void send(TranObject tran) {
		try {
			mOutput.writeObject(tran);
			mOutput.flush();
			notify();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 注册
	 */
	public void regist(TranObject tran) {
		User user = (User) tran.getObject();
		int id = UserDao.insertInfo(user);
		user.setId(id);
		if (id == -1)
			tran.setResult(Result.REGISTER_FAILED);
		else
			tran.setResult(Result.REGISTER_SUCCESS);
		System.out.println("发送注册结果...");
		send(tran);
	}

	/**
	 * 客户端下线
	 */
	public void getOffLine() {
		mServer.closeClientByID(user.getId());
		UserDao.updateIsOnline(user.getId(), 0);
	}

	/**
	 * 关闭与客户端的连接
	 */
	public void close() {
		try {
			mClient.close();// socket关闭后，他所在的流也都自动关闭
			mClientListen.close();
			mClientSend.close();
			if (user.getId() != 0)
				getOffLine();
			System.out.println(user.getAccount() + "下线了...");
		} catch (IOException e) {
			System.out.println("关闭失败.....");
			e.printStackTrace();
		}
	}

	/**
	 * 查找朋友
	 */
	public void searchFriend(TranObject tran) {
		String values[] = ((String) tran.getObject()).split(" ");
		ArrayList<User> list;
		if (values[0].equals("0"))
			list = UserDao.selectFriendByAccountOrID(values[1]);
		else
			list = UserDao.selectFriendByMix(values);
		System.out.println((String) tran.getObject());
		System.out.println("发送客户端查找的好友列表...");
		for (int i = 0; i < list.size(); i++)
			System.out.println(list.get(i));
		tran.setObject(list);
		send(tran);
	}

	/**
	 * 处理好友请求
	 */
	public void friendRequset(TranObject tran) {
		System.out.println("添加好友");
		Result result = tran.getResult();
		if (result == Result.FRIEND_REQUEST_RESPONSE_ACCEPT) {
			System.out.println("接收方id" + tran.getReceiveId());
			FriendDao.addFriend(tran.getReceiveId(), tran.getSendId());
			FriendDao.addFriend(tran.getSendId(), tran.getReceiveId());
			System.out.println("添加好友成功....");
			// 向好友发起方 发送自己的信息
			tran.setObject(user);
			ArrayList<User> friend = UserDao.selectFriendByAccountOrID(tran
					.getSendId());
			tran.setObject(friend.get(0));
			tran.setSendName(user.getUserName());
			// 向自己添加好友
			friend = UserDao.selectFriendByAccountOrID(tran.getReceiveId());
			TranObject tran2 = new TranObject();
			tran2.setObject(friend.get(0));
			tran2.setResult(tran.getResult());
			tran2.setReceiveId(tran.getSendId());
			tran2.setSendId(tran.getReceiveId());
			tran2.setSendName(friend.get(0).getUserName());
			tran2.setTranType(tran.getTranType());
			tran2.setSendTime(tran2.getSendTime());
			send(tran2);
		}
		sendFriend(tran);
	}

	/**
	 * 转发消息 将转发的消息发送到 服务器与该客户端连接的 发送队列中
	 */
	public void sendFriend(TranObject tran) {
		ClientActivity friendClient = null;
		System.out.println("包含要发送的那个好友吗？" + tran.getReceiveId()
				+ mServer.contatinId(tran.getReceiveId()));
		if (mServer.contatinId(tran.getReceiveId())) {
			friendClient = mServer.getClientByID(tran.getReceiveId());
			System.out.println("将好友请求发给好友...");
			friendClient.insertQueue(tran);
		} else {
			SaveMsgDao.insertSaveMsg(user.getId(), tran);
		}

	}

	public void sendMessage(TranObject tran) {
		// 添加到好友的发送队列
		System.out.println("发送聊天信息....");
		sendFriend(tran);
	}

	/******************************** 对发送队列的异步处理 ***********************************/
	/**
	 * 发送数据 如果是从好友那里发送来的 就先添加到队列 并发控制，因为同步性太强 否则直接发送； 属于发送线程
	 */
	public synchronized void insertQueue(TranObject tran) {
		sendQueue.add(tran);
	}

	public synchronized int sizeOfQueue() {
		return sendQueue.size();
	}

	public synchronized TranObject removeQueueEle(int i) {
		return sendQueue.remove(i);
	}
}
