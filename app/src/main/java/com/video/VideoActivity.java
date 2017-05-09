package com.video;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class VideoActivity extends AppCompatActivity implements View.OnClickListener {
    private  FloatingActionButton floatingActionButton;
    public static final String VIDEO_NAME = "video_name";
    public static final String VIDEO_IMAGE_ID = "video_image_id";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        Intent intent = getIntent();
        String videoName = intent.getStringExtra(VIDEO_NAME);
        int videoImageId = intent.getIntExtra(VIDEO_IMAGE_ID,0);
        Toolbar video_toolbar = (Toolbar) findViewById(R.id.video_toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        ImageView videoImageView = (ImageView) findViewById(R.id.video_image_view);
        TextView videoContentText = (TextView) findViewById(R.id.video_content_text);
        setSupportActionBar(video_toolbar);
        ActionBar actionBar2 = getSupportActionBar();
        if (actionBar2 !=  null){
            actionBar2.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbarLayout.setTitle(videoName);
        Glide.with(this).load(videoImageId).into(videoImageView);
        String videoContent = generateVideoContent(videoName);
        videoContentText.setText(videoContent);
        //找到悬浮的按钮
        floatingActionButton = (FloatingActionButton) findViewById(R.id.video_floatingButton);
        floatingActionButton.setOnClickListener(this);
    }
    //详情显示
    private String generateVideoContent(String videoName) {
        StringBuilder videoContent = new StringBuilder();
        for (int i = 0;i < 500; i ++){
            videoContent.append(videoName);
        }
        return videoContent.toString();
    }
    //返回监听
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.video_floatingButton:
                Intent intent = new Intent(VideoActivity.this,VideoPlayActivity.class);
                startActivity(intent);
                break;

        }
    }
}
