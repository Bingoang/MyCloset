package com.example.kinect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.mycloset.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ShirtActivity extends Activity implements OnItemClickListener,
		OnScrollListener {
	private ListView listView;
	private SimpleAdapter simpleAdapter;
	private List<Map<String, Object>> dataList;
	private Intent i;
	public static int myFlag = 0;
	private int[] images = new int[] { R.drawable.nikebag, R.drawable.t1,
			R.drawable.t2, R.drawable.t3, R.drawable.t4, R.drawable.t5,
			R.drawable.t6, R.drawable.t7 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview);
		initView();// ��ʼ��
		shirtMode();// ��������ģʽ

	}

	private void shirtMode() {
		try {
			// ���û����°�ť֮�󣬽��û���������ݷ�װ��Message
			// Ȼ���͸����߳�Handler
			Message msg = new Message();
			msg.what = 0x345;
			msg.obj = "11";
			synchronized (this.getClass()) {
				KinectActivity.getClientThread().revHandler.sendMessage(msg);
			}

		} catch (Exception e) {

		}

	}


	private void initView() {
		listView = (ListView) findViewById(R.id.listView);
		listView.setOnItemClickListener(this);
		listView.setOnScrollListener(this);
		dataList = new ArrayList<Map<String, Object>>();
		simpleAdapter = new SimpleAdapter(this, dataList, R.layout.item,
				new String[] { "pic" }, new int[] { R.id.pic });
		getData();
		listView.setAdapter(simpleAdapter);
	}

	private List<Map<String, Object>> getData() {

		for (int i = 0; i < images.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pic", images[i]);
			dataList.add(map);
		}
		return dataList;

	}

	// �¼��������������
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		i = new Intent(this, DetailsActivity.class);
		int a=(int) listView.getItemIdAtPosition(position);//a:0~7
		Log.v("ang","position"+a);
		ShirtActivity.myFlag = 10+a;//myFlag��10~17
		startActivity(i);
	}

	@Override
	public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// ��ָ�뿪��Ļǰ����������һ��
//		if (scrollState == SCROLL_STATE_FLING) {
//			for (int i = 0; i < images.length; i++) {
//				Map<String, Object> map = new HashMap<String, Object>();
//				map.put("pic", images[i]);
//				dataList.add(map);
//			}
//			listView.setAdapter(simpleAdapter);
//			simpleAdapter.notifyDataSetChanged();// ֪ͨUI���߳�ˢ��ҳ��
//		} else
//		// ֹͣ����
//		if (scrollState == SCROLL_STATE_IDLE) {
//
//		} else
//		 ���ڹ���
//		if (scrollState == SCROLL_STATE_TOUCH_SCROLL) {
//
//		}

	}

}
