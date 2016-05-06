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
	private ProgressDialog dialog;// webview加载页面时跳出的进度对话框

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.shop);
		setContentView(R.layout.web);
		// //通过Intent调用系统浏览器显示网页，如果有几个浏览器，则可供选择
		// Uri uri=Uri.parse(url);
		// Intent intent=new Intent(Intent.ACTION_VIEW,uri);
		// startActivity(intent);
		init();// 初始化函数

	}

	private void init() {
		// TODO Auto-generated method stub
		webView = (WebView) findViewById(R.id.webView);
		// WebView加载页面
		// //(1)WebView加载本地页面（存放在assets文件夹中）
		// webView.loadUrl("file:///android_asset/example.html");
		// (2)WebView加载外部页面
		// webView.loadUrl("http://taobao.com");
		webView.loadUrl(url);
		// 覆盖WebView默认通过第三方或者是系统浏览器打开网页的行为，使得网页在WebView中打开
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				/*
				 * 返回值是true时控制网页在WebView中去打开， 如果是false则调用系统或第三方浏览器打开
				 */
				view.loadUrl(url);// webview对象加载传过来的url地址
				return true;
			}
			// WebViewClient帮助处理一些页面控制和请求通知
		});
		// WebView中启用支持JavaScript脚本语言
		WebSettings settings = webView.getSettings();
		settings.setJavaScriptEnabled(true);
		// WebView优先使用缓存加载(这样就可以显著提速webview)
		settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

		webView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				// newProgress：1-100之间的整数，反映页面加载进度
				if (newProgress == 100) {
					closeDialog();// 网页加载完毕，关闭ProgressDialog
				}
				// 网页正在加载，打开ProgressDialog，并接受newProgress传过来的参数
				else {
					openDialog(newProgress);
				}
			}

			private void closeDialog() {
				// dialog不为空且正在显示
				if (dialog != null && dialog.isShowing()) {
					dialog.dismiss();// 取消显示
					dialog = null;// 对象设为空
				}
			}

			private void openDialog(int newProgress) {
				// TODO Auto-generated method stub
				if (dialog == null) {
					dialog = new ProgressDialog(ShopActivity.this);
					dialog.setTitle("正在加载");
					dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
					dialog.setProgress(newProgress);
					dialog.show();// 显示进度

				}
				// dialog不为空时，实时刷新进度即可
				else {
					dialog.setProgress(newProgress);
				}

			}
		});
	}

	// 改写手机返回键
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 当keycode代码位返回按键时
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// Toast.makeText(this, webView.getUrl(),
			// Toast.LENGTH_SHORT).show();
			// 因为已经用webview覆盖url加载方式使网页显示在webview上，所以webview会自动生成历史记录
			// 所以只用判断webview能否返回上一界面
			if (webView.canGoBack()) {
				// 如果能，则返回上一界面
				webView.goBack();
				return true;
			} else {
				// 如果不能返回上一级，即已到底，则退出程序
				System.exit(0);
			}

		}
		return super.onKeyDown(keyCode, event);
	}

}
