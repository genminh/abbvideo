package com.video;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private video[] videos = {
            new video("Apple",R.drawable.apple),
            new video("Banana",R.drawable.banana),
            new video("Cherry",R.drawable.cherry),
            new video("Grape",R.drawable.grape),
            new video("Mango",R.drawable.mango),
            new video("Orange",R.drawable.orange),
            new video("Pear",R.drawable.pear),
            new video("Pineapple",R.drawable.pineapple),
            new video("Strawberry",R.drawable.strawberry),
            new video("Watermelon",R.drawable.watermelon)
    };
    private List<video> videoList = new ArrayList<>();
    private VideoAdapter adapter;
    //下拉刷新
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        //拿到浮动的图片
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        //能到侧滑NavigationView
        navigationView = (NavigationView) findViewById(R.id.design_navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        initVideos();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setOnClickListener(this);
        //设置每一行显示的图片
        GridLayoutManager layoutManager = new GridLayoutManager(this,3);
        //放到容器RecyclerView中
        recyclerView.setLayoutManager(layoutManager);
        //初始化adapter
        adapter = new VideoAdapter(videoList);
        //设置到recycler
        recyclerView.setAdapter(adapter);
       // navigationView.setCheckedItem(R.id.menu_played);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        //设置下拉刷新进度条的颜色
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        //设置下拉监听器
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshVideo();
            }
        });

    }
    //刷新监听
    private void refreshVideo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initVideos();
                        //通知adapter更新数据
                        adapter.notifyDataSetChanged();
                        //关闭刷新
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    private void initVideos() {
        videoList.clear();
        for (int i = 0;i < 100; i ++){
            //获取随机
            Random random = new Random();
            int index = random.nextInt(videos.length);
            videoList.add(videos[index]);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }
    //监听ToolBar上的按钮
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.search:
                Toast.makeText(this, "搜索", Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting:
                Toast.makeText(this, "设置", Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return true;
    }
    //判断返回键退出
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        //监听back返回键
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK){
            //显示dialog
            new AlertDialog.Builder(this)
                    .setMessage("你确定要退出")
                    .setPositiveButton("退出", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    })
                    .setNegativeButton("再看看",null)
                    .show();
            return true;//此时就不会退出
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.recycler_view:
                Toast.makeText(this, "图片", Toast.LENGTH_SHORT).show();
        }
    }
    //侧滑选项监听
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_played:
                Toast.makeText(MainActivity.this, "播放历史", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_download:
                Toast.makeText(MainActivity.this, "我的下载", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_collect:
                Toast.makeText(MainActivity.this, "我的收藏", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_make_money:
                Toast.makeText(MainActivity.this, "我要赚钱", Toast.LENGTH_SHORT).show();
                break;
        }
        mDrawerLayout.closeDrawers();
        return false;
    }
}
