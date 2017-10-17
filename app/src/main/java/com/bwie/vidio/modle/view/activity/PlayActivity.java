package com.bwie.vidio.modle.view.activity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bwie.vidio.R;
import com.bwie.vidio.modle.bean.MusicPlayBean;
import com.bwie.vidio.modle.net.NetDataCallBack;
import com.bwie.vidio.modle.net.OkHttpUtils;
import com.bwie.vidio.modle.view.customview.ILrcView;
import com.bwie.vidio.utils.Api;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Android自定义View来实现解析lrc歌词并同步滚动、上下拖动、缩放歌词的功能:
 * http://blog.csdn.net/ouyang_peng/article/details/50813419
 */

public class PlayActivity extends AppCompatActivity implements View.OnClickListener, NetDataCallBack<MusicPlayBean> {
    private ImageView disc, needle, playingPre, playingPlay, playingNext;
    private ObjectAnimator discAnimation, needleAnimation;
    private boolean isPlaying = true;
    private List<MusicPlayBean> musicPlayBeen;
    private ImageView mBacklast;
    /**
     * 无音乐
     */
    private TextView mShowtitle;
    private ImageView mDisc;
    private ImageView mNeedle;
    private RelativeLayout mLin22;
    /**
     * 00:00
     */
    private TextView mTvCurrentTime;
    private SeekBar mSbProgress;
    /**
     * 00:00
     */
    private TextView mTvTotalTime;
    private ImageView mIvMode;
    private ImageView mPlayingPre;
    private ImageView mPlayingPlay;
    private ImageView mPlayingNext;
    private LinearLayout mActivityMain;
    private ImageView mDisc2;
    private MediaPlayer player;
    private String ids;
    private OkHttpUtils okhttputils;
    int p = 0;
    //_______________________-----------------------------------------------------
    public final static String TAG = "MainActivity";

    //自定义LrcView，用来展示歌词
    ILrcView mLrcView;
    //更新歌词的频率，每秒更新一次
    private int mPalyTimerDuration = 1000;
    //更新歌词的定时器
    private Timer mTimer;
    //更新歌词的定时任务
    private TimerTask mTask;
    //_______________________-----------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        initView();
        Intent intent = getIntent();
        //得到歌曲的id
        ids = intent.getStringExtra("aa");
        String s = Api.SONG_PLAY_ID + ids;
        okhttputils = new OkHttpUtils();
        okhttputils.getdata(s, this, MusicPlayBean.class);
        initViews();
        setAnimations();
        player = new MediaPlayer();
//        try {
//            player.setDataSource(musicPlayBeen.get(0).getSonginfo().getSong_source());
//            player.prepare();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


    }

    private void initViews() {
        musicPlayBeen = new ArrayList<>();
        playingPre = (ImageView) findViewById(R.id.playing_pre);
        playingPlay = (ImageView) findViewById(R.id.playing_play);
        playingNext = (ImageView) findViewById(R.id.playing_next);
        disc = (ImageView) findViewById(R.id.disc);
        needle = (ImageView) findViewById(R.id.needle);
        playingPre.setOnClickListener(this);
        playingPlay.setOnClickListener(this);
        playingNext.setOnClickListener(this);

        //获取自定义的LrcView
        mLrcView = (ILrcView) findViewById(R.id.lrcView);
    }

    //动画设置
    private void setAnimations() {
        discAnimation = ObjectAnimator.ofFloat(disc, "rotation", 0, 360);
        discAnimation = ObjectAnimator.ofFloat(mDisc2, "rotation", 0, 360);
        discAnimation.setDuration(20000);
        discAnimation.setInterpolator(new LinearInterpolator());
        discAnimation.setRepeatCount(ValueAnimator.INFINITE);
        needleAnimation = ObjectAnimator.ofFloat(needle, "rotation", 335, 360);
        needle.setPivotX(0);
        needle.setPivotY(0);
        needleAnimation.setDuration(800);
        needleAnimation.setInterpolator(new LinearInterpolator());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            //前一曲
            case R.id.playing_pre:
                if (discAnimation != null) {
                    discAnimation.end();
                    playing();
                }
                break;
            //播放中
            case R.id.playing_play:
                if (player == null) {
                    //监听：准备完成的监听
                    player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mediaPlayer) {
                            mediaPlayer.start();
                            int duration = mediaPlayer.getDuration();
                            mSbProgress.setMax(duration);
                            new MyThread().start();
                        }
                    });
                } else if (player.isPlaying()) {
                    player.pause();
                } else {
                    player.start();
                    int duration = player.getDuration();
                    //设置进度条
                    mSbProgress.setMax(duration);
                    mSbProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {
                            p = seekBar.getProgress();
                        }
                    });

                    new MyThread().start();
                }
                if (isPlaying) {
                    playing();

                } else {
                    if (needleAnimation != null) {
                        needleAnimation.reverse();
                        needleAnimation.end();
                    }
                    if (player.isPlaying() && player != null) {
                        player.pause();
                    }
                    if (discAnimation != null && discAnimation.isRunning()) {
                        discAnimation.cancel();
                        float valueAvatar = (float) discAnimation.getAnimatedValue();
                        discAnimation.setFloatValues(valueAvatar, 360f + valueAvatar);
                    }
                    playingPlay.setImageResource(R.drawable.ic_play);
                    isPlaying = true;
                }

                break;
            //下一曲
            case R.id.playing_next:
                if (discAnimation != null) {
                    discAnimation.end();
                    playing();
                    next();
                }
                break;
            default:
                break;
            case R.id.backlast:
                finish();
                break;
            case R.id.showtitle:
                break;
            case R.id.disc:
                break;
            case R.id.needle:
                break;
            case R.id.lin22:
                break;
            case R.id.tv_current_time:
                break;
            case R.id.sb_progress:
                break;
            case R.id.tv_total_time:
                break;
            case R.id.iv_mode:
                break;
            case R.id.activity_main:
                break;
        }
    }

    private void next() {
        p++;
        p = p % musicPlayBeen.size();
        String s = Api.SONG_PLAY_ID + ids;
        okhttputils.getdata(s, new NetDataCallBack<MusicPlayBean>() {
            @Override
            public void success(MusicPlayBean o) {
                final MediaPlayer mediaPlayers = new MediaPlayer();
                if (mediaPlayers != null) {

                    try {
                        mediaPlayers.reset();
                        mediaPlayers.setDataSource(o.getBitrate().getFile_link());
                        mediaPlayers.prepareAsync();
                        mediaPlayers.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mediaPlayer) {
                                mediaPlayers.start();
                                int duration = mediaPlayer.getDuration();
                                //设置进度条的最大值为音乐的总时长
                                mSbProgress.setMax(duration);
//                            new NextThread().start();
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void faild(int positon, String str) {
            }
        }, MusicPlayBean.class);
    }

    //播放时动画设置和图片切换,播放
    private void playing() {
        needleAnimation.start();
        discAnimation.start();
        playingPlay.setImageResource(R.drawable.ic_pause);
        isPlaying = false;
    }

    @Override
    public void success(MusicPlayBean musicPlayBean) {
        musicPlayBeen.add(musicPlayBean);
        mShowtitle.setText(musicPlayBeen.get(0).getSonginfo().getAlbum_title());
        ImageLoader.getInstance().displayImage(musicPlayBeen.get(0).getSonginfo().getPic_small(), mDisc2);
    }

    @Override
    public void faild(int positon, String str) {
    }

    private void initView() {
        mBacklast = (ImageView) findViewById(R.id.backlast);
        mShowtitle = (TextView) findViewById(R.id.showtitle);
        mDisc = (ImageView) findViewById(R.id.disc);
        mNeedle = (ImageView) findViewById(R.id.needle);
        mLin22 = (RelativeLayout) findViewById(R.id.lin22);
        mTvCurrentTime = (TextView) findViewById(R.id.tv_current_time);
        mSbProgress = (SeekBar) findViewById(R.id.sb_progress);
        mTvTotalTime = (TextView) findViewById(R.id.tv_total_time);
        mIvMode = (ImageView) findViewById(R.id.iv_mode);
        mPlayingPre = (ImageView) findViewById(R.id.playing_pre);
        mPlayingPlay = (ImageView) findViewById(R.id.playing_play);
        mPlayingNext = (ImageView) findViewById(R.id.playing_next);
        mActivityMain = (LinearLayout) findViewById(R.id.activity_main);
        mBacklast.setOnClickListener(this);
        mShowtitle.setOnClickListener(this);
        mDisc.setOnClickListener(this);
        mNeedle.setOnClickListener(this);
        mLin22.setOnClickListener(this);
        mTvCurrentTime.setOnClickListener(this);
        mSbProgress.setOnClickListener(this);
        mTvTotalTime.setOnClickListener(this);
        mIvMode.setOnClickListener(this);
        mPlayingPre.setOnClickListener(this);
        mPlayingPlay.setOnClickListener(this);
        mPlayingNext.setOnClickListener(this);
        mActivityMain.setOnClickListener(this);
        mDisc2 = (ImageView) findViewById(R.id.disc2);
    }

    class MyThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (mSbProgress.getProgress() <= mSbProgress.getMax()) {
                int currentPosition = player.getCurrentPosition();
                mSbProgress.setProgress(currentPosition);
            }
        }
    }

}