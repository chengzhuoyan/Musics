package com.bwie.vidio.modle.view.customview;

import com.bwie.vidio.modle.bean.LrcRow;

/**
 * Created by pc on 2017/10/17.
 */

public interface ILrcViewListener {
    /**
     * 当歌词被用户上下拖动的时候回调该方法
     */
    void onLrcSeeked(int newPosition, LrcRow row);
}
