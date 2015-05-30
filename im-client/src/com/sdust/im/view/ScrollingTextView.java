package com.sdust.im.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;

public class ScrollingTextView extends HandyTextView implements OnClickListener {

	public ScrollingTextView(Context context) {
		super(context);
		init();
	}

	public ScrollingTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ScrollingTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public void init() {
		setLines(1);
		setFocusable(true);
		setFocusableInTouchMode(true);
		setEllipsize(TextUtils.TruncateAt.MARQUEE);

	}

	@Override
	public boolean isFocused() {
		return true;
	}

	@Override
	public void onClick(View v) {
		setEllipsize(TextUtils.TruncateAt.MARQUEE);
		invalidate();
	}

}
