package com.sdust.im.activity.register;

import java.util.Calendar;
import java.util.Date;

import android.view.View;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;

import com.sdust.im.R;
import com.sdust.im.util.DateUtils;
import com.sdust.im.util.TextUtils;
import com.sdust.im.view.HandyTextView;

public class StepBirthday extends RegisterStep implements OnDateChangedListener {

	private HandyTextView mHtvConstellation;
	private HandyTextView mHtvAge;
	private DatePicker mDpBirthday;
	private Date mBirthday;
	private int mAge;
	private Calendar mCalendar;
	private Date mMinDate;
	private Date mMaxDate;
	private Date mSelectDate;
	private static final int MAX_AGE = 100;
	private static final int MIN_AGE = 12;

	public StepBirthday(RegisterActivity activity, View contentRootView) {
		super(activity, contentRootView);
		initData();

	}

	private void flushBirthday(Calendar calendar) {
		String constellation = TextUtils.getConstellation(
				calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH));
		mSelectDate = calendar.getTime();
		mHtvConstellation.setText(constellation);
		int year = calendar.get(Calendar.YEAR);
		int mnth = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		mAge = TextUtils.getAge(year, mnth, day);
		mBirthday = mSelectDate;
		mHtvAge.setText(mAge + "");
	}

	private void initData() {
		mSelectDate = DateUtils.getDate("19900101");

		Calendar mMinCalendar = Calendar.getInstance();
		Calendar mMaxCalendar = Calendar.getInstance();

		mMinCalendar.set(Calendar.YEAR, mMinCalendar.get(Calendar.YEAR)
				- MIN_AGE);
		mMinDate = mMinCalendar.getTime();
		mMaxCalendar.set(Calendar.YEAR, mMaxCalendar.get(Calendar.YEAR)
				- MAX_AGE);
		mMaxDate = mMaxCalendar.getTime();

		mCalendar = Calendar.getInstance();
		mCalendar.setTime(mSelectDate);
		flushBirthday(mCalendar);
		mDpBirthday.init(mCalendar.get(Calendar.YEAR),
				mCalendar.get(Calendar.MONTH),
				mCalendar.get(Calendar.DAY_OF_MONTH), this);
	}

	@Override
	public void initViews() {
		mHtvConstellation = (HandyTextView) findViewById(R.id.reg_birthday_htv_constellation);
		mHtvAge = (HandyTextView) findViewById(R.id.reg_birthday_htv_age);
		mDpBirthday = (DatePicker) findViewById(R.id.reg_birthday_dp_birthday);
	}

	@Override
	public void initEvents() {

	}

	@Override
	public void doNext() {
		System.out.println(mBirthday);
		mOnNextActionListener.next();
		
	}

	@Override
	public boolean validate() {
		return true;
	}

	@Override
	public boolean isChange() {
		return false;
	}

	@Override
	public void onDateChanged(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		mCalendar = Calendar.getInstance();
		//System.out.println(year + " "+monthOfYear+" "+ dayOfMonth);
		mCalendar.set(year, monthOfYear, dayOfMonth);
		if (mCalendar.getTime().after(mMinDate)
				|| mCalendar.getTime().before(mMaxDate)) {
			mCalendar.setTime(mSelectDate);
			mDpBirthday.init(mCalendar.get(Calendar.YEAR),
					mCalendar.get(Calendar.MONTH),
					mCalendar.get(Calendar.DAY_OF_MONTH), this);
		} else {
			flushBirthday(mCalendar);
		}
	}
	public Date getBirthday(){
		return mBirthday;
	}
	public int getAge() {
		return mAge;
	}

}
