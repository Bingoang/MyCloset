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
	private NoteDB noteDB;// ���ݿ����
	private SQLiteDatabase dbWriter;// ����Ȩ�ޣ�дȨ��

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select);
		// System.out.println(getIntent().getIntExtra(NoteDB.ID,
		// 0));//�����õģ���ӡ�����ı���ID��Ĭ��ֵ0
		s_delete = (Button) findViewById(R.id.s_delete);
		s_back = (Button) findViewById(R.id.s_back);
		s_img = (ImageView) findViewById(R.id.s_img);
		s_video = (VideoView) findViewById(R.id.s_video);
		s_tv = (TextView) findViewById(R.id.s_tv);
		noteDB = new NoteDB(this);
		dbWriter = noteDB.getWritableDatabase();// ��ȡдȨ��
		s_delete.setOnClickListener(this);// �󶨼����¼�
		s_back.setOnClickListener(this);
		// �ж�����ͼƬ
		if (getIntent().getStringExtra(NoteDB.PATH).equals("null")) {// ���·��Ϊ�գ�����ͼƬ
			s_img.setVisibility(View.GONE);
		} else {
			s_img.setVisibility(View.VISIBLE);
		}
		// �ж�������Ƶ
		if (getIntent().getStringExtra(NoteDB.VIDEO).equals("null")) {
			s_video.setVisibility(View.GONE);
		} else {
			s_video.setVisibility(View.VISIBLE);
		}
		// ��ʾ�ı���Ϣ
		s_tv.setText(getIntent().getStringExtra(NoteDB.CONTENT));
		// ��ʾͼƬ��������������������������Ҫ�İ�
		Bitmap bitmap = BitmapFactory.decodeFile(getIntent().getStringExtra(
				NoteDB.PATH));
		s_img.setImageBitmap(bitmap);
		// ��ʾ��Ƶ,������
		s_video.setVideoURI(Uri.parse(getIntent().getStringExtra(NoteDB.VIDEO)));
		s_video.start();

	}

	// 2����ť�ĵ���¼�
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.s_delete:
			//��Դ�ļ���ɾ��ʱ��ɾ�����ݿ��¼
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

	// ����ɾ���ļ�����
	public boolean deleteFile() {
		String fileNameImage=Uri.parse(getIntent().getStringExtra(NoteDB.PATH))+"";
		String fileNameVideo=Uri.parse(getIntent().getStringExtra(NoteDB.VIDEO))+"";
		Log.v("��","image======>"+fileNameImage);
		Log.v("��","video======>"+fileNameVideo);
		if (!fileNameImage.equals("null")) {
			Log.v("��","======>��ʼ����ɾ��image����");
			File file = new File(fileNameImage);
			if (file == null || !file.exists() || file.isDirectory()) {
				Toast.makeText(this, "�ļ�������!", Toast.LENGTH_SHORT).show();
				return false;
			}
			file.delete();
			Toast.makeText(this, "�ļ���ɾ��!", Toast.LENGTH_SHORT).show();
			return true;
		}
		if (!fileNameVideo.equals("null")) {
			Log.v("��","======>��ʼ����ɾ��video����");
			File file = new File(fileNameVideo);
			if (file == null || !file.exists() || file.isDirectory()) {
				Toast.makeText(this, "�ļ�������!", Toast.LENGTH_SHORT).show();
				Log.v("��","======>ɾ��ʧ��");
				return false;
			}
			file.delete();
			Log.v("��","======>ɾ���ɹ�");
			Toast.makeText(this, "�ļ���ɾ��!", Toast.LENGTH_SHORT).show();
			return true;
		}
		return false;
	}

	// ����ɾ��·������
	public void deleteDate() {
		// ����������������id=��ǰ��ȡ��idʱ���ޣ�
		dbWriter.delete(NoteDB.TABLE_NAME,
				"_id=" + getIntent().getIntExtra(NoteDB.ID, 0), null);
	}

}
