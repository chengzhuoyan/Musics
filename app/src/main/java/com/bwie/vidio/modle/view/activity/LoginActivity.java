package com.bwie.vidio.modle.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.vidio.R;
import com.bwie.vidio.modle.bean.JavaBean;
import com.bwie.vidio.modle.bean.SongListInfo;
import com.bwie.vidio.modle.net.NetDataCallBack;
import com.bwie.vidio.modle.view.apadter.SingListAdapter;
import com.bwie.vidio.utils.Api;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements NetDataCallBack<JavaBean> {

    private SongListInfo a;
    private TextView online_singtitle;
    private ImageView iv_cover;
    private ImageView iv_header_bg;
    private TextView tv_title;
    private TextView tv_update_date;
    private TextView tv_comment;
    private List<JavaBean> list;
    private static List<JavaBean.SongListBean> list2 = new ArrayList<>();
    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.online_music_all);
        initView();
        ScrollView sv = (ScrollView) findViewById(R.id.sc);//获取scrollView组件
        sv.fullScroll(ScrollView.FOCUS_DOWN);//滚动到底部
    }

    private void initView() {
        list = new ArrayList<>();
        online_singtitle = (TextView) findViewById(R.id.online_singtitle);
        iv_cover = (ImageView) findViewById(R.id.iv_cover);
        iv_header_bg = (ImageView) findViewById(R.id.iv_header_bg);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_update_date = (TextView) findViewById(R.id.tv_update_date);
        tv_comment = (TextView) findViewById(R.id.tv_comment);
        listview = (ListView) findViewById(R.id.online_singlist);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        a = (SongListInfo) bundle.get("a");
        Log.e("a", "" + a.getCoverUrl());
        //网络获取数据
        getData();

    }

    private void getData() {
        String TYPE = a.getType();
        String s = Api.SONG_LIST_TYPE + TYPE;
        com.bwie.vidio.modle.net.OkHttpUtils okhttputils = new com.bwie.vidio.modle.net.OkHttpUtils();
        okhttputils.getdata(s, this, JavaBean.class);
    }

    @Override
    public void success(JavaBean javaBean) {
        list.add(javaBean);
        online_singtitle.setText(list.get(0).getBillboard().getName());
        tv_title.setText(javaBean.getBillboard().getName());
        tv_update_date.setText(javaBean.getBillboard().getUpdate_date());
        tv_comment.setText(javaBean.getBillboard().getComment());
        ImageLoader.getInstance().displayImage(javaBean.getBillboard().getPic_s192(), iv_cover);
        ImageLoader.getInstance().displayImage(javaBean.getBillboard().getPic_s444(), iv_header_bg);
        list2.addAll(javaBean.getSong_list());
        listview.setAdapter(new SingListAdapter(list2, this));
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //点击条目提示播放的歌曲
                Toast.makeText(LoginActivity.this, "正在播放：" + list2.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                Intent playIntent = new Intent(LoginActivity.this, PlayActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("aa",listInfo);
//                playIntent.putExtras(bundle);
                playIntent.putExtra("aa", list2.get(position).getSong_id());//音乐id
                startActivity(playIntent);
            }
        });
    }
    @Override
    public void faild(int positon, String str) {
    }
}
