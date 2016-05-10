package com.example.mycloset;

import com.example.kinect.DetailsActivity;
import com.example.mycloset.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView.ScaleType;

public class WebViewActivity extends Activity {
	private String val;// ���մ�����flag
	// private String url= "http://taobao.com";
	private String url;
	private WebView webView;
	private ProgressDialog dialog;// webview����ҳ��ʱ�����Ľ��ȶԻ���

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.web);
		val = getIntent().getStringExtra("flag");// ���հ�ť�����ļ�ֵ��flag
		initView();// ��ʼ������

	}

	private void changeUrl() {
		if (val.equals("shop")) {
			url = "http://taobao.com";
		}
		if (val.equals("10")) {
			url = "https://item.taobao.com/item.htm?spm=a230r.1.999.23.paXsyl&id=45837113640&ns=1#detail";
		}
		if (val.equals("11")) {
			url = "https://item.taobao.com/item.htm?spm=a230r.1.999.7.IxFltW&id=526329159960&ns=1#detail";
		}
		if (val.equals("12")) {

		}
		if (val.equals("13")) {

		}
		if (val.equals("14")) {

		}
		if (val.equals("15")) {

		}
		if (val.equals("16")) {

		}
		if (val.equals("17")) {

		}

	}

	private void initView() {
		// TODO Auto-generated method stub
		webView = (WebView) findViewById(R.id.webView);
		changeUrl();

		// ����WebViewĬ��ͨ��������������ϵͳ���������ҳ����Ϊ��ʹ����ҳ��WebView�д�
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {

				if (url.startsWith("http:") || url.startsWith("https:")) {
					return false;
				}
				// Otherwise allow the OS to handle things like tel, mailto,
				// etc.
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
				startActivity(intent);

				// ����ֵ��trueʱ������ҳ��WebView��ȥ�򿪣� �����false�����ϵͳ��������������
				return true;
			}
		});
		webView.loadUrl(url);

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
					dialog = new ProgressDialog(WebViewActivity.this);
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
//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		// ��keycode����λ���ذ���ʱ
//		if (keyCode == KeyEvent.KEYCODE_BACK) {
//			// Toast.makeText(this, webView.getUrl(),
//			// Toast.LENGTH_SHORT).show();
//			// ��Ϊ�Ѿ���webview����url���ط�ʽʹ��ҳ��ʾ��webview�ϣ�����webview���Զ�������ʷ��¼
//			// ����ֻ���ж�webview�ܷ񷵻���һ����
//			if (webView.canGoBack()) {
//				 //����ܣ��򷵻���һ����
//				Log.v("ang","goback");
//				 webView.goBack();
////				/* ����һ��Activity���ؽ�� */
////				int i = Integer.parseInt(val);
////				Log.v("ang", i + "");
////				this.setResult(i);
////				/* ������Activity */
////				this.finish();
//				
//	      
////	                //������ʹ��Intent����
////	                Intent intent = new Intent();
////	                //�ѷ������ݴ���Intent
////	                intent.putExtra("result", val);
////	                //���÷�������
////	                WebViewActivity.this.setResult(RESULT_OK, intent);
////	                //�ر�Activity
////	                WebViewActivity.this.finish();
//				
//				return true;
//			} else {
//				// ������ܷ�����һ�������ѵ��ף����˳�����
//				Log.v("ang","shit");
//				System.exit(0);
//			}
//
//		}
//		return super.onKeyDown(keyCode, event);
//	}



	// // �����ص�ҳ�洫��flag,�����ٴΰ�tryonʱ�޷�����������ͻ���
	// private void goBackFlag() {
	// Intent i = new Intent(this, DetailsActivity.class);
	// i.putExtra("flag", val);
	// startActivity(i);
	// }
}
