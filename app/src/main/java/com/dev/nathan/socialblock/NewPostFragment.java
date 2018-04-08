package com.dev.nathan.socialblock;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import com.labo.kaji.fragmentanimations.CubeAnimation;


/**
 * A simple {@link Fragment} subclass.
 */
public class NewPostFragment extends Fragment {


    public NewPostFragment() {
        // Required empty public constructor
    }
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return CubeAnimation.create(CubeAnimation.UP, enter, 300);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification, container, false);
    }

}
