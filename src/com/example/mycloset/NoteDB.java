package com.example.mycloset;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NoteDB extends SQLiteOpenHelper {
	//��������ֶ�
	public static final String ID = "_id";// ���ݿ�ID
	public static final String TABLE_NAME = "notes";// ָ������
	public static final String CONTENT = "content";// ָ���е�����
	public static final String PATH = "path";// ͼƬ����·��
	public static final String VIDEO = "video";// ��Ƶ
	public static final String TIME = "time";// ���ݴ���ʱ��

	public NoteDB(Context context) {
		//notes.db���Ϻ�׺.db��Ϊ���㣡������
		super(context, "notes.db", null, 1);// super(context, name(����), factory,version�汾);
	}

	// �������ݿ�
	@Override
	public void onCreate(SQLiteDatabase db) {
		//���¿ɴ�дҲ��Сд��ע��ӿո񣡣������������˱�����ID�С������С�ʱ����
		db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + CONTENT
				+ " TEXT NOT NULL,"
				+ PATH + " TEXT NOT NULL,"
				+ VIDEO + " TEXT NOT NULL,"
				+ TIME + " TEXT NOT NULL)");

	}

	// �������ݿ⣨����Ҫ��
	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

	}

}
