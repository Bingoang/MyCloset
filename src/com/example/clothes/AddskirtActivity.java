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

public class AddskirtActivity extends Activity implements OnClickListener {// 2个按钮，所以用接口实现
	private String val;// 用来接收按传过来的flag
	private Button savebtn, canclebtn;
	private EditText ettext;
	private ImageView c_img;
	private VideoView c_video;
	private NoteDBskirt noteDB;// 声明数据库对象
	private SQLiteDatabase dbWriter;
	private File photoFile, videoFile;
	private static final String IMAGE_UNSPECIFIED = "image/*";// 查找文件类型为图片
	private static final int PHOTO_REQUEST_CODE = 2000;
	private static final int VIDEO_REQUEST_CODE = 2;
	private static final int ALBUM_REQUEST_CODE = 3;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addcontent);
		val = getIntent().getStringExtra("flag");// 接收按钮传来的价值对flag
		savebtn = (Button) findViewById(R.id.save);
		canclebtn = (Button) findViewById(R.id.cancle);
		ettext = (EditText) findViewById(R.id.ettext);
		c_img = (ImageView) findViewById(R.id.c_img);
		c_video = (VideoView) findViewById(R.id.c_video);
		// 添加监听事件
		savebtn.setOnClickListener(this);
		canclebtn.setOnClickListener(this);
		// 实例化数据库对象
		noteDB = new NoteDBskirt(this);
		dbWriter = noteDB.getWritableDatabase();// 获得写入权限
		createSDCardDir();
		initView();
	}

	// 在SD卡上创建一个文件夹
	public void createSDCardDir() {
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			// 创建一个文件夹对象，赋值为外部存储器的目录(即获得存储卡的路径)
			File sdcardDir = Environment.getExternalStorageDirectory();
			// 得到一个路径，内容是sdcard的文件夹路径和名字
			String path = sdcardDir.getPath() + "/myClothes/skirt";
			File path1 = new File(path);
			if (!path1.exists()) {
				// 若不存在，创建目录，可以在应用启动的时候创建
				path1.mkdirs();// 创建文件夹
				Toast.makeText(this, path, Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "exist!", Toast.LENGTH_SHORT).show();
			}

		} else {
			return;

		}
	}

	// 判断是文字、图文、视频，并显示
	public void initView() {
		if (val.equals("1")) {// 1：文字
			c_img.setVisibility(View.GONE);// （隐藏）不显示图片和视频对象
			c_video.setVisibility(View.GONE);
		}
		if (val.equals("2")) {// 2：图片
			c_img.setVisibility(View.VISIBLE);// （隐藏）不显示视频对象
			c_video.setVisibility(View.GONE);
			// 跳转至拍照界面
			Intent iimg = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			photoFile = new File(getPhotopath());
			Uri uri = Uri.fromFile(photoFile);
			// 获取拍照后未压缩的原图片，并保存在uri路径中
			iimg.putExtra(MediaStore.EXTRA_OUTPUT, uri);
			startActivityForResult(iimg, PHOTO_REQUEST_CODE);

		}
		if (val.equals("3")) {// 3：视频(与图片类似)
			c_img.setVisibility(View.GONE);// （隐藏）不显示图片对象
			c_video.setVisibility(View.VISIBLE);
			// 跳转到系统相机
			Intent ivideo = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
			// 获取sd卡路径，相片存在此路径中，数据库只存储路径
			// 实例化对象，并获取绝对路径,以系统时间命名
			String videoPath=Environment.getExternalStorageDirectory()
					.getAbsoluteFile() + "/myClothes/skirt/" + getTime1()+ ".mp4";
			videoFile = new File(videoPath);
			ivideo.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(videoFile));// 存储视频
			startActivityForResult(ivideo, VIDEO_REQUEST_CODE);// 传递带返回值的对象
		}
		if (val.equals("4")) {
			// 打开系统相册
			Intent intent = new Intent(Intent.ACTION_PICK, null);
			// setDataAndType:打开各种文件（uri:如Uri.fromFile(new File("/mnt/sdcard/")),type：文件类型，如*/*）
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
			addDB();// 向表添加数据
			finish();// 关闭当前activity
			break;

		case R.id.cancle:
			finish();// 关闭当前activity
			break;
		}

	}

	/**
	 * 根据路径获取图片资源（已缩放）
	 * 
	 * @param url
	 *            图片存储路径
	 * @param width
	 *            缩放的宽度
	 * @param height
	 *            缩放的高度
	 * @return
	 */
	private Bitmap getBitmapFromUrl(String url, double width, double height) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true; // 设置了此属性一定要记得将值设置为true
		Bitmap bitmap = BitmapFactory.decodeFile(url);
		// 防止OOM发生
		options.inJustDecodeBounds = false;// 此属性false表示需要加载图片的时候
		int mWidth = bitmap.getWidth();
		int mHeight = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidth = 1;
		float scaleHeight = 1;
		// 按照固定宽高进行缩放
		// 这里希望知道照片是横屏拍摄还是竖屏拍摄
		// 因为两种方式宽高不同，缩放效果就会不同
		// 这里用了比较笨的方式
		if (mWidth <= mHeight) {
			scaleWidth = (float) (width / mWidth);
			scaleHeight = (float) (height / mHeight);
		} else {
			scaleWidth = (float) (height / mWidth);
			scaleHeight = (float) (width / mHeight);
		}
		// matrix.postRotate(90); /* 翻转90度 */
		// 按照固定大小对图片进行缩放
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, mWidth, mHeight,
				matrix, true);
		// 用完了记得回收
		bitmap.recycle();
		return newBitmap;
	}
	
	private Bitmap getBitmapFromMap(Bitmap bitmap, double width, double height) {
		int mWidth = bitmap.getWidth();
		int mHeight = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidth = 1;
		float scaleHeight = 1;
		// 按照固定宽高进行缩放
		// 这里希望知道照片是横屏拍摄还是竖屏拍摄
		// 因为两种方式宽高不同，缩放效果就会不同
		// 这里用了比较笨的方式
		if (mWidth <= mHeight) {
			scaleWidth = (float) (width / mWidth);
			scaleHeight = (float) (height / mHeight);
		} else {
			scaleWidth = (float) (height / mWidth);
			scaleHeight = (float) (width / mHeight);
		}
		// matrix.postRotate(90); /* 翻转90度 */
		// 按照固定大小对图片进行缩放
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, mWidth, mHeight,
				matrix, true);
		// 用完了记得回收
		bitmap.recycle();
		return newBitmap;
	}
	/**
	 * 获取原图片存储路径
	 * 
	 * @return
	 */
	private String getPhotopath() {
		// 照片全路径
		String fileName = "";
		// 文件夹路径
		String pathUrl = Environment.getExternalStorageDirectory()
				+ "/myClothes/skirt/";
		String imageName = "imageTest.jpg";
		fileName = pathUrl + imageName;
		return fileName;
	}

	/**
	 * 存储缩放的图片
	 * 
	 * @param data
	 *            图片数据
	 */
	private void saveScalePhoto(Bitmap bitmap) {
		// 照片全路径
		String fileName = "";
		// 文件夹路径
		String pathUrl = Environment.getExternalStorageDirectory().getPath()
				+ "/myClothes/skirt/";
		String imageName = getTime1() + ".jpg";
		FileOutputStream fos = null;
		fileName = pathUrl + imageName;
		photoFile =new File(fileName);
		try {
			fos = new FileOutputStream(fileName);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 20, fos);// 在这一步完成图片的保存
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

	// 创建添加数据的方法
	public void addDB() {
		ContentValues cv = new ContentValues();// 创建表内容对象并直接实例化
		cv.put(NoteDBskirt.CONTENT, ettext.getText().toString());// (键值对)下同，添加内容;从编辑框中获取内容并转换成string
		cv.put(NoteDBskirt.TIME, getTime2());
		cv.put(NoteDBskirt.PATH, photoFile + "");// 将路径转成string类型存储
		cv.put(NoteDBskirt.VIDEO, videoFile + "");
		// 通过dbWriter将数据插入（写入）表中
		dbWriter.insert(NoteDBskirt.TABLE_NAME, null, cv);// (表名，条件，插入内容对象)

	}

	// 创建获取系统当前时间的方法1
	public String getTime1() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");// 指定格式并直接将其实例化
		Date curDate = new Date();// 创建并实例化创建文本的时间！！！import
									// java.util.Date别引错包！别因sql那个
		String str = format.format(curDate);// 通过格式format获取时间，并按照指定格式
		return str; // 返回的是string类型，所以方法类型是string而不是void!
	}

	// 创建获取系统当前时间的方法2
	public String getTime2() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// 指定格式并直接将其实例化
		Date curDate = new Date();// 创建并实例化创建文本的时间！！！import
									// java.util.Date别引错包！别因sql那个
		String str = format.format(curDate);// 通过格式format获取时间，并按照指定格式
		return str; // 返回的是string类型，所以方法类型是string而不是void!
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

		if (requestCode == VIDEO_REQUEST_CODE) {// 代表传递是视频
			c_video.setVideoURI(Uri.fromFile(videoFile));
			c_video.start();// 播放
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
			bitmap = getBitmapFromMap(bitmap, 627, 924);//这个函数要改进，不要再用getBitmapFromMap
			saveScalePhoto(bitmap);
			c_img.setImageBitmap(bitmap);
			Log.v("昂", "=======设置相册图片完成=======");
		}
	}
}
