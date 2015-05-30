package com.sdust.im.view;


import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sdust.im.R;

public class SwitcherButton extends LinearLayout {
	private View mView;

	private RelativeLayout mLayoutRoot;
	private LinearLayout mLayoutTab;
	private ImageView mIvLeftImage;
	private TextView mTvLeftText;
	private ImageView mIvRightImage;
	private TextView mTvRightText;

	private LayoutInflater mInflater;

	private onSwitcherButtonClickListener mSwitcherButtonClickListener;
	private SwitcherButtonState mState;

	public SwitcherButton(Context context) {
		super(context);
		init(context);
	}

	public SwitcherButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public SwitcherButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		mInflater = LayoutInflater.from(context);
		mView = mInflater.inflate(R.layout.common_switcher, null);
		addView(mView);
		initViews();
		initEvents();
		mState = SwitcherButtonState.LEFT;
		mTvLeftText.setSelected(true);
	}

	public View findViewBySwitcherId(int id) {
		return mView.findViewById(id);
	}

	private void initViews() {
		mLayoutRoot = (RelativeLayout) findViewBySwitcherId(R.id.switcher_layout_root);
		mLayoutTab = (LinearLayout) findViewBySwitcherId(R.id.switcher_layout_tab);
		mIvLeftImage = (ImageView) findViewBySwitcherId(R.id.switcher_iv_left_image);
		mIvRightImage = (ImageView) findViewBySwitcherId(R.id.switcher_iv_right_image);
		mTvLeftText = (TextView) findViewBySwitcherId(R.id.switcher_tv_left_text);
		mTvRightText = (TextView) findViewBySwitcherId(R.id.switcher_tv_right_text);

	}

	private void initEvents() {
		mLayoutRoot.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mSwitcherButtonClickListener != null) {
					switch (mState) {
					case LEFT:
						mState = SwitcherButtonState.RIGHT;
						mLayoutTab.setGravity(Gravity.RIGHT);
						mTvLeftText.setSelected(false);
						mTvRightText.setSelected(true);
						mSwitcherButtonClickListener.onClick(mState);
						break;

					case RIGHT:
						mState = SwitcherButtonState.LEFT;
						mLayoutTab.setGravity(Gravity.LEFT);
						mTvLeftText.setSelected(true);
						mTvRightText.setSelected(false);
						mSwitcherButtonClickListener.onClick(mState);
						break;
					}
				}
			}
		});
	}

	public void setLeftText(CharSequence text) {
		if (text != null) {
			mTvLeftText.setText(text);
		}
	}

	public void setRightText(CharSequence text) {
		if (text != null) {
			mTvRightText.setText(text);
		}
	}

	public void setLeftImage(int id) {
		if (id > 0) {
			mIvLeftImage.setImageResource(id);
		}
	}

	public void setRightImage(int id) {
		if (id > 0) {
			mIvRightImage.setImageResource(id);
		}
	}

	public void setOnSwitcherButtonClickListener(
			onSwitcherButtonClickListener listener) {
		mSwitcherButtonClickListener = listener;
	}

	public interface onSwitcherButtonClickListener {
		void onClick(SwitcherButtonState state);
	}

	public enum SwitcherButtonState {
		LEFT, RIGHT;
	}
}
