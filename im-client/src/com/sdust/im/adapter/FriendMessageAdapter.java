package com.sdust.im.adapter;

import java.text.SimpleDateFormat;
import java.util.List;

import com.sdust.im.R;
import com.sdust.im.bean.ApplicationData;
import com.sdust.im.bean.MessageTabEntity;
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

public class FriendMessageAdapter extends BaseAdapter {
	private List<MessageTabEntity> mMessageEntities;
	private LayoutInflater mInflater;
	private Context mContext0;

	public FriendMessageAdapter(Context context, List<MessageTabEntity> vector) {
		this.mMessageEntities = vector;
		mInflater = LayoutInflater.from(context);
		mContext0 = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup root) {
		ImageView avatarView;
		TextView nameView;
		TextView unReadCountView;
		TextView sendTimeView;
		TextView contentView;
		MessageTabEntity message = mMessageEntities.get(position);
		Integer senderId = message.getSenderId();
		String name = message.getName();
		Bitmap photo = (ApplicationData.getInstance().getFriendPhotoMap())
				.get(senderId);
		int messageType = message.getMessageType();
		String sendTime = message.getSendTime();
		int unReadCount = message.getUnReadCount();
		String content = message.getContent();
		convertView = mInflater.inflate(R.layout.fragment_message_item, null);
		avatarView = (ImageView) convertView.findViewById(R.id.user_photo);
		nameView = (TextView) convertView.findViewById(R.id.user_name);
		contentView = (TextView) convertView.findViewById(R.id.user_message);
		unReadCountView = (TextView) convertView
				.findViewById(R.id.unread_message_count);
		sendTimeView = (TextView) convertView.findViewById(R.id.send_time);
		if (unReadCount == 0) {
			unReadCountView.setVisibility(View.GONE);
		} else if (unReadCount > 9) {
			unReadCountView.setText("9+");
		} else {

			unReadCountView.setText(message.getUnReadCount() + "");
		}
		if (photo != null) {
			avatarView.setImageBitmap(photo);
		}
		nameView.setText(name);
		sendTimeView.setText(sendTime);
		// System.out.println(messageType + "message类型");

		contentView.setText(content);

		return convertView;
	}

	public int getCount() {
		return mMessageEntities.size();
	}

	public Object getItem(int position) {
		return mMessageEntities.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

}
