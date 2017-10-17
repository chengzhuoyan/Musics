package com.bwie.vidio.modle.view.apadter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.vidio.R;
import com.bwie.vidio.modle.bean.JavaBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Desc
 * Author 程茁燕
 * Time 2017/10/10.
 */
public class SingListAdapter extends BaseAdapter {
    private static List<JavaBean.SongListBean> list2 = new ArrayList<>();
    private Context context;

    public SingListAdapter(List<JavaBean.SongListBean> list2, Context context) {
        this.list2 = list2;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list2.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderSingList viewHolderSingList ;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.view_holder_music, null);
            viewHolderSingList = new ViewHolderSingList();
            viewHolderSingList.iv_cover1 = (ImageView) convertView.findViewById(R.id.iv_cover);
            viewHolderSingList.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolderSingList.tv_artist = (TextView) convertView.findViewById(R.id.tv_artist);

            convertView.setTag(viewHolderSingList);
        } else {
            viewHolderSingList = (ViewHolderSingList) convertView.getTag();
        }
//        List<JavaBean.SongListBean> song_list = list.get(position).getSong_list();
        ImageLoader.getInstance().displayImage(list2.get(position).getPic_small(), viewHolderSingList.iv_cover1);
        viewHolderSingList.tv_title.setText(list2.get(position).getTitle());
        viewHolderSingList.tv_artist.setText(list2.get(position).getAuthor()+"-"+list2.get(position).getTitle());
        return convertView;
    }

    class ViewHolderSingList {
        ImageView iv_cover1;
        TextView tv_title;
        TextView tv_artist;
    }
}
