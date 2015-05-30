package com.sdust.im.view;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class CropImageView extends ImageViewTouchBase {
	public ArrayList<HighlightView> mHighlightViews = new ArrayList<HighlightView>();
	HighlightView mMotionHighlightView = null;
	float mLastX, mLastY;
	int mMotionEdge;

	private CropImage mCropImage;

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		if (mBitmapDisplayed.getBitmap() != null) {
			for (HighlightView hv : mHighlightViews) {
				hv.mMatrix.set(getImageMatrix());
				hv.invalidate();
				if (hv.mIsFocused) {
					centerBasedOnHighlightView(hv);
				}
			}
		}
	}

	public CropImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void zoomTo(float scale, float centerX, float centerY) {
		super.zoomTo(scale, centerX, centerY);
		for (HighlightView hv : mHighlightViews) {
			hv.mMatrix.set(getImageMatrix());
			hv.invalidate();
		}
	}

	@Override
	protected void zoomIn() {
		super.zoomIn();
		for (HighlightView hv : mHighlightViews) {
			hv.mMatrix.set(getImageMatrix());
			hv.invalidate();
		}
	}

	@Override
	protected void zoomOut() {
		super.zoomOut();
		for (HighlightView hv : mHighlightViews) {
			hv.mMatrix.set(getImageMatrix());
			hv.invalidate();
		}
	}

	@Override
	protected void postTranslate(float deltaX, float deltaY) {
		super.postTranslate(deltaX, deltaY);
		for (int i = 0; i < mHighlightViews.size(); i++) {
			HighlightView hv = mHighlightViews.get(i);
			hv.mMatrix.postTranslate(deltaX, deltaY);
			hv.invalidate();
		}
	}

	// According to the event's position, change the focus to the first
	// hitting cropping rectangle.
	private void recomputeFocus(MotionEvent event) {
		for (int i = 0; i < mHighlightViews.size(); i++) {
			HighlightView hv = mHighlightViews.get(i);
			hv.setFocus(false);
			hv.invalidate();
		}

		for (int i = 0; i < mHighlightViews.size(); i++) {
			HighlightView hv = mHighlightViews.get(i);
			int edge = hv.getHit(event.getX(), event.getY());
			if (edge != HighlightView.GROW_NONE) {
				if (!hv.hasFocus()) {
					hv.setFocus(true);
					hv.invalidate();
				}
				break;
			}
		}
		invalidate();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		CropImage cropImage = mCropImage;
		if (cropImage.mSaving) {
			return false;
		}

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: // CR: inline case blocks.
			if (cropImage.mWaitingToPick) {
				recomputeFocus(event);
			} else {
				for (int i = 0; i < mHighlightViews.size(); i++) { // CR:
																	// iterator
																	// for; if
																	// not, then
																	// i++ =>
																	// ++i.
					HighlightView hv = mHighlightViews.get(i);
					int edge = hv.getHit(event.getX(), event.getY());
					if (edge != HighlightView.GROW_NONE) {
						mMotionEdge = edge;
						mMotionHighlightView = hv;
						mLastX = event.getX();
						mLastY = event.getY();
						// CR: get rid of the extraneous parens below.
						mMotionHighlightView
								.setMode((edge == HighlightView.MOVE) ? HighlightView.ModifyMode.Move
										: HighlightView.ModifyMode.Grow);
						break;
					}
				}
			}
			break;
		// CR: vertical space before case blocks.
		case MotionEvent.ACTION_UP:
			if (cropImage.mWaitingToPick) {
				for (int i = 0; i < mHighlightViews.size(); i++) {
					HighlightView hv = mHighlightViews.get(i);
					if (hv.hasFocus()) {
						cropImage.mCrop = hv;
						for (int j = 0; j < mHighlightViews.size(); j++) {
							if (j == i) { // CR: if j != i do your shit; no need
											// for continue.
								continue;
							}
							mHighlightViews.get(j).setHidden(true);
						}
						centerBasedOnHighlightView(hv);
						mCropImage.mWaitingToPick = false;
						return true;
					}
				}
			} else if (mMotionHighlightView != null) {
				centerBasedOnHighlightView(mMotionHighlightView);
				mMotionHighlightView.setMode(HighlightView.ModifyMode.None);
			}
			mMotionHighlightView = null;
			break;
		case MotionEvent.ACTION_MOVE:
			if (cropImage.mWaitingToPick) {
				recomputeFocus(event);
			} else if (mMotionHighlightView != null) {
				mMotionHighlightView.handleMotion(mMotionEdge, event.getX()
						- mLastX, event.getY() - mLastY);
				mLastX = event.getX();
				mLastY = event.getY();

				if (true) {
					// This section of code is optional. It has some user
					// benefit in that moving the crop rectangle against
					// the edge of the screen causes scrolling but it means
					// that the crop rectangle is no longer fixed under
					// the user's finger.
					ensureVisible(mMotionHighlightView);
				}
			}
			break;
		}

		switch (event.getAction()) {
		case MotionEvent.ACTION_UP:
			center(true, true);
			break;
		case MotionEvent.ACTION_MOVE:
			// if we're not zoomed then there's no point in even allowing
			// the user to move the image around. This call to center puts
			// it back to the normalized location (with false meaning don't
			// animate).
			// if (getScale() == 1F) {
			center(true, true);
			// }
			break;
		}

		return true;
	}

	// Pan the displayed image to make sure the cropping rectangle is visible.
	private void ensureVisible(HighlightView hv) {
		Rect r = hv.mDrawRect;

		int panDeltaX1 = Math.max(0, getLeft() - r.left);
		int panDeltaX2 = Math.min(0, getRight() - r.right);

		int panDeltaY1 = Math.max(0, getTop() - r.top);
		int panDeltaY2 = Math.min(0, getBottom() - r.bottom);

		int panDeltaX = panDeltaX1 != 0 ? panDeltaX1 : panDeltaX2;
		int panDeltaY = panDeltaY1 != 0 ? panDeltaY1 : panDeltaY2;

		if (panDeltaX != 0 || panDeltaY != 0) {
			panBy(panDeltaX, panDeltaY);
		}
	}

	// If the cropping rectangle's size changed significantly, change the
	// view's center and scale according to the cropping rectangle.
	// hv.mDrawRect.width<0.54*thisWidth||width>0.66*thisWidth,need to zoom
	private void centerBasedOnHighlightView(HighlightView hv) {
		Rect drawRect = hv.mDrawRect;

		float width = drawRect.width();
		float height = drawRect.height();

		float thisWidth = getWidth();
		float thisHeight = getHeight();

		float z1 = thisWidth / width * .6F;
		float z2 = thisHeight / height * .6F;

		float zoom = Math.min(z1, z2);
		zoom = zoom * this.getScale();
		zoom = Math.max(1F, zoom);// assure getScale()>1

		if ((Math.abs(zoom - getScale()) / zoom) > 0.1) {
			float[] coordinates = new float[] { hv.mCropRect.centerX(),
					hv.mCropRect.centerY() };
			getImageMatrix().mapPoints(coordinates);
			zoomTo(zoom, coordinates[0], coordinates[1], 300F); // CR: 300.0f.
		}

		ensureVisible(hv);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		for (int i = 0; i < mHighlightViews.size(); i++) {
			mHighlightViews.get(i).draw(canvas);
		}
	}

	public void add(HighlightView hv) {
		mHighlightViews.clear();
		mHighlightViews.add(hv);
		invalidate();
	}

	public void setCropImage(CropImage cropImage) {
		mCropImage = cropImage;
	}

	public void resetView(Bitmap b) {
		setImageBitmap(b);
		setImageBitmapResetBase(b, true);
		setImageMatrix(getImageViewMatrix());
		int width = mBitmapDisplayed.getWidth();
		int height = mBitmapDisplayed.getHeight();
		Rect imageRect = new Rect(0, 0, width, height);
		int cropWidth = Math.min(width, height) * 4 / 5;
		int cropHeight = cropWidth;
		int x = (width - cropWidth) / 2;
		int y = (height - cropHeight) / 2;
		RectF cropRect = new RectF(x, y, x + cropWidth, y + cropHeight);
		HighlightView hv = new HighlightView(this);
		hv.setup(getImageViewMatrix(), imageRect, cropRect, false, true);
		hv.setFocus(true);
		add(hv);
		centerBasedOnHighlightView(hv);
		hv.setMode(HighlightView.ModifyMode.None);
		center(true, true);
		invalidate();
	}

}
