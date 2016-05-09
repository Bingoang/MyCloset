package com.example.clothes;

import com.example.mycloset.R;
import com.example.mycloset.R.id;
import com.example.mycloset.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class ShoesActivity extends Activity implements OnClickListener {
	private Button textbtn, imgbtn, albumbtn, videobtn;
	private ListView lv;
	private Intent i;// ������ʶ����ʶ�𵽵׵�����ĸ���ť
	private MyAdapter adapter;// ��������������
	private NoteDBshoes noteDB;// �������ݿ����
	private SQLiteDatabase dbReader;// ��ȡȨ�޶���
	private Cursor cursor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shoes);
		initView();// ��ʼ��
	}

	// ������ʼ������
	public void initView() {
		textbtn = (Button) findViewById(R.id.text);
		imgbtn = (Button) findViewById(R.id.img);
		albumbtn = (Button) findViewById(R.id.album);
		videobtn = (Button) findViewById(R.id.video);
		lv = (ListView) findViewById(R.id.list);
		// ����ť��Ӽ����¼�
		textbtn.setOnClickListener(this);
		imgbtn.setOnClickListener(this);
		albumbtn.setOnClickListener(this);
		videobtn.setOnClickListener(this);
		noteDB = new NoteDBshoes(this);// ʵ�������ݿ����
		dbReader = noteDB.getReadableDatabase();// ��ö�ȡȨ��
		// ��listview������ӵ���¼�
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// ��ȡ��ǰcursorָ���position

				cursor.moveToPosition(position);
				// ͨ��intent��ת������ҳ��
				Intent i = new Intent(ShoesActivity.this, SelectshoesActivity.class);// ֱ��ʵ������������������ǰҳ�棬Ҫ��ת����ҳ��
				// ��ȡd�����ݡ�ʱ�䡢ͼƬ����Ƶ·��,Ҳ����д��getColumnIndex("_id")
				i.putExtra(NoteDBshoes.ID,
						cursor.getInt(cursor.getColumnIndex(NoteDBshoes.ID)));
				i.putExtra(NoteDBshoes.CONTENT,
						cursor.getString(cursor.getColumnIndex(NoteDBshoes.CONTENT)));
				i.putExtra(NoteDBshoes.TIME,
						cursor.getString(cursor.getColumnIndex(NoteDBshoes.TIME)));
				i.putExtra(NoteDBshoes.PATH,
						cursor.getString(cursor.getColumnIndex(NoteDBshoes.PATH)));
				i.putExtra(NoteDBshoes.VIDEO,
						cursor.getString(cursor.getColumnIndex(NoteDBshoes.VIDEO)));
				Log.v("��", cursor.getString(cursor.getColumnIndex(NoteDBshoes.PATH)));
				Log.v("��",
						cursor.getString(cursor.getColumnIndex(NoteDBshoes.VIDEO)));
				startActivity(i);// ��ת������ҳ���������ݴ�������ҳ
			}
		});

	}

	// ������ť�ĵ���¼�
	@Override
	public void onClick(View v) {
		i = new Intent(this, AddshoesActivity.class);// ʵ����i,���ܵ��ĸ���ť��������ת��AddActivity��
		switch (v.getId()) {
		case R.id.text:
			i.putExtra("flag", "1");// ��ֵ��.ֵΪstring����
			startActivity(i);
			break;

		case R.id.img:
			i.putExtra("flag", "2");
			startActivity(i);
			break;
		case R.id.video:
			i.putExtra("flag", "3");
			startActivity(i);
			break;
		case R.id.album:
			i.putExtra("flag", "4");
			startActivity(i);
			break;

		}

	}

	// ������ȡ���ݵķ���������onResume()�е��ø÷���
	public void selectDB() {
		// ��ѯ�õ������ݷ��ص���cursor����,���cursor��Ȼ��cursor.moveToPosition(position)�Ǹ�
		cursor = dbReader.query(NoteDBshoes.TABLE_NAME, null, null, null, null,
				null, null);// ��ѯȫ�����ݣ���һ�������Ǳ���������Ĳ���������Ҫ
		// ʵ����adapter����adapter��������

		adapter = new MyAdapter(this, cursor);
		// ��adapter
		lv.setAdapter(adapter);

	}

	@Override
	protected void onResume() {
		super.onResume();
		selectDB();// ���û�ȡ���ݷ���
	}

}
