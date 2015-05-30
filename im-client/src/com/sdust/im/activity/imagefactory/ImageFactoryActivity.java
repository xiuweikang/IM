package com.sdust.im.activity.imagefactory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ViewFlipper;

import com.sdust.im.BaseActivity;
import com.sdust.im.R;
import com.sdust.im.util.PhotoUtils;
import com.sdust.im.view.HeaderLayout;
import com.sdust.im.view.HeaderLayout.HeaderStyle;
import com.sdust.im.view.HeaderLayout.onRightImageButtonClickListener;

public class ImageFactoryActivity extends BaseActivity {
	private HeaderLayout mHeaderLayout;
	private ViewFlipper mVfFlipper;
	private Button mBtnLeft;
	private Button mBtnRight;

	private ImageFactoryCrop mImageFactoryCrop;
	private ImageFactoryFliter mImageFactoryFliter;
	private String mPath;
	private String mNewPath;
	private int mIndex = 0;
	private String mType;

	public static final String TYPE = "type";
	public static final String CROP = "crop";
	public static final String FLITER = "fliter";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_imagefactory);
		initViews();
		initEvents();
		init();
	}

	@Override
	protected void initViews() {
		mHeaderLayout = (HeaderLayout) findViewById(R.id.imagefactory_header);
		mHeaderLayout.init(HeaderStyle.TITLE_RIGHT_IMAGEBUTTON);
		mVfFlipper = (ViewFlipper) findViewById(R.id.imagefactory_vf_viewflipper);
		mBtnLeft = (Button) findViewById(R.id.imagefactory_btn_left);
		mBtnRight = (Button) findViewById(R.id.imagefactory_btn_right);
	}

	@Override
	protected void initEvents() {
		mBtnLeft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mIndex == 0) {
					setResult(RESULT_CANCELED);
					finish();
				} else {
					if (FLITER.equals(mType)) {
						setResult(RESULT_CANCELED);
						finish();
					} else {
						mIndex = 0;
						initImageFactory();
						mVfFlipper.setInAnimation(ImageFactoryActivity.this,
								R.anim.push_right_in);
						mVfFlipper.setOutAnimation(ImageFactoryActivity.this,
								R.anim.push_right_out);
						mVfFlipper.showPrevious();
					}
				}
			}
		});
		mBtnRight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mIndex == 1) {
					mNewPath = PhotoUtils.savePhotoToSDCard(mImageFactoryFliter
							.getBitmap());
					Intent intent = new Intent();
					intent.putExtra("path", mNewPath);
					setResult(RESULT_OK, intent);
					finish();
				} else {
					mNewPath = PhotoUtils.savePhotoToSDCard(mImageFactoryCrop
							.cropAndSave());
					mIndex = 1;
					initImageFactory();
					mVfFlipper.setInAnimation(ImageFactoryActivity.this,
							R.anim.push_left_in);
					mVfFlipper.setOutAnimation(ImageFactoryActivity.this,
							R.anim.push_left_out);
					mVfFlipper.showNext();
				}
			}
		});
	}

	@Override
	public void onBackPressed() {
		if (mIndex == 0) {
			setResult(RESULT_CANCELED);
			finish();
		} else {
			if (FLITER.equals(mType)) {
				setResult(RESULT_CANCELED);
				finish();
			} else {
				mIndex = 0;
				initImageFactory();
				mVfFlipper.setInAnimation(ImageFactoryActivity.this,
						R.anim.push_right_in);
				mVfFlipper.setOutAnimation(ImageFactoryActivity.this,
						R.anim.push_right_out);
				mVfFlipper.showPrevious();
			}
		}
	}

	private void init() {
		mPath = getIntent().getStringExtra("path");
		mType = getIntent().getStringExtra(TYPE);
		mNewPath = new String(mPath);
		if (CROP.equals(mType)) {
			mIndex = 0;
		} else if (FLITER.equals(mType)) {
			mIndex = 1;
			mVfFlipper.showPrevious();
		}
		initImageFactory();
	}

	private void initImageFactory() {
		switch (mIndex) {
		case 0:
			if (mImageFactoryCrop == null) {
				mImageFactoryCrop = new ImageFactoryCrop(this,
						mVfFlipper.getChildAt(0));
			}
			mImageFactoryCrop.init(mPath, mScreenWidth, mScreenHeight);
			mHeaderLayout.setTitleRightImageButton("裁切图片", null,
					R.drawable.ic_topbar_rotation,
					new OnRightImageButtonClickListener());
			mBtnLeft.setText("取    消");
			mBtnRight.setText("确    认");

			break;

		case 1:
			if (mImageFactoryFliter == null) {
				mImageFactoryFliter = new ImageFactoryFliter(this,
						mVfFlipper.getChildAt(1));
			}
			mImageFactoryFliter.init(mNewPath);
			mHeaderLayout.setTitleRightImageButton("图片滤镜", null,
					R.drawable.ic_topbar_rotation,
					new OnRightImageButtonClickListener());
			mBtnLeft.setText("取    消");
			mBtnRight.setText("完    成");
			break;
		}
	}

	private class OnRightImageButtonClickListener implements
			onRightImageButtonClickListener {

		@Override
		public void onClick() {
			switch (mIndex) {
			case 0:
				if (mImageFactoryCrop != null) {
					mImageFactoryCrop.Rotate();
				}
				break;

			case 1:
				if (mImageFactoryFliter != null) {
					mImageFactoryFliter.Rotate();
				}
				break;
			}
		}
	}
}
