package com.sdust.im.adapter;

import java.util.List;

import com.sdust.im.R;
import com.sdust.im.bean.ApplicationData;
import com.sdust.im.bean.User;
import com.sdust.im.util.PhotoUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FriendListAdapter extends BaseAdapter {
	private List<User> mFriendList;
	private LayoutInflater mInflater;

	public FriendListAdapter(Context context, List<User> vector) {
		this.mFriendList = vector;
		mInflater = LayoutInflater.from(context);
		System.out.println("初始化FriendAdapter");
	}
	@Override
	public View getView(int position, View convertView, ViewGroup root) {
		ImageView avatarView;
		TextView nameView;
		ImageView isOnline;
		TextView introView;
		User user = mFriendList.get(position);
		Bitmap photo = (ApplicationData.getInstance().getFriendPhotoMap()).get(user.getId());
		String name = user.getUserName();
		String briefIntro = user.getUserBriefIntro();
		convertView = mInflater.inflate(R.layout.friend_list_item,
				null);
		avatarView = (ImageView) convertView
				.findViewById(R.id.user_photo);
		nameView = (TextView) convertView
				.findViewById(R.id.friend_list_name);
		isOnline = (ImageView)convertView.findViewById(R.id.stateicon);
		
		introView = (TextView) convertView
				.findViewById(R.id.friend_list_brief);
		
		nameView.setText(name);
		
		if(!user.isOnline()) {
			isOnline.setVisibility(View.GONE);
			
		}
		if (photo != null) {
			avatarView.setImageBitmap(photo);
		}
		introView.setText(briefIntro);
	

		return convertView;
	}

	public int getCount() {
		return mFriendList.size();
	}

	public Object getItem(int position) {
		return mFriendList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

}
