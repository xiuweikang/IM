package com.sdust.im.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.sdust.im.R;
import com.sdust.im.action.UserAction;
import com.sdust.im.adapter.FriendSearchResultAdapter;
import com.sdust.im.bean.ApplicationData;
import com.sdust.im.bean.TranObject;
import com.sdust.im.bean.User;
import com.sdust.im.global.Result;
import com.sdust.im.view.TitleBarView;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

public class FriendSearchResultActivity extends Activity{


	private ListView mListviewOfResults;
	private TitleBarView mTitleBarView;
	private List<User> mFriendList;
	private User requestee;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_friend_search_result);
		initView();
		initEvent();
	}
	private void initView() {
		mListviewOfResults = (ListView)findViewById(R.id.friend_search_result_listview);
		mTitleBarView = (TitleBarView) findViewById(R.id.title_bar);
		mTitleBarView.setCommonTitle(View.GONE, View.VISIBLE, View.GONE);
		mTitleBarView.setTitleText("查找好友结果");
	}
	private void initEvent() {
		
		
		mFriendList = ApplicationData.getInstance().getFriendSearched();
		System.out.println(mFriendList.size() + "friendSearch result");
	
		mListviewOfResults.setAdapter(new FriendSearchResultAdapter(FriendSearchResultActivity.this,mFriendList));
		
		mListviewOfResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				User mySelf = ApplicationData.getInstance().getUserInfo();
				requestee = mFriendList.get(position);
				
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						FriendSearchResultActivity.this);
				
				alertDialogBuilder.setTitle(null);
				//System.out.println(mySelf.getId() +" "+requestee.getId());
				if(mySelf.getId() == requestee.getId()) {
					alertDialogBuilder
					.setMessage("你不能添加自己为好友")
					.setCancelable(true)
					.setPositiveButton("确定",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							dialog.cancel();
						}
					});
				}
				else if(!hasFriend(ApplicationData.getInstance().getFriendList(), requestee)){
					alertDialogBuilder
					.setMessage("确定发送请求？")
					.setCancelable(true)
					.setPositiveButton("是",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
								UserAction.sendFriendRequest(Result.MAKE_FRIEND_REQUEST,requestee.getId());
						}
					  })
					.setNegativeButton("否",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							dialog.cancel();
					}
					});
				} else {
					alertDialogBuilder
					.setMessage("你们已经是好友")
					.setCancelable(true)
					.setPositiveButton("确定",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							dialog.cancel();
						}
					});
				}

				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
			}
		});
		
		
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		//mInstance = null;
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}
	private boolean hasFriend(List<User> friendList,User person) {
		for(int i = 0;i < friendList.size();i++) {
			if(friendList.get(i).getId() == person.getId())
				return true;
		}
		return false;
	}
	
}
