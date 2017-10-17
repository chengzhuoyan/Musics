package com.bwie.vidio.utils;

public class Api {

    public static String SONG = "http://tingapi.ting.baidu.com/v1/restserver/ting";
    //获取音乐信息
    public static  String SONG_MESS = SONG + "?method=baidu.ting.billboard.billList&type=1&size=10&offset=0";
    //获取改变信息
    public static  String SONG_MESS_URL = SONG  + SONG_MESS + "&type=1&size=10&offset=0";
    //获取对应歌曲列表信息
    public static  String SONG_LIST_TYPE = SONG  + "?method=baidu.ting.billboard.billList&size=10&offset=0&type=";
    //获取播放歌曲信息
    public static  String SONG_PLAY_ID = SONG  + "?method=baidu.ting.song.play&songid=";

}
