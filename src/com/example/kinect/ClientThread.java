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
	// 定义向UI线程发送消息的Handler对象
	Handler handler;
	// 定义接收UI线程的Handler对象
	Handler revHandler;
	// 该线程处理Socket所对用的输入输出流
	BufferedReader br = null;
	OutputStream os = null;

	public ClientThread(Handler handler) {
		this.handler = handler;
	}

	@Override
	public void run() {
		s = new Socket();

		// 连接服务器 并设置连接超时为3秒
		try {
			s.connect(new InetSocketAddress("192.168.191.1", 1234), 3000);
			Message msg = new Message();
			msg.what = 0x111;
			msg.obj = "已连接！";
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
							s.sendUrgentData(0xFF); // 发送心跳包
							Log.v("ang", "已连接！");
							msg.what = 0x111;
							msg.obj = "已连接！";
//							synchronized (this.getClass()) {
//								handler.sendMessage(msg);
//							}
							Thread.sleep(3 * 1000);// 线程睡眠3秒
						}
					} catch (Exception e) {
						Log.v("ang", "已断开！");
						msg.what = 0x123;
						msg.obj = "已断开！";
						// 同步锁，防止多个子线程同时操作handler引起冲突
						synchronized (this.getClass()) {
							handler.sendMessage(msg);
						}
						e.printStackTrace();
					}
				}

			}.start();

			// 启动一条子线程来读取服务器相应的数据
			// new Thread() {

			// @Override
			// public void run() {
			// String content = null;
			//
			// // 不断的读取Socket输入流的内容
			// try {
			// while ((content = br.readLine()) != null) {
			// // 每当读取到来自服务器的数据之后，发送的消息通知程序
			// // 界面显示该数据
			// Message msg = new Message();
			// msg.what = 0x123;
			// msg.obj = content;
			// //同步锁，防止多个子线程同时操作handler引起冲突
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

			// 为当前线程初始化Looper
//			Looper.prepare();
//			// 创建revHandler对象
//			revHandler = new Handler() {
//
//				@Override
//				public void handleMessage(Message msg) {
//					// 接收到UI线程的中用户输入的数据
//					if (msg.what == 0x345) {
//						// 将用户在文本框输入的内容写入网络
//						try {
//							os.write((msg.obj.toString()).getBytes("gbk"));
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//					}
//				}
//
//			};
//			// 启动Looper
//			Looper.loop();
			
		} catch (SocketTimeoutException e) {
			Message msg = new Message();
			msg.what = 0x123;
			msg.obj = "网络连接超时！";
			synchronized (this.getClass()) {
				handler.sendMessage(msg);
			}
		} catch (IOException io) {
			io.printStackTrace();
		}

	}

}
