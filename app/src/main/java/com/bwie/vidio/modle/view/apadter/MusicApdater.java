package com.bwie.vidio.modle.view.apadter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bwie.vidio.modle.view.fragment.LocalFragment;
import com.bwie.vidio.modle.view.fragment.OnLineFragment;

public class MusicApdater extends FragmentPagerAdapter {
    public MusicApdater(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                LocalFragment localFragment = new LocalFragment();
                return localFragment;
            case 1:
                OnLineFragment onLineFragment = new OnLineFragment();
                return onLineFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
