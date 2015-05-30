package com.sdust.im.activity;

import java.io.IOException;

import com.sdust.im.BaseActivity;
import com.sdust.im.R;
import com.sdust.im.action.UserAction;
import com.sdust.im.activity.register.RegisterActivity;
import com.sdust.im.bean.ApplicationData;
import com.sdust.im.bean.TranObject;
import com.sdust.im.bean.User;
import com.sdust.im.global.Result;
import com.sdust.im.network.NetService;
import com.sdust.im.util.VerifyUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class LoginActivity extends BaseActivity {

	private Context mContext;
	private RelativeLayout rl_user;
	private Button mLoginButton;
	private Button mRegisterButton;
	private EditText mAccount;
	private EditText mPassword;;
	
	private NetService mNetService = NetService.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		mContext = this;
		initViews();
		initEvents();
	}

	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		rl_user = (RelativeLayout) findViewById(R.id.rl_user);
		mLoginButton = (Button) findViewById(R.id.login);
		mRegisterButton = (Button) findViewById(R.id.register);
		mAccount = (EditText) findViewById(R.id.account);
		mPassword = (EditText) findViewById(R.id.password);

	}

	@Override
	protected void initEvents() {
		// TODO Auto-generated method stub
		Animation anim = AnimationUtils.loadAnimation(mContext,
				R.anim.login_anim);
		anim.setFillAfter(true);
		rl_user.startAnimation(anim);

		mLoginButton.setOnClickListener(loginOnClickListener);
		mRegisterButton.setOnClickListener(registerOnClickListener);

	}

	private OnClickListener loginOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			String account = mAccount.getText().toString().trim();
			String password = mPassword.getText().toString().trim();
			if (account.equals("")) {
				showCustomToast("请填写账号");
				mAccount.requestFocus();
			} else if (password.equals("")) {
				showCustomToast("请填写密码");
			} else if (!VerifyUtils.matchAccount(account)) {
				showCustomToast("账号格式错误");
				mAccount.requestFocus();
			} else if (mPassword.length() < 6) {
				showCustomToast("密码格式错误");
			} else {
				tryLogin(account, password);
			}
		}
	};

	private void tryLogin(final String account, final String password) {
		new AsyncTask<Void, Void, Integer>() {

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				showLoadingDialog("正在登录,请稍后...");
			}

			@Override
			protected Integer doInBackground(Void... params) {
				try {
					
					mNetService.closeConnection();
					mNetService.onInit(LoginActivity.this);
					mNetService.setupConnection();
					Log.d("network", "set up connection");
					if (!mNetService.isConnected()) {
						return 0;
					}
					
					User user = new User();
					user.setAccount(account);
					user.setPassword(password);
					UserAction.loginVerify(user);
					ApplicationData data = ApplicationData.getInstance();
					data.initData(LoginActivity.this);
					data.start();
					System.out.println(data.getReceivedMessage().getResult());
					if (data.getReceivedMessage().getResult() == Result.LOGIN_SUCCESS)
						return 1;// 登录成功
					else if(data.getReceivedMessage().getResult() == Result.LOGIN_FAILED)
						return 2;// 登录失败
				} catch (IOException e) {
					Log.d("network", "IO异常");
				}
				return 0;

			}

			@Override
			protected void onPostExecute(Integer result) {
				super.onPostExecute(result);
				dismissLoadingDialog();
				if (result == 0) {
					showCustomToast("服务器异常");
				} else {
					if (result == 2) {
						showCustomToast("登录失败");
					} else if (result == 1) {
						Intent intent = new Intent(mContext, MainActivity.class);
						startActivity(intent);
						finish();
					}
				}
			}
		}.execute();

	}

	private OnClickListener registerOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(mContext, RegisterActivity.class);
			startActivity(intent);
		}
	};


}
