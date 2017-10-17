package com.bwie.vidio.modle.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragmnet extends Fragment {

    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(setLayoutResouceId(),container,false);
        setInitView(mView,savedInstanceState);
        setLodaData();
        setInitData();

        return mView;
    }
    protected abstract int setLayoutResouceId();
    protected abstract void setInitView(View mView,Bundle savedInstanceState);
    protected abstract void setInitData();
    protected abstract void setLodaData();
}
