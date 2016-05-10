package com.example.clothes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.mycloset.R;
import com.example.mycloset.R.id;
import com.example.mycloset.R.layout;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

public class AddskirtActivity extends Activity implements OnClickListener {// 2����ť�������ýӿ�ʵ��
	private String val;// �������հ���������flag
	private Button savebtn, canclebtn;
	private EditText ettext;
	private ImageView c_img;
	private VideoView c_video;
	private NoteDBskirt noteDB;// �������ݿ����
	private SQLiteDatabase dbWriter;
	private File photoFile, videoFile;
	private static final String IMAGE_UNSPECIFIED = "image/*";// �����ļ�����ΪͼƬ
	private static final int PHOTO_REQUEST_CODE = 2000;
	private static final int VIDEO_REQUEST_CODE = 2;
	private static final int ALBUM_REQUEST_CODE = 3;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addcontent);
		val = getIntent().getStringExtra("flag");// ���հ�ť�����ļ�ֵ��flag
		savebtn = (Button) findViewById(R.id.save);
		canclebtn = (Button) findViewById(R.id.cancle);
		ettext = (EditText) findViewById(R.id.ettext);
		c_img = (ImageView) findViewById(R.id.c_img);
		c_video = (VideoView) findViewById(R.id.c_video);
		// ��Ӽ����¼�
		savebtn.setOnClickListener(this);
		canclebtn.setOnClickListener(this);
		// ʵ�������ݿ����
		noteDB = new NoteDBskirt(this);
		dbWriter = noteDB.getWritableDatabase();// ���д��Ȩ��
		createSDCardDir();
		initView();
	}

	// ��SD���ϴ���һ���ļ���
	public void createSDCardDir() {
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			// ����һ���ļ��ж��󣬸�ֵΪ�ⲿ�洢����Ŀ¼(����ô洢����·��)
			File sdcardDir = Environment.getExternalStorageDirectory();
			// �õ�һ��·����������sdcard���ļ���·��������
			String path = sdcardDir.getPath() + "/myClothes/skirt";
			File path1 = new File(path);
			if (!path1.exists()) {
				// �������ڣ�����Ŀ¼��������Ӧ��������ʱ�򴴽�
				path1.mkdirs();// �����ļ���
				Toast.makeText(this, path, Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "exist!", Toast.LENGTH_SHORT).show();
			}

		} else {
			return;

		}
	}

	// �ж������֡�ͼ�ġ���Ƶ������ʾ
	public void initView() {
		if (val.equals("1")) {// 1������
			c_img.setVisibility(View.GONE);// �����أ�����ʾͼƬ����Ƶ����
			c_video.setVisibility(View.GONE);
		}
		if (val.equals("2")) {// 2��ͼƬ
			c_img.setVisibility(View.VISIBLE);// �����أ�����ʾ��Ƶ����
			c_video.setVisibility(View.GONE);
			// ��ת�����ս���
			Intent iimg = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			photoFile = new File(getPhotopath());
			Uri uri = Uri.fromFile(photoFile);
			// ��ȡ���պ�δѹ����ԭͼƬ����������uri·����
			iimg.putExtra(MediaStore.EXTRA_OUTPUT, uri);
			startActivityForResult(iimg, PHOTO_REQUEST_CODE);

		}
		if (val.equals("3")) {// 3����Ƶ(��ͼƬ����)
			c_img.setVisibility(View.GONE);// �����أ�����ʾͼƬ����
			c_video.setVisibility(View.VISIBLE);
			// ��ת��ϵͳ���
			Intent ivideo = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
			// ��ȡsd��·������Ƭ���ڴ�·���У����ݿ�ֻ�洢·��
			// ʵ�������󣬲���ȡ����·��,��ϵͳʱ������
			String videoPath=Environment.getExternalStorageDirectory()
					.getAbsoluteFile() + "/myClothes/skirt/" + getTime1()+ ".mp4";
			videoFile = new File(videoPath);
			ivideo.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(videoFile));// �洢��Ƶ
			startActivityForResult(ivideo, VIDEO_REQUEST_CODE);// ���ݴ�����ֵ�Ķ���
		}
		if (val.equals("4")) {
			// ��ϵͳ���
			Intent intent = new Intent(Intent.ACTION_PICK, null);
			// setDataAndType:�򿪸����ļ���uri:��Uri.fromFile(new File("/mnt/sdcard/")),type���ļ����ͣ���*/*��
			intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
					IMAGE_UNSPECIFIED);
			String photoPath=Environment.getExternalStorageDirectory()
					.getAbsoluteFile() + "/" + getTime1() + ".jpg";
			photoFile = new File(photoPath);
			startActivityForResult(intent, ALBUM_REQUEST_CODE);
			c_img.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.save:
			addDB();// ����������
			finish();// �رյ�ǰactivity
			break;

		case R.id.cancle:
			finish();// �رյ�ǰactivity
			break;
		}

	}

	/**
	 * ����·����ȡͼƬ��Դ�������ţ�
	 * 
	 * @param url
	 *            ͼƬ�洢·��
	 * @param width
	 *            ���ŵĿ��
	 * @param height
	 *            ���ŵĸ߶�
	 * @return
	 */
	private Bitmap getBitmapFromUrl(String url, double width, double height) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true; // �����˴�����һ��Ҫ�ǵý�ֵ����Ϊtrue
		Bitmap bitmap = BitmapFactory.decodeFile(url);
		// ��ֹOOM����
		options.inJustDecodeBounds = false;// ������false��ʾ��Ҫ����ͼƬ��ʱ��
		int mWidth = bitmap.getWidth();
		int mHeight = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidth = 1;
		float scaleHeight = 1;
		// ���չ̶���߽�������
		// ����ϣ��֪����Ƭ�Ǻ������㻹����������
		// ��Ϊ���ַ�ʽ��߲�ͬ������Ч���ͻ᲻ͬ
		// �������˱Ƚϱ��ķ�ʽ
		if (mWidth <= mHeight) {
			scaleWidth = (float) (width / mWidth);
			scaleHeight = (float) (height / mHeight);
		} else {
			scaleWidth = (float) (height / mWidth);
			scaleHeight = (float) (width / mHeight);
		}
		// matrix.postRotate(90); /* ��ת90�� */
		// ���չ̶���С��ͼƬ��������
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, mWidth, mHeight,
				matrix, true);
		// �����˼ǵû���
		bitmap.recycle();
		return newBitmap;
	}
	
	private Bitmap getBitmapFromMap(Bitmap bitmap, double width, double height) {
		int mWidth = bitmap.getWidth();
		int mHeight = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidth = 1;
		float scaleHeight = 1;
		// ���չ̶���߽�������
		// ����ϣ��֪����Ƭ�Ǻ������㻹����������
		// ��Ϊ���ַ�ʽ��߲�ͬ������Ч���ͻ᲻ͬ
		// �������˱Ƚϱ��ķ�ʽ
		if (mWidth <= mHeight) {
			scaleWidth = (float) (width / mWidth);
			scaleHeight = (float) (height / mHeight);
		} else {
			scaleWidth = (float) (height / mWidth);
			scaleHeight = (float) (width / mHeight);
		}
		// matrix.postRotate(90); /* ��ת90�� */
		// ���չ̶���С��ͼƬ��������
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, mWidth, mHeight,
				matrix, true);
		// �����˼ǵû���
		bitmap.recycle();
		return newBitmap;
	}
	/**
	 * ��ȡԭͼƬ�洢·��
	 * 
	 * @return
	 */
	private String getPhotopath() {
		// ��Ƭȫ·��
		String fileName = "";
		// �ļ���·��
		String pathUrl = Environment.getExternalStorageDirectory()
				+ "/myClothes/skirt/";
		String imageName = "imageTest.jpg";
		fileName = pathUrl + imageName;
		return fileName;
	}

	/**
	 * �洢���ŵ�ͼƬ
	 * 
	 * @param data
	 *            ͼƬ����
	 */
	private void saveScalePhoto(Bitmap bitmap) {
		// ��Ƭȫ·��
		String fileName = "";
		// �ļ���·��
		String pathUrl = Environment.getExternalStorageDirectory().getPath()
				+ "/myClothes/skirt/";
		String imageName = getTime1() + ".jpg";
		FileOutputStream fos = null;
		fileName = pathUrl + imageName;
		photoFile =new File(fileName);
		try {
			fos = new FileOutputStream(fileName);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 20, fos);// ����һ�����ͼƬ�ı���
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				fos.flush();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// ����������ݵķ���
	public void addDB() {
		ContentValues cv = new ContentValues();// ���������ݶ���ֱ��ʵ����
		cv.put(NoteDBskirt.CONTENT, ettext.getText().toString());// (��ֵ��)��ͬ���������;�ӱ༭���л�ȡ���ݲ�ת����string
		cv.put(NoteDBskirt.TIME, getTime2());
		cv.put(NoteDBskirt.PATH, photoFile + "");// ��·��ת��string���ʹ洢
		cv.put(NoteDBskirt.VIDEO, videoFile + "");
		// ͨ��dbWriter�����ݲ��루д�룩����
		dbWriter.insert(NoteDBskirt.TABLE_NAME, null, cv);// (�������������������ݶ���)

	}

	// ������ȡϵͳ��ǰʱ��ķ���1
	public String getTime1() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");// ָ����ʽ��ֱ�ӽ���ʵ����
		Date curDate = new Date();// ������ʵ���������ı���ʱ�䣡����import
									// java.util.Date�������������sql�Ǹ�
		String str = format.format(curDate);// ͨ����ʽformat��ȡʱ�䣬������ָ����ʽ
		return str; // ���ص���string���ͣ����Է���������string������void!
	}

	// ������ȡϵͳ��ǰʱ��ķ���2
	public String getTime2() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy��MM��dd�� HH:mm:ss");// ָ����ʽ��ֱ�ӽ���ʵ����
		Date curDate = new Date();// ������ʵ���������ı���ʱ�䣡����import
									// java.util.Date�������������sql�Ǹ�
		String str = format.format(curDate);// ͨ����ʽformat��ȡʱ�䣬������ָ����ʽ
		return str; // ���ص���string���ͣ����Է���������string������void!
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == PHOTO_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
			Bitmap bitmap = getBitmapFromUrl(getPhotopath(), 627, 924);
			saveScalePhoto(bitmap);
			c_img.setImageBitmap(bitmap);
		}

		if (requestCode == VIDEO_REQUEST_CODE) {// ����������Ƶ
			c_video.setVideoURI(Uri.fromFile(videoFile));
			c_video.start();// ����
		}
		
		if (requestCode == ALBUM_REQUEST_CODE) {
			if (data == null) {
				return;
			}
			Bitmap bitmap = null;
			ContentResolver resolver = getContentResolver();
			Uri originalUri = data.getData();
			try {
				bitmap = MediaStore.Images.Media.getBitmap(resolver,
						originalUri);

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			bitmap = getBitmapFromMap(bitmap, 627, 924);//�������Ҫ�Ľ�����Ҫ����getBitmapFromMap
			saveScalePhoto(bitmap);
			c_img.setImageBitmap(bitmap);
			Log.v("��", "=======�������ͼƬ���=======");
		}
	}
}
