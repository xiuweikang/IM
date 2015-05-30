package com.sdust.im.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.sdust.im.R;
import com.sdust.im.view.HeaderSpinner.onSpinnerClickListener;
import com.sdust.im.view.SwitcherButton.onSwitcherButtonClickListener;

public class HeaderLayout extends LinearLayout {

	private LayoutInflater mInflater;
	private View mHeader;
	private ImageView mIvLogo;
	private LinearLayout mLayoutLeftContainer;
	private LinearLayout mLayoutMiddleContainer;
	private LinearLayout mLayoutRightContainer;

	// 标题
	private LinearLayout mLayoutTitle;
	private ScrollingTextView mStvTitle;
	private HandyTextView mHtvSubTitle;

	// 搜索
	private RelativeLayout mLayoutSearch;
	private EditText mEtSearch;
	private Button mBtnSearchClear;
	private ImageView mIvSearchLoading;
	private onSearchListener mOnSearchListener;

	// 右边文本
	private HandyTextView mHtvRightText;

	// 右边按钮
	private LinearLayout mLayoutRightImageButtonLayout;
	private ImageButton mIbRightImageButton;
	private onRightImageButtonClickListener mRightImageButtonClickListener;

	private HeaderSpinner mHsSpinner;
	private LinearLayout mLayoutMiddleImageButtonLayout;
	private ImageButton mIbMiddleImageButton;
	private ImageView mIvMiddleLine;
	private onMiddleImageButtonClickListener mMiddleImageButtonClickListener;

	private SwitcherButton mSbRightSwitcherButton;
	private onSwitcherButtonClickListener mSwitcherButtonClickListener;

	public HeaderLayout(Context context) {
		super(context);
		init(context);
	}

	public HeaderLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public void init(Context context) {
		mInflater = LayoutInflater.from(context);
		mHeader = mInflater.inflate(R.layout.common_headerbar, null);
		addView(mHeader);
		initViews();

	}

	public void initViews() {
		mIvLogo = (ImageView) findViewByHeaderId(R.id.header_iv_logo);
		mIvLogo.setVisibility(View.VISIBLE);
		mLayoutLeftContainer = (LinearLayout) findViewByHeaderId(R.id.header_layout_leftview_container);
		mLayoutLeftContainer.setVisibility(View.VISIBLE);
		mLayoutMiddleContainer = (LinearLayout) findViewByHeaderId(R.id.header_layout_middleview_container);
		mLayoutRightContainer = (LinearLayout) findViewByHeaderId(R.id.header_layout_rightview_container);
		mLayoutTitle = (LinearLayout) findViewByHeaderId(R.id.header_layout_title);
		mStvTitle = (ScrollingTextView) findViewByHeaderId(R.id.header_stv_title);
		mHtvSubTitle = (HandyTextView) findViewByHeaderId(R.id.header_htv_subtitle);

		mLayoutSearch = (RelativeLayout) findViewByHeaderId(R.id.header_layout_search);
		mEtSearch = (EditText) findViewByHeaderId(R.id.header_et_search);
		mBtnSearchClear = (Button) findViewByHeaderId(R.id.header_btn_searchclear);
		mIvSearchLoading = (ImageView) findViewByHeaderId(R.id.header_iv_searchloading);

		mHsSpinner = (HeaderSpinner) findViewByHeaderId(R.id.header_hs_spinner);
		mLayoutMiddleImageButtonLayout = (LinearLayout) findViewByHeaderId(R.id.header_layout_middle_imagebuttonlayout);
		mIbMiddleImageButton = (ImageButton) findViewByHeaderId(R.id.header_ib_middle_imagebutton);
		mIvMiddleLine=(ImageView)findViewByHeaderId(R.id.header_iv_middle_line);
		
		mSbRightSwitcherButton = (SwitcherButton) findViewByHeaderId(R.id.header_sb_rightview_switcherbutton);
	}

	public View findViewByHeaderId(int id) {
		return mHeader.findViewById(id);
	}

	public void init(HeaderStyle style) {
		switch (style) {
		case DEFAULT_TITLE:
			defaultTitle();
			break;

		case TITLE_RIGHT_TEXT:
			titleRightText();
			break;

		case TITLE_RIGHT_IMAGEBUTTON:
			titleRightImageButton();
			break;

		case TITLE_NEARBY_PEOPLE:
			titleNearBy(true);
			break;

		case TITLE_NEARBY_GROUP:
			titleNearBy(false);
			break;

		case TITLE_CHAT:
			titleChat();
			break;
		}
	}

	/**
	 * 默认只有标题
	 */
	private void defaultTitle() {
		mLayoutTitle.setVisibility(View.VISIBLE);
		mLayoutMiddleContainer.removeAllViews();
		mLayoutRightContainer.removeAllViews();
	}

	/**
	 * 添加默认标题内容
	 * 
	 * @param title
	 * @param subTitle
	 */
	public void setDefaultTitle(CharSequence title, CharSequence subTitle) {
		if (title != null) {
			mStvTitle.setText(title);
		} else {
			mStvTitle.setVisibility(View.GONE);
		}
		if (subTitle != null) {
			mHtvSubTitle.setText(subTitle);
		} else {
			mHtvSubTitle.setVisibility(View.GONE);
		}
	}

	/**
	 * 标题以及右边有文本内容
	 */
	private void titleRightText() {
		mLayoutTitle.setVisibility(View.VISIBLE);
		mLayoutMiddleContainer.removeAllViews();
		mLayoutRightContainer.removeAllViews();
		View mRightText = mInflater.inflate(R.layout.include_header_righttext,
				null);
		mLayoutRightContainer.addView(mRightText);
		mHtvRightText = (HandyTextView) mRightText
				.findViewById(R.id.header_htv_righttext);
	}

	/**
	 * 添加标题以及右边文本内容
	 * 
	 * @param title
	 * @param subTitle
	 * @param rightText
	 */
	public void setTitleRightText(CharSequence title, CharSequence subTitle,
			CharSequence rightText) {
		setDefaultTitle(title, subTitle);
		if (mHtvRightText != null && rightText != null) {
			mHtvRightText.setText(rightText);
		}
	}

	/**
	 * 标题以及右边图片按钮
	 */
	private void titleRightImageButton() {
		mLayoutTitle.setVisibility(View.VISIBLE);
		mLayoutMiddleContainer.removeAllViews();
		mLayoutRightContainer.removeAllViews();
		View mRightImageButton = mInflater.inflate(
				R.layout.include_header_rightimagebutton, null);
		mLayoutRightContainer.addView(mRightImageButton);
		mLayoutRightImageButtonLayout = (LinearLayout) mRightImageButton
				.findViewById(R.id.header_layout_right_imagebuttonlayout);
		mIbRightImageButton = (ImageButton) mRightImageButton
				.findViewById(R.id.header_ib_right_imagebutton);
		mLayoutRightImageButtonLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (mRightImageButtonClickListener != null) {
					mRightImageButtonClickListener.onClick();
				}
			}
		});
	}

	public void setTitleRightImageButton(CharSequence title,
			CharSequence subTitle, int id,
			onRightImageButtonClickListener listener) {
		setDefaultTitle(title, subTitle);
		if (mIbRightImageButton != null && id > 0) {
			mIbRightImageButton.setImageResource(id);
			setOnRightImageButtonClickListener(listener);
		}
	}

	/**
	 * 附近标题
	 */
	private void titleNearBy(boolean isPeople) {
		mSbRightSwitcherButton.setVisibility(View.VISIBLE);
		if (isPeople) {
			mLayoutTitle.setVisibility(View.GONE);
			mHsSpinner.setVisibility(View.VISIBLE);
			mLayoutMiddleImageButtonLayout.setVisibility(View.GONE);
		} else {
			mLayoutTitle.setVisibility(View.VISIBLE);
			mHsSpinner.setVisibility(View.GONE);
			mLayoutMiddleImageButtonLayout.setVisibility(View.VISIBLE);
		}
	}

	public HeaderSpinner setTitleNearBy(CharSequence spinnerText,
			onSpinnerClickListener spinnerClickListener, CharSequence title,
			int middleImageId,
			onMiddleImageButtonClickListener middleImageButtonClickListener,
			CharSequence switcherLeftText, CharSequence switcherRightText,
			onSwitcherButtonClickListener switcherButtonClickListener) {

		mHsSpinner.setText(spinnerText);
		mHsSpinner.setOnSpinnerClickListener(spinnerClickListener);
		setDefaultTitle(title, null);
		if (middleImageId > 0) {
			mIbMiddleImageButton.setImageResource(middleImageId);
		}
		mMiddleImageButtonClickListener = middleImageButtonClickListener;
		mLayoutMiddleImageButtonLayout
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (mMiddleImageButtonClickListener != null) {
							mMiddleImageButtonClickListener.onClick();
						}
					}
				});
		mSbRightSwitcherButton.setLeftText(switcherLeftText);
		mSbRightSwitcherButton.setRightText(switcherRightText);
		mSwitcherButtonClickListener = switcherButtonClickListener;
		mSbRightSwitcherButton
				.setOnSwitcherButtonClickListener(mSwitcherButtonClickListener);
		return mHsSpinner;
	}

	public void showSearch() {
		mLayoutSearch.setVisibility(View.VISIBLE);
		mEtSearch.requestFocus();
		mLayoutTitle.setVisibility(View.GONE);
		mLayoutMiddleContainer.setVisibility(View.GONE);
		mLayoutRightContainer.setVisibility(View.GONE);
	}

	public void dismissSearch() {
		mLayoutSearch.setVisibility(View.GONE);
		mLayoutTitle.setVisibility(View.VISIBLE);
		mLayoutMiddleContainer.setVisibility(View.VISIBLE);
		mLayoutRightContainer.setVisibility(View.VISIBLE);
	}

	public boolean searchIsShowing() {
		if (mLayoutSearch.getVisibility() == View.VISIBLE) {
			return true;
		}
		return false;
	}

	public void clearSearch() {
		mEtSearch.setText(null);
	}

	public void initSearch(onSearchListener listener) {
		dismissSearch();
		setOnSearchListener(listener);
		mEtSearch.setText(null);
		changeSearchState(SearchState.INPUT);
		mBtnSearchClear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				clearSearch();
			}
		});
		mEtSearch.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_ENTER
						&& event.getRepeatCount() == 0) {
					if (mOnSearchListener != null) {
						mOnSearchListener.onSearch(mEtSearch);
					}
				}
				return false;
			}
		});
	}

	public void changeSearchState(SearchState state) {
		switch (state) {
		case INPUT:
			mBtnSearchClear.setVisibility(View.VISIBLE);
			mIvSearchLoading.clearAnimation();
			mIvSearchLoading.setVisibility(View.GONE);
			break;

		case SEARCH:
			mBtnSearchClear.setVisibility(View.GONE);
			Animation anim = AnimationUtils.loadAnimation(getContext(),
					R.anim.loading);
			mIvSearchLoading.clearAnimation();
			mIvSearchLoading.startAnimation(anim);
			mIvSearchLoading.setVisibility(View.VISIBLE);
			break;
		}
	}

	private void titleChat() {
		mLayoutTitle.setVisibility(View.VISIBLE);
		mLayoutRightContainer.removeAllViews();
		mIvMiddleLine.setVisibility(View.GONE);
		View mRightImageButton = mInflater.inflate(
				R.layout.include_header_rightimagebutton, null);
		mLayoutRightContainer.addView(mRightImageButton);
		mLayoutRightImageButtonLayout = (LinearLayout) mRightImageButton
				.findViewById(R.id.header_layout_right_imagebuttonlayout);
		mIbRightImageButton = (ImageButton) mRightImageButton
				.findViewById(R.id.header_ib_right_imagebutton);
		mLayoutRightImageButtonLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (mRightImageButtonClickListener != null) {
					mRightImageButtonClickListener.onClick();
				}
			}
		});
		mLayoutMiddleImageButtonLayout
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (mMiddleImageButtonClickListener != null) {
							mMiddleImageButtonClickListener.onClick();
						}
					}
				});
	}

	public void setTitleChat(int iconImageId, int iconImageBg,
			CharSequence title, CharSequence subTitle, int middleImageId,
			onMiddleImageButtonClickListener middleImageButtonClickListener,
			int rightImageId,
			onRightImageButtonClickListener rightImageButtonClickListener) {
		mIvLogo.setImageResource(iconImageId);
		mIvLogo.setBackgroundResource(iconImageBg);
		setDefaultTitle(title, subTitle);
		mIbMiddleImageButton.setImageResource(middleImageId);
		mIbRightImageButton.setImageResource(rightImageId);
		mMiddleImageButtonClickListener = middleImageButtonClickListener;
		mRightImageButtonClickListener = rightImageButtonClickListener;

	}

	public enum HeaderStyle {
		DEFAULT_TITLE, TITLE_RIGHT_TEXT, TITLE_RIGHT_IMAGEBUTTON, TITLE_NEARBY_PEOPLE, TITLE_NEARBY_GROUP, TITLE_CHAT;
	}

	public enum SearchState {
		INPUT, SEARCH;
	}

	public void setOnSearchListener(onSearchListener onSearchListener) {
		mOnSearchListener = onSearchListener;
	}

	public void setOnMiddleImageButtonClickListener(
			onMiddleImageButtonClickListener listener) {
		mMiddleImageButtonClickListener = listener;
	}

	public void setOnRightImageButtonClickListener(
			onRightImageButtonClickListener listener) {
		mRightImageButtonClickListener = listener;
	}

	public interface onMiddleImageButtonClickListener {
		void onClick();
	}

	public interface onRightImageButtonClickListener {
		void onClick();
	}

	public interface onSearchListener {
		void onSearch(EditText et);
	}
}
