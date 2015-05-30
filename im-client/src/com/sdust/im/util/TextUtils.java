package com.sdust.im.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import android.content.Context;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View.OnClickListener;

import com.sdust.im.R;
import com.sdust.im.view.HandyTextView;


public class TextUtils {
	/**
	 * 添加下划线
	 * 
	 * @param context
	 *            上下文
	 * @param textView
	 *            添加下划线的TextView
	 * @param start
	 *            添加下划线开始的位置
	 * @param end
	 *            添加下划线结束的位置
	 */
	public static void addUnderlineText(final Context context,
			final HandyTextView textView, final int start, final int end) {
		textView.setFocusable(true);
		textView.setClickable(true);
		SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(
				textView.getText().toString().trim());
		spannableStringBuilder.setSpan(new UnderlineSpan(), start, end,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		textView.setText(spannableStringBuilder);
	}


	/**
	 * 添加超链接
	 * 
	 * @param textView
	 *            超链接的TextView
	 * @param start
	 *            超链接开始的位置
	 * @param end
	 *            超链接结束的位置
	 * @param listener
	 *            超链接的单击监听事件
	 */
	public static void addHyperlinks(final HandyTextView textView,
			final int start, final int end, final OnClickListener listener) {

		String text = textView.getText().toString().trim();
		SpannableString sp = new SpannableString(text);
		sp.setSpan(new IntentSpan(listener), start, end,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		sp.setSpan(new ForegroundColorSpan(textView.getContext().getResources()
				.getColor(R.color.black)), start, end,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		textView.setText(sp);
		textView.setMovementMethod(LinkMovementMethod.getInstance());

	}

	/**
	 * 根据月日获取星座
	 * 
	 * @param month
	 *            月
	 * @param day
	 *            日
	 * @return
	 */
	public static String getConstellation(int month, int day) {
		month++;
		int temp = month *100 + day;
		System.out.println(month + " " + day);
		if (temp >=120&&temp <= 218) {
			return "水瓶座";
		} else if (temp >= 219 && temp <= 320) {
			return "双鱼座";
		} else if (temp >= 321 && temp <= 419) {
			return "白羊座";
		} else if (temp >= 420&& temp <= 520) {
			return "金牛座";
		} else if (temp >= 521&& temp <= 621) {
			return "双子座";
		} else if (temp >= 622&& temp <= 722) {
			return "巨蟹座";
		} else if (temp >= 723&& temp <= 822) {
			return "狮子座";
		} else if (temp >= 823&& temp <= 922) {
			return "处女座";
		} else if (temp >= 923&& temp <= 1023) {
			return "天秤座";
		} else if (temp >= 1024&& temp <= 1122) {
			return "天蝎座";
		} else if (temp >= 1123&& temp <= 1221) {
			return "射手座";
		} else 
			return "魔蝎座";
		
	}

	/**
	 * 根据年月日获取年龄
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @param day
	 *            日
	 * @return
	 */
	public static int getAge(int year, int month, int day) {
		int age = 0;
		month++;
		Calendar calendar = Calendar.getInstance();
		if (calendar.get(Calendar.YEAR) == year) {
			if (calendar.get(Calendar.MONTH) == month) {
				if (calendar.get(Calendar.DAY_OF_MONTH) >= day) {
					age = calendar.get(Calendar.YEAR) - year + 1;
				} else {
					age = calendar.get(Calendar.YEAR) - year;
				}
			} else if (calendar.get(Calendar.MONTH) > month) {
				age = calendar.get(Calendar.YEAR) - year + 1;
			} else {
				age = calendar.get(Calendar.YEAR) - year;
			}
		} else {
			age = calendar.get(Calendar.YEAR) - year;
		}
		if (age < 0) {
			return 0;
		}
		return age;
	}

	/**
	 * 获取Assets中的json文本
	 * 
	 * @param context
	 *            上下文
	 * @param name
	 *            文本名称
	 * @return
	 */
	public static String getJson(Context context, String name) {
		if (name != null) {
			String path = "json/" + name;
			InputStream is = null;
			try {
				is = context.getAssets().open(path);
				return readTextFile(is);
			} catch (IOException e) {
				return null;
			} finally {
				try {
					if (is != null) {
						is.close();
						is = null;
					}
				} catch (IOException e) {

				}
			}
		}
		return null;
	}

	/**
	 * 从输入流中获取文本
	 * 
	 * @param inputStream
	 *            文本输入流
	 * @return
	 */
	public static String readTextFile(InputStream inputStream) {
		String readedStr = "";
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			String tmp;
			while ((tmp = br.readLine()) != null) {
				readedStr += tmp;
			}
			br.close();
			inputStream.close();
		} catch (UnsupportedEncodingException e) {
			return null;
		} catch (IOException e) {
			return null;
		}

		return readedStr;
	}

}
