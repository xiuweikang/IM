package com.sdust.im.view;


import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

public class RotatingImageView extends ImageView {

	private int mDegress = 0;

	public RotatingImageView(Context context) {
		super(context);
	}

	public RotatingImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public RotatingImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		int top = getTop();
		int left = getLeft();
		int bottom = getBottom();
		int right = getRight();
		float centerX = (right - left) / 2.0f;
		float centerY = (bottom - top) / 2.0f;
		int i = canvas.save();
		canvas.rotate(mDegress, centerX, centerY);
		super.onDraw(canvas);
		canvas.restoreToCount(i);
	}

	public void setDegress(int degress) {
		mDegress = degress;
		invalidate();
	}
}
