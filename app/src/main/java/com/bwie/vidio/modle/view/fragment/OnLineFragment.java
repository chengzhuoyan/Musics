package com.bwie.vidio.modle.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bwie.vidio.R;
import com.bwie.vidio.modle.view.activity.LoginActivity;
import com.bwie.vidio.modle.view.apadter.PlaylistAdapter;
import com.bwie.vidio.modle.view.apadter.TitleApdater;
import com.bwie.vidio.modle.app.AppCache;
import com.bwie.vidio.modle.app.LoadStateEnum;
import com.bwie.vidio.modle.bean.SongListInfo;
import com.bwie.vidio.modle.net.HttpClient;
import com.bwie.vidio.utils.NetworkUtils;
import com.bwie.vidio.utils.ViewUtils;
import com.bwie.vidio.utils.binding.Bind;

import java.util.List;

public class OnLineFragment extends BaseFragmnet implements AdapterView.OnItemClickListener{
    private ListView listView;
    private List<SongListInfo> list ;
    private TextView textView;
    private TitleApdater titleApdater;
    private HttpClient httpClient;
    @Bind(R.id.ll_loading)
    private LinearLayout llLoading;
    @Bind(R.id.ll_load_fail)
    private LinearLayout llLoadFail;
    @Override
    protected int setLayoutResouceId() {
        return R.layout.fragment_playlist;
    }

    @Override
    protected void setInitView(View mView, Bundle savedInstanceState) {
        textView = (TextView) mView.findViewById(R.id.tv_mess);
        listView = (ListView) mView.findViewById(R.id.lv_song_list);

    }
    @Override
    protected void setLodaData() {
    }
    @Override
    protected void setInitData() {
        if(!NetworkUtils.isNetworkAvailable(getContext())){
            ViewUtils.changeViewState(listView,llLoading,llLoadFail, LoadStateEnum.LOAD_FAIL);
            return;
        }
        list = AppCache.getSongListInfos();
        if(list.isEmpty()){
            String [] titles = getResources().getStringArray(R.array.online_music_list_title);
            String[] types = getResources().getStringArray(R.array.online_music_list_type);
            for(int i=0;i<titles.length;i++){
                SongListInfo info = new SongListInfo();
                info.setTitle(titles[i]);
                info.setType(types[i]);
                list.add(info);
            }
        }

        PlaylistAdapter playlistAdapter = new PlaylistAdapter(list);
        listView.setAdapter(playlistAdapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        SongListInfo songListInfo = list.get(i);
        Intent intent = new Intent(getContext(), LoginActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("a",songListInfo);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}

