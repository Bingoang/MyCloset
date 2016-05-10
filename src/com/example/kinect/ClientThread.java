package com.example.kinect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

import com.example.mycloset.R;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.WebView.FindListener;
import android.widget.TextView;

public class ClientThread implements Runnable {
	private Socket s;
	// ������UI�̷߳�����Ϣ��Handler����
	Handler handler;
	// �������UI�̵߳�Handler����
	Handler revHandler;
	// ���̴߳���Socket�����õ����������
	BufferedReader br = null;
	OutputStream os = null;

	public ClientThread(Handler handler) {
		this.handler = handler;
	}

	@Override
	public void run() {
		s = new Socket();

		// ���ӷ����� ���������ӳ�ʱΪ3��
		try {
			s.connect(new InetSocketAddress("192.168.191.1", 1234), 3000);
			Message msg = new Message();
			msg.what = 0x111;
			msg.obj = "�����ӣ�";
//			synchronized (this.getClass()) {
				handler.sendMessage(msg);
//			}
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			os = s.getOutputStream();

			new Thread() {
				@Override
				public void run() {
					Message msg = new Message();

					try {
						s.setKeepAlive(true);
						s.setSoTimeout(10);
						while (true) {
							s.sendUrgentData(0xFF); // ����������
							Log.v("ang", "�����ӣ�");
							msg.what = 0x111;
							msg.obj = "�����ӣ�";
//							synchronized (this.getClass()) {
//								handler.sendMessage(msg);
//							}
							Thread.sleep(3 * 1000);// �߳�˯��3��
						}
					} catch (Exception e) {
						Log.v("ang", "�ѶϿ���");
						msg.what = 0x123;
						msg.obj = "�ѶϿ���";
						// ͬ��������ֹ������߳�ͬʱ����handler�����ͻ
						synchronized (this.getClass()) {
							handler.sendMessage(msg);
						}
						e.printStackTrace();
					}
				}

			}.start();

			// ����һ�����߳�����ȡ��������Ӧ������
			// new Thread() {

			// @Override
			// public void run() {
			// String content = null;
			//
			// // ���ϵĶ�ȡSocket������������
			// try {
			// while ((content = br.readLine()) != null) {
			// // ÿ����ȡ�����Է�����������֮�󣬷��͵���Ϣ֪ͨ����
			// // ������ʾ������
			// Message msg = new Message();
			// msg.what = 0x123;
			// msg.obj = content;
			// //ͬ��������ֹ������߳�ͬʱ����handler�����ͻ
			// synchronized (this.getClass()) {
			// handler.sendMessage(msg);
			// }
			// }
			// } catch (IOException io) {
			// io.printStackTrace();
			// }
			// }
			//
			// }.start();

			// Ϊ��ǰ�̳߳�ʼ��Looper
//			Looper.prepare();
//			// ����revHandler����
//			revHandler = new Handler() {
//
//				@Override
//				public void handleMessage(Message msg) {
//					// ���յ�UI�̵߳����û����������
//					if (msg.what == 0x345) {
//						// ���û����ı������������д������
//						try {
//							os.write((msg.obj.toString()).getBytes("gbk"));
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//					}
//				}
//
//			};
//			// ����Looper
//			Looper.loop();
			
		} catch (SocketTimeoutException e) {
			Message msg = new Message();
			msg.what = 0x123;
			msg.obj = "�������ӳ�ʱ��";
			synchronized (this.getClass()) {
				handler.sendMessage(msg);
			}
		} catch (IOException io) {
			io.printStackTrace();
		}

	}

}
