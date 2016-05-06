package com.example.mycloset;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NoteDB extends SQLiteOpenHelper {
	//创建表的字段
	public static final String ID = "_id";// 数据库ID
	public static final String TABLE_NAME = "notes";// 指定表名
	public static final String CONTENT = "content";// 指定列的内容
	public static final String PATH = "path";// 图片保存路径
	public static final String VIDEO = "video";// 视频
	public static final String TIME = "time";// 内容创建时间

	public NoteDB(Context context) {
		//notes.db加上后缀.db较为方便！！！！
		super(context, "notes.db", null, 1);// super(context, name(库名), factory,version版本);
	}

	// 创建数据库
	@Override
	public void onCreate(SQLiteDatabase db) {
		//以下可大写也可小写（注意加空格！！！）。创建了表名、ID列、内容列、时间列
		db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + CONTENT
				+ " TEXT NOT NULL,"
				+ PATH + " TEXT NOT NULL,"
				+ VIDEO + " TEXT NOT NULL,"
				+ TIME + " TEXT NOT NULL)");

	}

	// 更新数据库（不需要）
	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

	}

}
