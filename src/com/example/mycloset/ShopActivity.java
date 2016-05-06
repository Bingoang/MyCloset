package com.example.mycloset;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ShopActivity extends Activity {

	private String url = "http://taobao.com";
	private WebView webView;
	private ProgressDialog dialog;// webview����ҳ��ʱ�����Ľ��ȶԻ���

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.shop);
		setContentView(R.layout.web);
		// //ͨ��Intent����ϵͳ�������ʾ��ҳ������м������������ɹ�ѡ��
		// Uri uri=Uri.parse(url);
		// Intent intent=new Intent(Intent.ACTION_VIEW,uri);
		// startActivity(intent);
		init();// ��ʼ������

	}

	private void init() {
		// TODO Auto-generated method stub
		webView = (WebView) findViewById(R.id.webView);
		// WebView����ҳ��
		// //(1)WebView���ر���ҳ�棨�����assets�ļ����У�
		// webView.loadUrl("file:///android_asset/example.html");
		// (2)WebView�����ⲿҳ��
		// webView.loadUrl("http://taobao.com");
		webView.loadUrl(url);
		// ����WebViewĬ��ͨ��������������ϵͳ���������ҳ����Ϊ��ʹ����ҳ��WebView�д�
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				/*
				 * ����ֵ��trueʱ������ҳ��WebView��ȥ�򿪣� �����false�����ϵͳ��������������
				 */
				view.loadUrl(url);// webview������ش�������url��ַ
				return true;
			}
			// WebViewClient��������һЩҳ����ƺ�����֪ͨ
		});
		// WebView������֧��JavaScript�ű�����
		WebSettings settings = webView.getSettings();
		settings.setJavaScriptEnabled(true);
		// WebView����ʹ�û������(�����Ϳ�����������webview)
		settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

		webView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				// newProgress��1-100֮�����������ӳҳ����ؽ���
				if (newProgress == 100) {
					closeDialog();// ��ҳ������ϣ��ر�ProgressDialog
				}
				// ��ҳ���ڼ��أ���ProgressDialog��������newProgress�������Ĳ���
				else {
					openDialog(newProgress);
				}
			}

			private void closeDialog() {
				// dialog��Ϊ����������ʾ
				if (dialog != null && dialog.isShowing()) {
					dialog.dismiss();// ȡ����ʾ
					dialog = null;// ������Ϊ��
				}
			}

			private void openDialog(int newProgress) {
				// TODO Auto-generated method stub
				if (dialog == null) {
					dialog = new ProgressDialog(ShopActivity.this);
					dialog.setTitle("���ڼ���");
					dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
					dialog.setProgress(newProgress);
					dialog.show();// ��ʾ����

				}
				// dialog��Ϊ��ʱ��ʵʱˢ�½��ȼ���
				else {
					dialog.setProgress(newProgress);
				}

			}
		});
	}

	// ��д�ֻ����ؼ�
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// ��keycode����λ���ذ���ʱ
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// Toast.makeText(this, webView.getUrl(),
			// Toast.LENGTH_SHORT).show();
			// ��Ϊ�Ѿ���webview����url���ط�ʽʹ��ҳ��ʾ��webview�ϣ�����webview���Զ�������ʷ��¼
			// ����ֻ���ж�webview�ܷ񷵻���һ����
			if (webView.canGoBack()) {
				// ����ܣ��򷵻���һ����
				webView.goBack();
				return true;
			} else {
				// ������ܷ�����һ�������ѵ��ף����˳�����
				System.exit(0);
			}

		}
		return super.onKeyDown(keyCode, event);
	}

}
