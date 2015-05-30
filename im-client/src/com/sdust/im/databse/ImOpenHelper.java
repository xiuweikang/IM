package com.sdust.im.databse;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class ImOpenHelper extends SQLiteOpenHelper {

	public static final String CREATE_FRIEND = "create table if not exists friend("
			+ "userid integer ,"
			+ "friendid integer,"
			+ "name text,"
			+ "birthday text," + "gender integer," + "photo blob)";
	public static final String CREATE_MESSAGE = "create table if not exists message("
			+ "userid integer,"
			+ "senderid integer,"
			+ "name text,"
			+ "sendtime text,"
			+ "content text,"
			+ "unread integer,"
			+ "type integer)";
	public static final String CREATE_CHAT_MESSAGE = "create table if not exists chat_message("
			+ "userid integer,"
			+ "friendid integer,"
			+ "type integer,"
			+ "sendtime text," + "content text)";

	public ImOpenHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_FRIEND);
		db.execSQL(CREATE_MESSAGE);
		db.execSQL(CREATE_CHAT_MESSAGE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
