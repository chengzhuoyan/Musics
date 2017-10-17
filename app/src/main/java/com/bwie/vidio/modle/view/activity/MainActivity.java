package com.bwie.vidio.modle.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bwie.vidio.R;
import com.bwie.vidio.modle.bean.SongListInfo;
import com.bwie.vidio.modle.view.apadter.MusicApdater;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private ViewPager viewPager;
    private MusicApdater musicApdater;
    private TextView tv_local;
    private TextView tv_online;
    private SlidingMenu menu;
    private ImageView imageView;
    private TextView slidingmenu_title2;
    private TextView slidingmenu_title1;
    private TextView slidingmenu_title3;
    private TextView slidingmenu_title4;
    private TextView slidingmenu_title5;
    private View view;
    private List<SongListInfo> songList ;

    @Override
    int setViewId() {
        return R.layout.activity_main;
    }

    /**
     * 初始化控件
     */
    @Override
    void initView() {
        imageView = (ImageView) findViewById(R.id.images);
        menu = new SlidingMenu(MainActivity.this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        menu.setShadowWidth(160);
        menu.setBehindWidth(400);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        view = View.inflate(this, R.layout.tabbar, null);
        getView();//查找左侧页面的数据
        menu.setMenu(view);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menu.toggle();
            }
        });
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        tv_local = (TextView) findViewById(R.id.local_music);
        tv_online = (TextView) findViewById(R.id.online_music);
        tv_local.setOnClickListener(this);
        tv_online.setOnClickListener(this);
        //viewPager滑动监听
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        tv_local.setTextColor(Color.WHITE);
                        tv_online.setTextColor(Color.GRAY);
                        break;
                    case 1:
                        tv_online.setTextColor(Color.WHITE);
                        tv_local.setTextColor(Color.GRAY);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void getView() {
        slidingmenu_title1 = (TextView) view.findViewById(R.id.slidingmenu_title1);
        slidingmenu_title2 = (TextView) view.findViewById(R.id.slidingmenu_title2);
        slidingmenu_title3 = (TextView) view.findViewById(R.id.slidingmenu_title3);
        slidingmenu_title4 = (TextView) view.findViewById(R.id.slidingmenu_title4);
        slidingmenu_title5 = (TextView) view.findViewById(R.id.slidingmenu_title5);
        slidingmenu_title1.setOnClickListener(this);
        slidingmenu_title2.setOnClickListener(this);
        slidingmenu_title3.setOnClickListener(this);
        slidingmenu_title4.setOnClickListener(this);
        slidingmenu_title5.setOnClickListener(this);
        //底部点击播放切换暂停音乐的控件
        ImageView playing_img = (ImageView) findViewById(R.id.playing_img);
        TextView playing_title = (TextView) findViewById(R.id.playing_title);
        ImageView play_ctrl = (ImageView) findViewById(R.id.play_ctrl);
        ImageView next_song = (ImageView) findViewById(R.id.next_song);
        SeekBar pro_bar = (SeekBar) findViewById(R.id.pro_bar);
    }

    @Override
    void initData() {
        musicApdater = new MusicApdater(getSupportFragmentManager());
        viewPager.setAdapter(musicApdater);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.local_music:
                viewPager.setCurrentItem(0);
                break;
            case R.id.online_music:
                viewPager.setCurrentItem(1);
                break;
            case R.id.slidingmenu_title1:
                //功能设置
                startActivity(new Intent(MainActivity.this, GongNengSlidingMenu.class));
                break;
            case R.id.slidingmenu_title2:
                //夜间模式
                SharedPreferences preferences = getSharedPreferences("use", MODE_PRIVATE);
                // 默认第一个为夜间
                boolean night = preferences.getBoolean("night", false);
                if (night) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    preferences.edit().putBoolean("night", false).commit();
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    preferences.edit().putBoolean("night", true).commit();
                }
                recreate();
                break;
            case R.id.slidingmenu_title3:
                break;
            case R.id.slidingmenu_title4:
                //点击退出工程
                System.exit(0);
                break;
            case R.id.slidingmenu_title5:
                //关于
                startActivity(new Intent(MainActivity.this, GuanYuSlidingMenu.class));
                break;
        }
    }

    /**
     * 给mainactivity底部的音乐播放添加点击事件，点击跳转到歌词播放页面
     */
    public void playingLin(View view) {
        switch (view.getId()) {
            case R.id.playing_lin:
                //传递歌曲的id过去
//                SongListInfo listInfo = songList.get();
                Intent playIntent = new Intent(MainActivity.this, PlayActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("aa",listInfo);
//                playIntent.putExtras(bundle);
                startActivity(playIntent);
                break;
        }
    }
}
