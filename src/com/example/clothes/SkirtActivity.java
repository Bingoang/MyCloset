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
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class SkirtActivity extends Activity implements OnClickListener {
	private Button textbtn, imgbtn, albumbtn, videobtn;
	private ListView lv;
	private Intent i;// 创建标识，来识别到底点的是哪个按钮
	private MyAdapter adapter;// 创建适配器对象
	private NoteDBskirt noteDB;// 创建数据库对象
	private SQLiteDatabase dbReader;// 读取权限对象
	private Cursor cursor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.skirt);
		initView();// 初始化
	}

	// 创建初始化方法
	public void initView() {
		textbtn = (Button) findViewById(R.id.text);
		imgbtn = (Button) findViewById(R.id.img);
		albumbtn = (Button) findViewById(R.id.album);
		videobtn = (Button) findViewById(R.id.video);
		lv = (ListView) findViewById(R.id.list);
		// 各按钮添加监听事件
		textbtn.setOnClickListener(this);
		imgbtn.setOnClickListener(this);
		albumbtn.setOnClickListener(this);
		videobtn.setOnClickListener(this);
		noteDB = new NoteDBskirt(this);// 实例化数据库对象
		dbReader = noteDB.getReadableDatabase();// 获得读取权限
		// 给listview对象添加点击事件
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 获取当前cursor指向的position

				cursor.moveToPosition(position);
				// 通过intent跳转到详情页面
				Intent i = new Intent(SkirtActivity.this, SelectskirtActivity.class);// 直接实例化，两个参数：当前页面，要跳转到的页面
				// 获取d、内容、时间、图片和视频路径,也可以写成getColumnIndex("_id")
				i.putExtra(NoteDBskirt.ID,
						cursor.getInt(cursor.getColumnIndex(NoteDBskirt.ID)));
				i.putExtra(NoteDBskirt.CONTENT,
						cursor.getString(cursor.getColumnIndex(NoteDBskirt.CONTENT)));
				i.putExtra(NoteDBskirt.TIME,
						cursor.getString(cursor.getColumnIndex(NoteDBskirt.TIME)));
				i.putExtra(NoteDBskirt.PATH,
						cursor.getString(cursor.getColumnIndex(NoteDBskirt.PATH)));
				i.putExtra(NoteDBskirt.VIDEO,
						cursor.getString(cursor.getColumnIndex(NoteDBskirt.VIDEO)));
				Log.v("昂", cursor.getString(cursor.getColumnIndex(NoteDBskirt.PATH)));
				Log.v("昂",
						cursor.getString(cursor.getColumnIndex(NoteDBskirt.VIDEO)));
				startActivity(i);// 跳转到详情页，并把数据传到详情页
			}
		});

	}

	// 三个按钮的点击事件
	@Override
	public void onClick(View v) {
		i = new Intent(this, AddskirtActivity.class);// 实例化i,不管点哪个按钮，都是跳转到AddActivity类
		switch (v.getId()) {
		case R.id.text:
			i.putExtra("flag", "1");// 键值对.值为string类型
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

	// 创建获取数据的方法，并在onResume()中调用该方法
	public void selectDB() {
		// 查询得到的数据返回的是cursor类型,这个cursor依然是cursor.moveToPosition(position)那个
		cursor = dbReader.query(NoteDBskirt.TABLE_NAME, null, null, null, null,
				null, null);// 查询全部数据，第一个参数是表名，后面的参数都不需要
		// 实例化adapter，将adapter进行适配

		adapter = new MyAdapter(this, cursor);
		// 绑定adapter
		lv.setAdapter(adapter);

	}

	@Override
	protected void onResume() {
		super.onResume();
		selectDB();// 调用获取数据方法
	}

}
