package com.sdust.im.view;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.widget.ImageView;

public class RotateImageView extends ImageView {

	private Bitmap mBitmap;

	public RotateImageView(Context context) {
		super(context);
	}

	public RotateImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public RotateImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void setImageBitmap(Bitmap bm) {
		mBitmap = bm;
		super.setImageBitmap(bm);
	}

	@Override
	public void setImageResource(int resId) {
		mBitmap = BitmapFactory.decodeResource(getResources(), resId);
		super.setImageResource(resId);
	}

	public Bitmap rotate(RotateType type, float degrees) {
		Matrix matrix = new Matrix();
		if (type.equals(RotateType.LEFT)) {
			matrix.setRotate(degrees * -1);
		} else {
			matrix.setRotate(degrees);
		}
		Bitmap bitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(),
				mBitmap.getHeight(), matrix, true);
		setImageBitmap(bitmap);
		return bitmap;
	}

	public Bitmap getImageViewBitmap() {
		return mBitmap;
	}

	public enum RotateType {
		DEFAULT, LEFT, RIGHT;
	}
}
