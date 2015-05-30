/**
 * 文件名：VerifyUtils.java
 * 时间：2015年5月17日上午9:52:25
 * 作者：修维康
 */
package com.sdust.im.util;

import java.util.regex.Pattern;

import android.widget.EditText;

/**
 * 类名：VerifyUtils
 * 说明：验证账号 密码是否合法
 */
public class VerifyUtils {
	public static boolean isNull(EditText editText) {
		String text = editText.getText().toString().trim();
		if (text != null && text.length() > 0) {
			return false;
		}
		return true;
	}

	public static boolean matchAccount(String text) {
		if (Pattern.compile("^[a-z0-9_-]{6,18}$").matcher(text).matches()) {
			return true;
		}
		return false;
	}

	public static boolean matchEmail(String text) {
		if (Pattern.compile("\\w[\\w.-]*@[\\w.]+\\.\\w+").matcher(text)
				.matches()) {
			return true;
		}
		return false;
	}
}
