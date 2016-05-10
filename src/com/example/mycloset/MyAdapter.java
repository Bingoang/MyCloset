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

	private Context context;// �����н������ĵ�context
	private Cursor cursor;// ���ݿ��ѯ������������cursor���͵ģ�����Ҫ����һ��cursor����
	private LinearLayout layout;// ������ͼ���󣨲�һ��Ҫlinearlayout��

	// ����һ�����췽����������������������������
	public MyAdapter(Context context, Cursor cursor) {

		this.context = context;
		this.cursor = cursor;
	}

	// ��дBaseAdapter��4������
	@Override
	public int getCount() {
		return cursor.getCount();// ����cursor�ĳ���
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
		// ��ȡcell�е�ÿһ������
		TextView contenttv = (TextView) layout.findViewById(R.id.list_content);// ������ʾ�ı�����
		TextView timetv = (TextView) layout.findViewById(R.id.list_time);// ������ʾʱ��
		ImageView imgiv = (ImageView) layout.findViewById(R.id.list_img);// ������ʾͼƬ
		ImageView videoiv = (ImageView) layout.findViewById(R.id.list_video);// ������Ƶ����ֻ��ȡ��Ƶ��һ��ͼ��ʾ��
		cursor.moveToPosition(position);
		// content��time������������ѯ�����������ݡ�ʱ��;url��urlvideo������Դ
		String content = cursor.getString(cursor.getColumnIndex("content"));
		String time = cursor.getString(cursor.getColumnIndex("time"));
		String url = cursor.getString(cursor.getColumnIndex("path"));// ����ͼͼƬ
		String urlvideo = cursor.getString(cursor.getColumnIndex("video"));// ������Ƶ��Դ����
																			// ��ȡ��ǰvideo�ֶ�,
		// ������鵽�Ĵ�������4������
		contenttv.setText(content);
		timetv.setText(time);
		videoiv.setImageBitmap(getVdieoThumbnail(urlvideo, 200, 200,
				MediaStore.Images.Thumbnails.MICRO_KIND));// ��ʾ��Ƶ
		imgiv.setImageBitmap(getImageThumbnail(url, 200, 200));// ��ʾͼƬ������oom����
		return layout;
	}

	// ͼƬ����ͼ����������������·��������
	public Bitmap getImageThumbnail(String uri, int width, int height) {
		Bitmap bitmap = null;// ����Bitmap���󣬲���ʼ��Ϊnull
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;//
		// ��ȡͼƬ
		bitmap = BitmapFactory.decodeFile(uri, options);
		options.inJustDecodeBounds = false;
		// ��ȡͼƬ���
		int beWidth = options.outWidth / width;
		int beHeight = options.outHeight / height;
		int be = 1;// �Է��ܳ�����
		if (beWidth < beHeight) {
			be = beWidth;
		} else {
			be = beHeight;
		}
		if (beWidth <= 0) {
			be = 1;
		}
		options.inSampleSize = be;
		bitmap = BitmapFactory.decodeFile(uri, options);// ��ȡ�����Ժ��ͼƬ
		bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
				ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		return bitmap;
	}

	// ��Ƶ����ͼ����
	public Bitmap getVdieoThumbnail(String uri, int width, int height, int kind) {
		Bitmap bitmap = null;// ����Bitmap���󣬲���ʼ��Ϊnull
		bitmap = ThumbnailUtils.createVideoThumbnail(uri, kind);// ���ݵ�ַ��ֱ�Ӵ���
		bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
				ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		return bitmap;
	}
}
