package com.example.mycloset;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {

	private Context context;// 创建承接上下文的context
	private Cursor cursor;// 数据库查询出来的数据是cursor类型的，所以要创建一个cursor对象
	private LinearLayout layout;// 创建视图对象（不一定要linearlayout）

	// 创建一个构造方法，并将上面声明的两个对象传入
	public MyAdapter(Context context, Cursor cursor) {

		this.context = context;
		this.cursor = cursor;
	}

	// 复写BaseAdapter的4个方法
	@Override
	public int getCount() {
		return cursor.getCount();// 返回cursor的长度
	}

	@Override
	public Object getItem(int position) {
		return cursor.getPosition();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convView, ViewGroup parent) {
		LayoutInflater inflater = LayoutInflater.from(context);
		layout = (LinearLayout) inflater.inflate(R.layout.cell, null);
		// 获取cell中的每一项内容
		TextView contenttv = (TextView) layout.findViewById(R.id.list_content);// 加载显示文本内容
		TextView timetv = (TextView) layout.findViewById(R.id.list_time);// 加载显示时间
		ImageView imgiv = (ImageView) layout.findViewById(R.id.list_img);// 加载显示图片
		ImageView videoiv = (ImageView) layout.findViewById(R.id.list_video);// 加载视频（即只截取视频第一张图显示）
		cursor.moveToPosition(position);
		// content、time用来承载所查询到的所有内容、时间;url、urlvideo加载资源
		String content = cursor.getString(cursor.getColumnIndex("content"));
		String time = cursor.getString(cursor.getColumnIndex("time"));
		String url = cursor.getString(cursor.getColumnIndex("path"));// 加载图图片
		String urlvideo = cursor.getString(cursor.getColumnIndex("video"));// 声明视频资源对象，
																			// 获取当前video字段,
		// 将上面查到的传给以下4个对象
		contenttv.setText(content);
		timetv.setText(time);
		videoiv.setImageBitmap(getVdieoThumbnail(urlvideo, 200, 200,
				MediaStore.Images.Thumbnails.MICRO_KIND));// 显示视频
		imgiv.setImageBitmap(getImageThumbnail(url, 200, 200));// 显示图片，避免oom问题
		return layout;
	}

	// 图片缩略图方法，三个参数：路径，宽，高
	public Bitmap getImageThumbnail(String uri, int width, int height) {
		Bitmap bitmap = null;// 创建Bitmap对象，并初始化为null
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;//
		// 获取图片
		bitmap = BitmapFactory.decodeFile(uri, options);
		options.inJustDecodeBounds = false;
		// 获取图片宽高
		int beWidth = options.outWidth / width;
		int beHeight = options.outHeight / height;
		int be = 1;// 以防跑出界外
		if (beWidth < beHeight) {
			be = beWidth;
		} else {
			be = beHeight;
		}
		if (beWidth <= 0) {
			be = 1;
		}
		options.inSampleSize = be;
		bitmap = BitmapFactory.decodeFile(uri, options);// 获取到缩略后的图片
		bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
				ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		return bitmap;
	}

	// 视频缩略图操作
	public Bitmap getVdieoThumbnail(String uri, int width, int height, int kind) {
		Bitmap bitmap = null;// 创建Bitmap对象，并初始化为null
		bitmap = ThumbnailUtils.createVideoThumbnail(uri, kind);// 传递地址，直接传递
		bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
				ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		return bitmap;
	}
}
