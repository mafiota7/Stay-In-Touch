package com.example.zhivko.stayintouch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

public class NewsDeatilsActivity extends AppCompatActivity {

    private WebView webView;
    private Button backButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_deatils);
        webView = (WebView) findViewById(R.id.web_view);
        if(getIntent().getExtras()!= null && !getIntent().getExtras().isEmpty()) {
            Bundle bundle = getIntent().getExtras();
            /*
            loading news in to the web view by setting url
             */
            webView.loadUrl(bundle.getString("link"));
        }
        backButton = (Button) findViewById(R.id.button_back_news_deatils);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
