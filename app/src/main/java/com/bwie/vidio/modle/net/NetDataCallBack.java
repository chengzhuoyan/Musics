package com.bwie.vidio.modle.net;

public interface NetDataCallBack<T> {
    void success(T t);
    void faild(int positon, String str);
}
