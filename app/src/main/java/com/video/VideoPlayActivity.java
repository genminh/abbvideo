package com.video;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.video.play.Utils;

public class VideoPlayActivity extends AppCompatActivity {
    private VideoView videoPlayView;
    private LinearLayout vido_play_linearlayout;
  //  private RelativeLayout video_play_progressBar;
   // private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);
        videoPlayView = (VideoView) findViewById(R.id.video_play_view);
        vido_play_linearlayout = (LinearLayout) findViewById(R.id.vido_play_linearlayout);
        //video_play_progressBar = (RelativeLayout) findViewById(R.id.video_play_progressBar);
        //网络视频
        String videoUrl2 = Utils.videoUrl;
        final Uri uri = Uri.parse(videoUrl2);
        //设置视频监控器
        videoPlayView.setMediaController(new MediaController(VideoPlayActivity.this));
        //加载读取的dialog
        videoPlayView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                    @Override
                    public boolean onInfo(MediaPlayer mp, int what, int extra) {
                        if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START){
                            //开始播放
                            vido_play_linearlayout.setVisibility(View.GONE);
                        }else if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START){
                            //视频缓冲
                            vido_play_linearlayout.setVisibility(View.VISIBLE);
                        }else if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END) {
                            //视频缓冲完成
                            vido_play_linearlayout.setVisibility(View.GONE);
                        }
                        return false;
                    }
                });
            }
        });
        //播放完成回调
        videoPlayView.setOnCompletionListener( new MyPlayerOnCompletionListener());
        //设置视频路径
        videoPlayView.setVideoURI(uri);
        //开始播放视频
        videoPlayView.start();
    }
    class MyPlayerOnCompletionListener implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mp) {
            Toast.makeText( VideoPlayActivity.this, "播放完成了", Toast.LENGTH_SHORT).show();
        }
    }
}
