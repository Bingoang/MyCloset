package com.example.mycloset;

import com.example.mycloset.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends Activity {
	private String val;// 接收传来的flag
	// private String url= "http://taobao.com";
	private String url;
	private WebView webView;
	private ProgressDialog dialog;// webview加载页面时跳出的进度对话框

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.web);
		val = getIntent().getStringExtra("flag");// 接收按钮传来的价值对flag
		initView();// 初始化函数

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

		// 覆盖WebView默认通过第三方或者是系统浏览器打开网页的行为，使得网页在WebView中打开
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

				// 返回值是true时控制网页在WebView中去打开， 如果是false则调用系统或第三方浏览器打开
				return true;
			}
		});
		webView.loadUrl(url);

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
					dialog = new ProgressDialog(WebViewActivity.this);
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

}
