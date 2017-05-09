package com.video;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by chang on 2017/5/8 0008.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    private Context mContext;
    private List<video> mVideoList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView videoImage;
        TextView videoName;
        //拿到控件
        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            videoImage = (ImageView) itemView.findViewById(R.id.video_image);
            videoName = (TextView) itemView.findViewById(R.id.video_name);
        }
    }
    public VideoAdapter(List<video> videoList){
        mVideoList = videoList;
    }
    @Override
    public VideoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext  == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.video_item,parent,false);
        //监听打开详情页
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                video video = mVideoList.get(position);
                Intent intent = new Intent(mContext,VideoActivity.class);
                intent.putExtra(VideoActivity.VIDEO_NAME,video.getName());
                intent.putExtra(VideoActivity.VIDEO_IMAGE_ID,video.getVideoId());
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(VideoAdapter.ViewHolder holder, int position) {
        video video = mVideoList.get(position);
        holder.videoName.setText(video.getName());
        Glide.with(mContext).load(video.getVideoId()).into(holder.videoImage);

    }

    @Override
    public int getItemCount() {
        return mVideoList.size();
    }

}
