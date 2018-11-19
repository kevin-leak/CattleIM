package com.example.thinkpad.cattleim.frags.main.contact;

import android.os.Bundle;
import android.util.Log;

import com.example.common.app.BaseFragment;
import com.example.thinkpad.cattleim.R;

import static android.content.ContentValues.TAG;

public class GroupFragment extends BaseFragment{
    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_group;
    }

    @Override
    protected void intiArgs(Bundle arguments) {
        super.intiArgs(arguments);
        Log.e(TAG, "intiArgs: ");
    }
}
