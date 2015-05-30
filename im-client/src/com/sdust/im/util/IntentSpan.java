package com.sdust.im.util;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.View.OnClickListener;

public class IntentSpan extends ClickableSpan {
	private final OnClickListener mOnClickListener;

	public IntentSpan(View.OnClickListener listener) {
		mOnClickListener = listener;
	}

	@Override
	public void onClick(View view) {
		mOnClickListener.onClick(view);
	}

	@Override
	public void updateDrawState(TextPaint ds) {
		super.updateDrawState(ds);
		ds.setUnderlineText(true);
	}
}
