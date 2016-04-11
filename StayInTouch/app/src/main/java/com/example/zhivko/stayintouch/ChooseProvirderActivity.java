package com.example.zhivko.stayintouch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.zhivko.stayintouch.controlers.RSSReaderTask;

public class ChooseProvirderActivity extends AppCompatActivity {

    public static final String PROVIDER = "provider";


    private ImageButton bbc, sky, fox, science, cbs, computer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_provirder);
        bbc = (ImageButton) findViewById(R.id.button_bbc);
        bbc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseProvirderActivity.this, NewsProvirdersActivity.class);
                intent.putExtra(PROVIDER, RSSReaderTask.WWW_FEEDS_BBC_CO_UK_NEWS_RSS_XML);
                startActivity(intent);
            }
        });
        sky = (ImageButton) findViewById(R.id.button_sky);
        sky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseProvirderActivity.this, NewsProvirdersActivity.class);
                intent.putExtra(PROVIDER, RSSReaderTask.WWW_SKY_NEWS_HOME_XML);
                startActivity(intent);
            }
        });
        fox = (ImageButton) findViewById(R.id.button_fox);
        fox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseProvirderActivity.this, NewsProvirdersActivity.class);
                intent.putExtra(PROVIDER, RSSReaderTask.WWW_FOXNEWS_COM_ABOUT_RSS_FEEDBURNER_FOXNEWS_LATEST);
                startActivity(intent);
            }
        });
        science = (ImageButton) findViewById(R.id.button_science);
        science.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseProvirderActivity.this, NewsProvirdersActivity.class);
                intent.putExtra(PROVIDER, RSSReaderTask.WWW_SCIENCEMAG_ORG_RSS_NEWS_XML);
                startActivity(intent);
            }
        });
        cbs = (ImageButton) findViewById(R.id.button_cbs);
        cbs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseProvirderActivity.this, NewsProvirdersActivity.class);
                intent.putExtra(PROVIDER, RSSReaderTask.WWW_CBSNEWS_COM_LATEST_RSS_MAIN);
                startActivity(intent);
            }
        });

        computer = (ImageButton) findViewById(R.id.button_computer);
        computer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseProvirderActivity.this, NewsProvirdersActivity.class);
                intent.putExtra(PROVIDER, RSSReaderTask.WWW_COMPUTERWEEKLY_COM_RSS_LATEST_NEWS);
                startActivity(intent);
            }
        });



    }
}
