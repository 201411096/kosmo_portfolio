package com.example.okidoghere2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

public class SecondActivity extends AppCompatActivity {


    private static String urlChange="";
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_activiry);

        //data 수신
//        Intent intent = getIntent();
//        String title = intent.getExtras().getString("title");
//        String body = intent.getExtras().getString("body");
//        String uri = intent.getExtras().getString("uri");
//        urlChange=uri;


        getSupportActionBar().setTitle("SecondActivity");
        webView = (WebView)findViewById(R.id.webView);
        webView.setWebViewClient(new SslWebViewConnect());
        webView.loadUrl(urlChange);

    }

    // 로그에 토큰 출력
    public void println(String data){
        Log.d("FMS", data);
    }
    @Override
    protected void onNewIntent(Intent intent) {
        println("onNewIntent 호출 됨");
        if (intent != null) {
            processIntent(intent);
        }

        super.onNewIntent(intent);
    }

    private void processIntent(Intent intent){
        String from = intent.getStringExtra("from");
        if (from == null){
            println("from is null");
            return;
        }

//        String contents = intent.getStringExtra("contents");
        String title = intent.getStringExtra("title");
        String body = intent.getStringExtra("body");
        String uri = intent.getStringExtra("uri");
        webView.loadUrl(uri);
//        println("DATA : " + from + ", " + contents);
        println("DATA : " + from + ", title: " + title + ", body:" + body );
    }
}