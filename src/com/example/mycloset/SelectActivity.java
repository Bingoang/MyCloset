package com.example.mycloset;

import java.io.File;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class SelectActivity extends Activity implements OnClickListener {
	private Button s_delete, s_back;
	private ImageView s_img;
	private VideoView s_video;
	private TextView s_tv;
	private NoteDB noteDB;// 数据库对象
	private SQLiteDatabase dbWriter;// 操作权限：写权限

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select);
		// System.out.println(getIntent().getIntExtra(NoteDB.ID,
		// 0));//测试用的，打印的是文本的ID，默认值0
		s_delete = (Button) findViewById(R.id.s_delete);
		s_back = (Button) findViewById(R.id.s_back);
		s_img = (ImageView) findViewById(R.id.s_img);
		s_video = (VideoView) findViewById(R.id.s_video);
		s_tv = (TextView) findViewById(R.id.s_tv);
		noteDB = new NoteDB(this);
		dbWriter = noteDB.getWritableDatabase();// 获取写权限
		s_delete.setOnClickListener(this);// 绑定监听事件
		s_back.setOnClickListener(this);
		// 判断有无图片
		if (getIntent().getStringExtra(NoteDB.PATH).equals("null")) {// 如果路径为空，则无图片
			s_img.setVisibility(View.GONE);
		} else {
			s_img.setVisibility(View.VISIBLE);
		}
		// 判断有无视频
		if (getIntent().getStringExtra(NoteDB.VIDEO).equals("null")) {
			s_video.setVisibility(View.GONE);
		} else {
			s_video.setVisibility(View.VISIBLE);
		}
		// 显示文本信息
		s_tv.setText(getIntent().getStringExtra(NoteDB.CONTENT));
		// 显示图片？？？？？？？？？？？估计要改吧
		Bitmap bitmap = BitmapFactory.decodeFile(getIntent().getStringExtra(
				NoteDB.PATH));
		s_img.setImageBitmap(bitmap);
		// 显示视频,并播放
		s_video.setVideoURI(Uri.parse(getIntent().getStringExtra(NoteDB.VIDEO)));
		s_video.start();

	}

	// 2个按钮的点击事件
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.s_delete:
			//在源文件被删除时才删除数据库记录
			if(deleteFile()){
				deleteDate();
			}
			finish();
			break;

		case R.id.s_back:
			finish();
			break;
		}

	}

	// 创建删除文件方法
	public boolean deleteFile() {
		String fileNameImage=Uri.parse(getIntent().getStringExtra(NoteDB.PATH))+"";
		String fileNameVideo=Uri.parse(getIntent().getStringExtra(NoteDB.VIDEO))+"";
		Log.v("昂","image======>"+fileNameImage);
		Log.v("昂","video======>"+fileNameVideo);
		if (!fileNameImage.equals("null")) {
			Log.v("昂","======>开始进入删除image函数");
			File file = new File(fileNameImage);
			if (file == null || !file.exists() || file.isDirectory()) {
				Toast.makeText(this, "文件不存在!", Toast.LENGTH_SHORT).show();
				return false;
			}
			file.delete();
			Toast.makeText(this, "文件已删除!", Toast.LENGTH_SHORT).show();
			return true;
		}
		if (!fileNameVideo.equals("null")) {
			Log.v("昂","======>开始进入删除video函数");
			File file = new File(fileNameVideo);
			if (file == null || !file.exists() || file.isDirectory()) {
				Toast.makeText(this, "文件不存在!", Toast.LENGTH_SHORT).show();
				Log.v("昂","======>删除失败");
				return false;
			}
			file.delete();
			Log.v("昂","======>删除成功");
			Toast.makeText(this, "文件已删除!", Toast.LENGTH_SHORT).show();
			return true;
		}
		return false;
	}

	// 创建删除路径方法
	public void deleteDate() {
		// 三个参数（表名，id=当前获取的id时，无）
		dbWriter.delete(NoteDB.TABLE_NAME,
				"_id=" + getIntent().getIntExtra(NoteDB.ID, 0), null);
	}

}
