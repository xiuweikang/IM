package com.sdust.im.activity.imagefactory;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdust.im.R;
import com.sdust.im.util.PhotoUtils;
import com.sdust.im.view.RotateImageView;
import com.sdust.im.view.RotateImageView.RotateType;

public class ImageFactoryFliter extends ImageFactory {

	private RotateImageView mRivImage;

	private String mPath;
	private Bitmap mBitmap;
	private List<FilterItem> mFilterItems;
	private View[] mFliterBlocks;
	private int mSelectBlock = 0;
	private Bitmap mSelectBitmap;

	public ImageFactoryFliter(ImageFactoryActivity activity,
			View contentRootView) {
		super(activity, contentRootView);
	}

	@Override
	public void initViews() {
		mRivImage = (RotateImageView) findViewById(R.id.imagefactory_fliter_riv_image);
	}

	@Override
	public void initEvents() {

	}

	public void Rotate() {
		mSelectBitmap = mRivImage.rotate(RotateType.RIGHT, 90.0f);
	}

	public Bitmap getBitmap() {
		return mSelectBitmap;
	}

	public void init(String path) {
		mPath = path;
		mBitmap = PhotoUtils.getBitmapFromFile(mPath);
		if (mBitmap != null) {
			mSelectBitmap = mBitmap;
			mRivImage.setImageBitmap(mBitmap);
			initFilterList();
			initFilterBlocks();
			refreshBlockBg();
		}
	}

	private void initFilterList() {
		mFilterItems = new ArrayList<ImageFactoryFliter.FilterItem>();
		FilterItem filterItem_1 = new FilterItem(FilterType.默认, "默认");
		FilterItem filterItem_2 = new FilterItem(FilterType.LOMO, "LOMO");
		FilterItem filterItem_3 = new FilterItem(FilterType.纯真, "纯真");
		FilterItem filterItem_4 = new FilterItem(FilterType.重彩, "重彩");
		FilterItem filterItem_5 = new FilterItem(FilterType.维也纳, "维也纳");
		FilterItem filterItem_6 = new FilterItem(FilterType.淡雅, "淡雅");
		FilterItem filterItem_7 = new FilterItem(FilterType.酷, "酷");
		FilterItem filterItem_8 = new FilterItem(FilterType.浓厚, "浓厚");
		mFilterItems.add(filterItem_1);
		mFilterItems.add(filterItem_2);
		mFilterItems.add(filterItem_3);
		mFilterItems.add(filterItem_4);
		mFilterItems.add(filterItem_5);
		mFilterItems.add(filterItem_6);
		mFilterItems.add(filterItem_7);
		mFilterItems.add(filterItem_8);
	}

	private void initFilterBlocks() {
		mFliterBlocks = new View[8];
		mFliterBlocks[0] = findViewById(R.id.imagefactory_fliter_item_1);
		mFliterBlocks[1] = findViewById(R.id.imagefactory_fliter_item_2);
		mFliterBlocks[2] = findViewById(R.id.imagefactory_fliter_item_3);
		mFliterBlocks[3] = findViewById(R.id.imagefactory_fliter_item_4);
		mFliterBlocks[4] = findViewById(R.id.imagefactory_fliter_item_5);
		mFliterBlocks[5] = findViewById(R.id.imagefactory_fliter_item_6);
		mFliterBlocks[6] = findViewById(R.id.imagefactory_fliter_item_7);
		mFliterBlocks[7] = findViewById(R.id.imagefactory_fliter_item_8);
		for (int i = 0; i < mFilterItems.size(); i++) {
			View cover = mFliterBlocks[i].findViewById(R.id.filter_item_cover);
			cover.setTag(i);
			cover.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					mSelectBlock = (Integer) v.getTag();
					refreshBlockBg();
					changeImage();
				}
			});
			ImageView image = (ImageView) mFliterBlocks[i]
					.findViewById(R.id.filter_item_image);
			TextView text = (TextView) mFliterBlocks[i]
					.findViewById(R.id.filter_item_text);
			image.setImageBitmap(PhotoUtils.getFilter(
					mFilterItems.get(i).mFilterType, mBitmap));
			text.setText(mFilterItems.get(i).mFilterName);

		}
	}

	private void refreshBlockBg() {
		for (int i = 0; i < mFilterItems.size(); i++) {
			View cover = mFliterBlocks[i].findViewById(R.id.filter_item_cover);
			if (mSelectBlock == i) {
				cover.setSelected(true);
			} else {
				cover.setSelected(false);
			}
		}
	}

	private void changeImage() {
		mSelectBitmap = PhotoUtils.getFilter(
				mFilterItems.get(mSelectBlock).mFilterType, mBitmap);
		mRivImage.setImageBitmap(mSelectBitmap);
	}

	public class FilterItem {

		public FilterItem(FilterType mFilterType, String mFilterName) {
			super();
			this.mFilterType = mFilterType;
			this.mFilterName = mFilterName;
		}

		public FilterType mFilterType;
		public String mFilterName;
	}

	public enum FilterType {
		默认, LOMO, 纯真, 重彩, 维也纳, 淡雅, 酷, 浓厚;
	}
}
