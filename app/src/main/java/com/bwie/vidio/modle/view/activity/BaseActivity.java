package com.bwie.vidio.modle.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setViewId());
        initView();
        initData();
    }
    /**
     * 获取布局文件
     */
    abstract  int setViewId();
    /**
     * 初始化控件
     */
    abstract void initView();

    /**
     * 初始化数据
     */
    abstract  void initData();



}
